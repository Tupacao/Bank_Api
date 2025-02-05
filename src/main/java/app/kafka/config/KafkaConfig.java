package app.kafka.config;

import jakarta.inject.Singleton;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@Singleton
public class KafkaConfig implements ProducerProvider {
    @Override
    public KafkaProducer<String, byte[]> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", ByteArraySerializer.class.getName());
        return new KafkaProducer<String, byte[]>(props);
    }
}
