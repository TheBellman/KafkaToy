package net.parttimepolymath.kafkatoy;

import java.util.UUID;

public class UUIDKeyGenerator implements KeyGenerator<String> {
    @Override
    public String getKey() {
        return UUID.randomUUID().toString();
    }
}
