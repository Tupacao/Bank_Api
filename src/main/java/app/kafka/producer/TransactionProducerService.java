package app.kafka.producer;

import app.kafka.config.ProducerProvider;
import app.proto.TransactionProto;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;

import java.util.UUID;

@Singleton
@Slf4j
public class TransactionProducerService {

    final private KafkaProducer<String, byte[]> producer;

    public TransactionProducerService(ProducerProvider producerProvider) {
        this.producer = producerProvider.createProducer();
    }

    public void sendTransaction(String key, TransactionProto.Transaction transaction) {
        ProducerRecord<String, byte[]> record = new ProducerRecord("transaction-topic", key, transaction.toByteArray());

        String uuid = UUID.randomUUID().toString();

        Headers headers = new RecordHeaders();
        headers.add("uuid", uuid.getBytes());
        record.headers();

        producer.send(record);
    }
}
