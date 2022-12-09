package net.parttimepolymath.kafkatoy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConsumerFactoryTest {

    @Test
    void make() {
        assertNotNull(KafkaConsumerFactory.make("consumer", "localhost:9091"));
    }
}