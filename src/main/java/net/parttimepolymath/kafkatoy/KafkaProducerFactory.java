package net.parttimepolymath.kafkatoy;

import org.apache.kafka.clients.producer.KafkaProducer;

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
        kafkaProperties.put("bootstrap.servers", bootstrap);
        kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put("client.id", ApplicationProperties.getProducerId());
        kafkaProperties.put("compression.type", "snappy");
        kafkaProperties.put("enable.idempotence", "true");
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
