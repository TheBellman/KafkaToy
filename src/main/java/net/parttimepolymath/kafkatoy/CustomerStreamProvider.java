package net.parttimepolymath.kafkatoy;

import com.github.javafaker.Faker;
import net.parttimepolymath.kafkatoy.avro.Customer;

import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CustomerStreamProvider implements DataStreamProvider<Customer> {
    private static final Faker faker = new Faker();

    @Override
    public Stream<Customer> getDataStream() {
        // these two lines could be combined, but I like the reminder of what is going on.
        Supplier<Customer> supplier =
                () -> Customer.newBuilder().setId(UUID.randomUUID().toString()).setName(faker.name().fullName()).build();
        return Stream.generate(supplier);
    }
}
