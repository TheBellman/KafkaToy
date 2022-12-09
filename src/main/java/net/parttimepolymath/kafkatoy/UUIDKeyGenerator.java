package net.parttimepolymath.kafkatoy;

import java.util.UUID;

/**
 * Key generator that returns a random UUID as a string.
 */
public class UUIDKeyGenerator implements KeyGenerator<String> {
    @Override
    public String getKey() {
        return UUID.randomUUID().toString();
    }
}
