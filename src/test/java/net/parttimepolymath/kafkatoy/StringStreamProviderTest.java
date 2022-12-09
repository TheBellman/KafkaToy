package net.parttimepolymath.kafkatoy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringStreamProviderTest {
    StringStreamProvider instance = new StringStreamProvider();

    @Test
    void testGetDataStream() {
        assertNotNull(instance.getDataStream());
    }

    @Test
    void testGetData() {
        Object[] result = instance.getDataStream().limit(10).toArray();
        assertEquals(10, result.length);
        for (Object o : result) {
            assertNotNull(o);
        }
    }
}