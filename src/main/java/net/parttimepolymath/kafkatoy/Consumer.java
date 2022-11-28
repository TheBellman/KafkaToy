package net.parttimepolymath.kafkatoy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

/**
 * The consumer just reads from the specified topic and writes what it sees to stdout.
 */
@Builder
@Slf4j
public class Consumer<K, V> implements Runnable {
    private String topic;
    private String bootstrap;
    private final long TIMEOUT_MILLIS=100;

    @Override
    public void run() {
        log.info("starting");
        try (KafkaConsumer<K, V> consumer = ConsumerFactory.make(topic, bootstrap)) {
            while (true) {
                ConsumerRecords<K, V> records = consumer.poll(Duration.ofMillis(TIMEOUT_MILLIS));
                for (ConsumerRecord<K, V> record : records) {
                    log.info("{} : {}", record.key(), record.value());
                }
            }
        } catch (Exception ex) {
            log.error("Receiving from Kafka failed", ex);
        }
        log.info("ending");
    }
}
