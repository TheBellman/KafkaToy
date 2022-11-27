package net.parttimepolymath.kafkatoy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationPropertiesTest {
    @Test
    void getAppName() {
        assertNotNull(ApplicationProperties.getAppName());
    }

    @Test
    void getAppVersion() {
        assertNotNull(ApplicationProperties.getAppVersion());
    }

    @Test
    void getBuildDate() {
        assertNotNull(ApplicationProperties.getBuildDate());
    }
}