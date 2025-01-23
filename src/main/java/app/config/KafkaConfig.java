package app.config;

import org.apache.kafka.clients.producer.KafkaProducer;

public interface KafkaConfig {
    KafkaProducer<String, byte[]> createProducer();
}
