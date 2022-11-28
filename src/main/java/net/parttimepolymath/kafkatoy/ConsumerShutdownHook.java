package net.parttimepolymath.kafkatoy;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * shutdown hook that is used to close the consumer that is provided.
 */
@Slf4j
public class ConsumerShutdownHook<K, V> extends Thread {
    private final KafkaConsumer<K, V> service;
    private final String id;

    /**
     * primary constructor
     * @param groupId the group id of the consumer, used for reporting
     * @param consumer the consumer that will be shutdown.
     */
    public ConsumerShutdownHook(final String groupId, final KafkaConsumer<K,V> consumer) {
        this.service = consumer;
        this.id = groupId;
    }

    @Override
    public void run() {
        if (service != null) {
            log.info("Shutting down consumer for group {}", id);
            // catch the exception that may occur as the client complains about thread safety
            try {
                service.close();
            } catch (Exception ex) {
                log.debug("Exception while closing", ex);
            }
        }
    }
}
