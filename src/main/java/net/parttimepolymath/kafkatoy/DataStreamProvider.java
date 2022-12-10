package net.parttimepolymath.kafkatoy;

import java.util.stream.Stream;

/**
 * allow injection of the source of our data, with the data being provided as a stream
 * @param <V> the type of data in our stream.
 */
public interface DataStreamProvider<V> {
    /**
     * obtain a stream of data to send to our topic.
     * @return the stream of data.
     */
    Stream<V> getDataStream();
}
