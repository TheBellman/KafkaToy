package net.parttimepolymath.kafkatoy;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * put all the pieces for making the KafkaConsumer in one place for convenience.
 * Note that this also looks after setting a shutdown hook to try to close the
 * generated consumer.
 */
public class ConsumerFactory {
    static <K, V> KafkaConsumer<K, V> make(final String topic, final String bootstrap) {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", bootstrap);
        kafkaProperties.put("group.id", ApplicationProperties.getGroupId());
        kafkaProperties.put("enable.auto.commit", "true");
        kafkaProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put("auto.commit.interval.ms", "1000");
        KafkaConsumer<K, V> consumer =  new KafkaConsumer<>(kafkaProperties);
        addShutdown(ApplicationProperties.getGroupId(), consumer);
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }

    /**
     * register a thread to close down the provided consumer
     * @param groupId the group id of the consumer, used for reporting.
     * @param consumer the consumer to close down.
     */
    private static <K, V> void addShutdown(final String groupId, final KafkaConsumer<K, V> consumer) {
        Runtime.getRuntime().addShutdownHook(new ConsumerShutdownHook<>(groupId, consumer));
    }
}
