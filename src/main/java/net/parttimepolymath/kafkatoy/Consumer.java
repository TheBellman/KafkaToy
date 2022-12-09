package net.parttimepolymath.kafkatoy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;

/**
 * The consumer just reads from the specified topic and writes what it sees to stdout.
 */
@Builder
@Slf4j
public class Consumer<K, V> implements Runnable {
    private String topic;
    private String bootstrap;
    private final long TIMEOUT_MILLIS = 100;
    private boolean closing;

    @Override
    public void run() {
        log.info("starting");
        try (KafkaConsumer<K, V> consumer = KafkaConsumerFactory.make(topic, bootstrap)) {
            while (!closing) {
                ConsumerRecords<K, V> records = consumer.poll(Duration.ofMillis(TIMEOUT_MILLIS));
                for (ConsumerRecord<K, V> record : records) {
                    log.info("{} : {}", record.key(), record.value());
                }
                consumer.commitAsync();
            }
            consumer.commitSync();
        } catch (WakeupException ex) {
            log.debug("Consumer was woken up - WakeupException raised");
        }
        catch (Exception ex) {
            log.error("Unexpected error", ex);
        }
        log.info("ending");
    }
}
