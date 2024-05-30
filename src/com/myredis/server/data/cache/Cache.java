package com.myredis.server.data.cache;

public interface Cache<K, V> {
    void put(K key, V value);
    Object get(K key);
    boolean evict(K key);
    void printMemory();
}
