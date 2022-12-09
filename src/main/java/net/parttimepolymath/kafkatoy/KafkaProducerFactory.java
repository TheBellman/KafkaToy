package net.parttimepolymath.kafkatoy;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * put all the pieces for making the KafkaProducer in one place for convenience.
 * Note that this also looks after setting a shutdown hook to try to close the
 * generated producer.
 */
public final class KafkaProducerFactory {
    /**
     * create the producer that will be used to write our data stream.
     * This is package private to support testing.
     *
     * @return a non null KafkaProducer
     */
    static <K, V> KafkaProducer<K, V> make(final String bootstrap) {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getTypeName());
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getTypeName());
        kafkaProperties.put("schema.registry.url", ApplicationProperties.getSchemaRegistryUrl());
        kafkaProperties.put(ProducerConfig.CLIENT_ID_CONFIG, ApplicationProperties.getProducerId());
        kafkaProperties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        kafkaProperties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        KafkaProducer<K, V> result =  new KafkaProducer<>(kafkaProperties);
        addShutdown(ApplicationProperties.getProducerId(), result);
        return result;
    }

    /**
     * register a thread to close down the provided producer
     * @param clientId the id of the producer, used for reporting.
     * @param producer the producer to close down.
     */
    private static <K, V> void addShutdown(final String clientId, final KafkaProducer<K, V> producer) {
        Runtime.getRuntime().addShutdownHook(new ProducerShutdownHook<>(clientId, producer));
    }
}
