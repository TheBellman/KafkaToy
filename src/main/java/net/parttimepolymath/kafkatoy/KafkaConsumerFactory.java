package net.parttimepolymath.kafkatoy;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
/**
 * put all the pieces for making the KafkaConsumer in one place for convenience.
 * Note that this also looks after setting a shutdown hook to try to close the
 * generated consumer.
 */
public class KafkaConsumerFactory {
    static <K, V> KafkaConsumer<K, V> make(final String topic, final String bootstrap) {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, ApplicationProperties.getGroupId());
        kafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaProperties.put("schema.registry.url", ApplicationProperties.getSchemaRegistryUrl());
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getTypeName());
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getTypeName());
        kafkaProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
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
        Runtime.getRuntime().addShutdownHook(new ConsumerShutdownHook<>(groupId, consumer, Thread.currentThread()));
    }
}
