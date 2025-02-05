package app.kafka.streams;

import app.kafka.config.KafkaStreamsConfig;
import app.processor.IdempotentProcessor;
import app.service.LogTransactionService;
import app.service.TransactionService;
import app.proto.TransactionProto;

import com.google.protobuf.InvalidProtocolBufferException;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.context.annotation.Context;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

@KafkaListener
@Context
public class TransactionStreamsConsumer {
    private final KafkaStreamsConfig kafkaStreamsConfig;

    @Inject
    LogTransactionService logTransactionService;

    @Inject
    TransactionService transactionService;
    StreamsBuilder builder;
    private KafkaStreams kafkaStreams;

    public TransactionStreamsConsumer(KafkaStreamsConfig kafkaStreamsConfig) {
        this.kafkaStreamsConfig = kafkaStreamsConfig;
    }

    @PostConstruct
    public void init() {
        buildTopology();
        startKafkaStreams();
    }

    private void startKafkaStreams() {
        if (kafkaStreams == null) {
            Topology topology = builder.build();
            kafkaStreams = new KafkaStreams(topology, kafkaStreamsConfig.getStreamsConfig());
            kafkaStreams.start();
        }
    }

    private void buildTopology() {
        builder = new StreamsBuilder();
        builder.addStateStore(IdempotentProcessor.createStoreBuilder());
        KStream<String, byte[]> transactionStream = builder.stream("transaction-topic");

        transactionStream
                .transform(() -> new IdempotentProcessor(), IdempotentProcessor.STATE_STORE_NAME)
                .mapValues(bytes -> {
                    try {
                        return TransactionProto.Transaction.parseFrom(bytes);
                    } catch (InvalidProtocolBufferException e) {
                        throw new RuntimeException("Failed to parse transaction from byte array", e);
                    }
                })
                .mapValues(transactionProto -> transactionService.getTransaction(transactionProto.getTransactionId()))
                .mapValues(logTransactionService::processTransaction)
                .to("transaction-message-topic", Produced.with(Serdes.String(), Serdes.String()));

        KStream<String, String> transactionLogStream = builder.stream("transaction-message-topic", Consumed.with(Serdes.String(), Serdes.String()));
        transactionLogStream.foreach((key, value) -> {
            System.out.println("Log Transaction Confirmation Message Received:");
            System.out.println("  Key: " + key);
            System.out.println("  Message: " + value);
        });
    }

}
