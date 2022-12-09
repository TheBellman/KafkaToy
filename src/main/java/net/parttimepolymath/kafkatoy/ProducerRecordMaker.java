package net.parttimepolymath.kafkatoy;

import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * interface that allows us to make the Producer more generic by abstracting how the ProducerRecords are made
 * @param <K> the type of message key
 * @param <V> the type of value key
 */
public interface ProducerRecordMaker<K, V> {
    ProducerRecord<K, V> createRecord(String topic, K key, V value);
}
