package net.parttimepolymath.kafkatoy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;


import java.util.concurrent.atomic.AtomicLong;
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

    private KeyGenerator<K> keyGenerator;
    private DataStreamProvider<V> dataStreamProvider;

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
        try (Stream<V> nameStream = dataStreamProvider.getDataStream(); KafkaProducer<K, V> producer =
                KafkaProducerFactory.make(bootstrap)) {
            if (messageCount > 0) {
                nameStream.limit(messageCount).map(body -> new ProducerRecord<K, V>(topic, keyGenerator.getKey(),
                        body)).forEach(msg -> {
                    producer.send(msg, new CallbackLogger());
                    totalSent.incrementAndGet();
                });
            } else {
                nameStream.map(body -> new ProducerRecord<K, V>(topic, keyGenerator.getKey(), body)).forEach(msg -> {
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
