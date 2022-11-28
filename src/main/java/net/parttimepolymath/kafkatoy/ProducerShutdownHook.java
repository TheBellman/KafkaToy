package net.parttimepolymath.kafkatoy;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * shutdown hook that is used to close the producer that is provided.
 */
@Slf4j
public class ProducerShutdownHook<K, V> extends Thread {
    private final KafkaProducer<K, V> service;
    private final String id;

    /**
     * primary constructor
     * @param clientId the id of the producer, used for reporting
     * @param producer the producer that will bue shutdown.
     */
    public ProducerShutdownHook(final String clientId, final KafkaProducer<K, V> producer) {
        this.service = producer;
        this.id = clientId;
    }

    @Override
    public void run() {
        if (service != null) {
            log.info("Shutting down producer {}", id);
            service.close();
        }
    }
}
