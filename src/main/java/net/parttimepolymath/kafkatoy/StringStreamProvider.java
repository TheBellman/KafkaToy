package net.parttimepolymath.kafkatoy;

import com.github.javafaker.Faker;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * data provider that returns a stream of names as string
 */
public class StringStreamProvider implements DataStreamProvider<String> {
    private static final Faker faker = new Faker();
    @Override
    public Stream<String> getDataStream() {
        // these two lines could be combined, but I like the reminder of what is going on.
        Supplier<String> nameSupplier = () -> faker.name().fullName();
        return  Stream.generate(nameSupplier);
    }
}
