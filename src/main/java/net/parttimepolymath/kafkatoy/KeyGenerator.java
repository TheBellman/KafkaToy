package net.parttimepolymath.kafkatoy;

/**
 * interface which allows us to abstract keys in the Producer
 * @param <K> the type of Key to create
 */
public interface KeyGenerator<K> {
    /**
     * get a key of type K
     * @return a generated key of type K
     */
    K getKey();
}
