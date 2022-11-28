package net.parttimepolymath.kafkatoy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationPropertiesTest {
    @Test
    void getValues() {
        assertAll(() -> assertNotNull(ApplicationProperties.getProducerId()),
                () -> assertNotNull(ApplicationProperties.getAppName()),
                () -> assertNotNull(ApplicationProperties.getAppVersion()),
                () -> assertNotNull(ApplicationProperties.getBuildDate()),
                () -> assertNotNull(ApplicationProperties.getDefaultTopic()),
                () -> assertNotNull(ApplicationProperties.getBootstrap())
        );
    }
}