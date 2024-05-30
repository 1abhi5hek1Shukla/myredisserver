package com.myredis.server.data.structures;


import java.util.Arrays;

public class MyHashMap <K, V>{
    private static final int INITIAL_SIZE = 1 << 4; // 16
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private final Entry<K, V>[] hashTable;

    @SuppressWarnings("unchecked")
    public MyHashMap(){
        hashTable = (Entry<K, V>[]) new Entry[INITIAL_SIZE];
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int capacity){
        capacity = tableSize(capacity);
        hashTable = (Entry<K, V>[]) new Entry[capacity];
    }

    public void put(K key, V value){
        int hash = hasher(key);

        Entry<K, V> entry = hashTable[hash];
        if(entry == null){
            hashTable[hash] = new Entry<>(key, value);
        }else{
            Entry<K, V> prevEntry = entry;
            while (entry != null){
                if(entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
                prevEntry = entry;
                entry = entry.next;
            }
            prevEntry.next = new Entry<>(key, value);
        }
    }
    public V get(K key){
        int hash = hasher(key);
        Entry<K, V> entry = hashTable[hash];
        while(entry != null){
            if(entry.key.equals(key)) return entry.value;
            entry = entry.next;
        }
        return null;
    }
    public boolean containsKey(K key){
        return get(key) != null;
    }
    private int tableSize(int capcity){
        int res = capcity - 1;
        res |= res >>> 1;
        res |= res >>> 2;
        res |= res >>> 4;
        res |= res >>> 8;
        res |= res >>> 16;
        return res < 0 ? 1 : res >= MAXIMUM_CAPACITY ? MAXIMUM_CAPACITY : res + 1;
    }
    public void remove(K key) {
        int hash = hasher(key);
        Entry<K, V> entry = hashTable[hash];
        Entry<K, V> prevEntry = null;
        while(entry != null){
            if(entry.key.equals(key)){
                if(prevEntry == null) {
                    hashTable[hash] = entry.next;
                }else{
                    prevEntry.next = entry.next;
                }
                return;
            }
            prevEntry = entry;
            entry = entry.next;
        }
    }
    private int hasher(K key){
        int h;
        h = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        h &= ~(1 << 31);
//        h = key == null ? 0 : ((h = key.hashCode()) ^ h >>> 16);
        return h % hashTable.length;
    }

    @Override
    public String toString() {
        return "MyHashMap{" +
                "hasclhTable=" + Arrays.toString(hashTable) +
                '}';
    }

    public static class Entry<K, V> {
        public K key;
        public V value;
        public Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    ", next=" + next +
                    '}';

        }
    }
}
