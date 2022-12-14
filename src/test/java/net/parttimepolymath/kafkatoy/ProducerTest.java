package net.parttimepolymath.kafkatoy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProducerTest {
    // tests variations on the builder
    @Test
    void build() {
        assertAll("build", () -> assertNotNull(Producer.builder().build()),
                () -> assertNotNull(Producer.builder().messageCount(20).build()));
    }


}