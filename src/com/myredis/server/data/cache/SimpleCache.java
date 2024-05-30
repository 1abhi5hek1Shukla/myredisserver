package com.myredis.server.data.cache;

import com.myredis.server.data.structures.MyHashMap;

public class SimpleCache<K, V> implements Cache<K, V>{
    private MyHashMap<K, V> myHashMap;

    public SimpleCache(){
        myHashMap = new MyHashMap<>();
    }
    @Override
    public void put(K key, V value) {
        myHashMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return myHashMap.get(key);
    }

    @Override
    public boolean evict(K key) {
        myHashMap.remove(key);
        return myHashMap.containsKey(key);
    }

    @Override
    public void printMemory() {
        System.out.println(myHashMap);
    }
}
