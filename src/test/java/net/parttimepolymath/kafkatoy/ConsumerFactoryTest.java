package net.parttimepolymath.kafkatoy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsumerFactoryTest {

    @Test
    void make() {
        assertNotNull(ConsumerFactory.make("consumer", "localhost:9091"));
    }
}