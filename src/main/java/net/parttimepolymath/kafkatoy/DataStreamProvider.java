package net.parttimepolymath.kafkatoy;

import java.util.stream.Stream;

public interface DataStreamProvider<V> {
    Stream<V> getDataStream();
}
