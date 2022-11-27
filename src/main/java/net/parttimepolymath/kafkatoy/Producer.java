package net.parttimepolymath.kafkatoy;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * The Producer will be used to push messages to the kafka topic. It can either run forever,
 * or be instructed to run a limted number of times
 */
@Builder
@Slf4j
public class Producer implements Runnable {
    private boolean debugMode = false;
    private int messageCount;
    private static final Faker faker = new Faker();

    @Override
    public void run() {
        log.info("starting with messageCount = {}", messageCount);

        Stream<String> nameStream = getDataStream();
        if (messageCount>0) {
            nameStream.limit(messageCount).forEach(System.out::println);
        } else {
            nameStream.forEach(System.out::println);
        }
        log.info("ending");
    }

    /**
     * create a Stream of data that can be used for testing with
     * @return a non null Stream
     */
    public Stream<String> getDataStream() {
        // these two lines could be combined but I like the reminder of what is going on.
        Supplier<String> nameSupplier = () -> faker.name().fullName();
        return Stream.generate(nameSupplier);
    }
}
