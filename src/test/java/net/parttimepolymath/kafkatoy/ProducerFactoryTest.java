package net.parttimepolymath.kafkatoy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProducerFactoryTest {

    @Test
    void testMakeProducer() {
        assertNotNull(ProducerFactory.make("localhost:9091"));
    }
}