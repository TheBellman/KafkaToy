package net.parttimepolymath.kafkatoy;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * The Producer will be used to push messages to the kafka topic. It can either run forever,
 * or be instructed to run a limited number of times
 */
@Builder
@Slf4j
public class Producer implements Runnable {
    private boolean debugMode;
    private int messageCount;
    private String topic;
    private static final Faker faker = new Faker();

    // TODO: loop forever should trap for interrupt to make sure close happens
    @Override
    public void run() {
        log.info("starting with messageCount = {}", messageCount);

        /*
         * note that we are relying on AutoClose behaviour on the producer, which is not necessarily optimal
         * because we don't get a chance to manage how long the producer will wait to tidy itself off, and
         * it will wait Long.MAX_VALUE milliseconds to finish tidying up before giving up.
         */
        AtomicLong totalSent = new AtomicLong(0);
        try (Stream<String> nameStream = getDataStream(); KafkaProducer<String, String> producer = makeProducer()) {
            if (messageCount > 0) {
                nameStream.limit(messageCount).map(name -> new ProducerRecord<String, String>(topic, name)).forEach(msg -> {
                    producer.send(msg, new CallbackLogger());
                    totalSent.incrementAndGet();
                });
            } else {
                nameStream.map(name -> new ProducerRecord<String, String>(topic, name)).forEach(msg -> {
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
     * create the producer that will be used to write our data stream.
     * This is package private to support testing.
     *
     * @return a non null KafkaProducer
     */
    KafkaProducer<String, String> makeProducer() {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put("bootstrap.servers", "localhost:9092");
        kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(kafkaProperties);
    }

    /**
     * Simple Callback that just logs any errors. A more sophisticated solution could be used to
     * manage production rate, perhaps pausing for a period of time.
     */
    private  class CallbackLogger implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e != null) {
                log.error("Sending message failed", e);
            }
        }
    }
}
