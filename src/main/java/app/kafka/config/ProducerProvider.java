package app.kafka.config;

import jakarta.inject.Singleton;
import org.apache.kafka.clients.producer.KafkaProducer;

public interface ProducerProvider {
    KafkaProducer<String, byte[]> createProducer();
}
