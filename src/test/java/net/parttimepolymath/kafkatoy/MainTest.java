package net.parttimepolymath.kafkatoy;

import org.apache.commons.cli.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void validateBootstrap() {
        assertThrows(ParseException.class,
                () -> Main.validateBootstrap("fred"),
                "Expected ParseException but nothing was thrown");

        assertThrows(ParseException.class,
                () -> Main.validateBootstrap("fred:mary"),
                "Expected ParseException but nothing was thrown");

        assertDoesNotThrow(
                () -> Main.validateBootstrap("localhost:9092"),
                "Exception thrown when it was not expected");
    }
}