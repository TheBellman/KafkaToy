package net.parttimepolymath.kafkatoy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProducerTest {
    private Producer instance;

    // note this will verify that the builder will in fact work
    @BeforeEach
    void setUp() {
        instance = Producer.builder().messageCount(20).build();
    }

    // tests variations on the builder
    @Test
    void build() {
        assertAll("build", () -> assertNotNull(Producer.builder().build()),
                () -> assertNotNull(Producer.builder().messageCount(20).build()));
    }

    @Test
    void testGetDataStream() {
        assertNotNull(instance.getDataStream());
    }

}