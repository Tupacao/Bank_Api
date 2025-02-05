package app.processor;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

public class IdempotentProcessor implements Transformer<String, byte[], KeyValue<String, byte[]>> {

    public static final String STATE_STORE_NAME = "processed-transactions";
    private KeyValueStore<String, String> stateStore;
    private ProcessorContext context;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        this.stateStore = (KeyValueStore<String, String>) context.getStateStore(STATE_STORE_NAME);
    }

    @Override
    public KeyValue<String, byte[]> transform(String key, byte[] value) {
        Headers headers = context.headers();
        byte[] uuidBytes = headers.lastHeader("uuid").value();
        if (uuidBytes == null) {
            throw new IllegalStateException("UUID header is missing");
        }
        String uuid = new String(uuidBytes);

        if (stateStore.get(uuid) != null) {
            System.out.println("Mensagem duplicada ignorada: " + uuid);
            return null; // Ignora a mensagem duplicada
        }

        stateStore.put(uuid, "processed");

        System.out.println("Mensagem processada: " + uuid);
        return KeyValue.pair(key, value); // Retorna a mensagem original
    }

    @Override
    public void close() {
    }

    // Método para criar o StoreBuilder para o state store de idempotência
    public static StoreBuilder<KeyValueStore<String, String>> createStoreBuilder() {
        return Stores.keyValueStoreBuilder(
                Stores.persistentKeyValueStore(STATE_STORE_NAME), // Nome do state store
                Serdes.String(), // Serde para as chaves
                Serdes.String()  // Serde para os valores
        );
    }
}