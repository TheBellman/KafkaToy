package net.parttimepolymath.kafkatoy;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.security.Key;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * The Producer will be used to push messages to the kafka topic. It can either run forever,
 * or be instructed to run a limited number of times
 */
@Builder
@Slf4j
public class Producer<K, V> implements Runnable {
    private int messageCount;
    private String topic;
    private String bootstrap;
    private static final Faker faker = new Faker();
    private KeyGenerator<K> keyGenerator;

    @Override
    public void run() {
        log.info("starting with messageCount = {}", messageCount);

        /*
         * note that we are relying on AutoClose behaviour on the producer, which is not necessarily optimal
         * because we don't get a chance to manage how long the producer will wait to tidy itself off, and
         * it will wait Long.MAX_VALUE milliseconds to finish tidying up before giving up.
         */
        // TODO move the counter into a wrapper around the producer so it's available during shutdown
        AtomicLong totalSent = new AtomicLong(0);
        try (Stream<String> nameStream = getDataStream(); KafkaProducer<K, V> producer =
                ProducerFactory.make(bootstrap)) {
            if (messageCount > 0) {
                nameStream.limit(messageCount).map(name -> new ProducerRecord<K, V>(topic,
                        keyGenerator.getKey(), (V) name)).forEach(msg -> {
                    producer.send(msg, new CallbackLogger());
                    totalSent.incrementAndGet();
                });
            } else {
                nameStream.map(name -> new ProducerRecord<K, V>(topic, keyGenerator.getKey(), (V) name)).forEach(msg -> {
                    producer.send(msg, new CallbackLogger());
                    totalSent.incrementAndGet();
                });
            }
        } catch (Exception ex) {
            log.error("Sending to Kafka failed", ex);
        }
        log.info("ending - sent {} messages", totalSent.get());
    }

    /**
     * create a Stream of data that can be used for testing with. Package private to support testing.
     *
     * @return a non null Stream
     */
    Stream<String> getDataStream() {
        // these two lines could be combined, but I like the reminder of what is going on.
        Supplier<String> nameSupplier = () -> faker.name().fullName();
        return Stream.generate(nameSupplier);
    }

    /**
     * Simple Callback that just logs any errors. A more sophisticated solution could be used to
     * manage production rate, perhaps pausing for a period of time.
     */
    private static class CallbackLogger implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                log.error("Sending message failed", e);
            }
        }
    }
}
