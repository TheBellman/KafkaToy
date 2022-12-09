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
    private final Thread mainThread;
    /**
     * primary constructor
     *
     * @param groupId  the group id of the consumer, used for reporting
     * @param consumer the consumer that will be shutdown.
     * @param mainThread reference to main thread so we can rejoin it
     */
    public ConsumerShutdownHook(final String groupId, final KafkaConsumer<K, V> consumer, final Thread mainThread) {
        this.service = consumer;
        this.id = groupId;
        this.mainThread = mainThread;
    }

    @Override
    public void run() {
        if (service != null) {
            log.info("Shutting down consumer for group {}", id);
            // catch the exception that may occur as the client complains about thread safety
            service.wakeup();
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
