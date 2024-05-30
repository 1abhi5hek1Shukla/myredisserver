package com.myredis.server.data.cache;

import com.myredis.server.data.structures.DoublyLinkedList;
import com.myredis.server.data.structures.MyHashMap;

import java.util.ArrayList;

public class LRUCache<K, V> implements Cache<K, V>{

    private static final int DEFAULT_CAPCITY = 1 << 8;

    private MyHashMap<K, DoublyLinkedList.Node<K, V>> myHashMap;
    private DoublyLinkedList<K, V> doublyLinkedList;

    private int capacity;
    private static LRUCache<String, Object> lruCache;

    public LRUCache(){
        this.capacity = DEFAULT_CAPCITY;
        doublyLinkedList = new DoublyLinkedList<>();
        myHashMap = new MyHashMap<>();
    }
    public LRUCache(int capacity){
        this.capacity = capacity;
        myHashMap = new MyHashMap<>();
        ArrayList<Integer> c = new ArrayList<>();
    }
    // LRU
    @Override
    public void put(K key, V value) {
        DoublyLinkedList.Node<K, V> node;
        if(myHashMap.containsKey(key)){
            node = myHashMap.get(key);
            node.value = value;
            doublyLinkedList.moveToFront(node);
        }else{
            if(doublyLinkedList.getSize() >= capacity){
                DoublyLinkedList.Node<K, V> nodeToRemove = doublyLinkedList.removeLast();
                myHashMap.remove(nodeToRemove.key);
            }
            node = new DoublyLinkedList.Node<>(key, value);
            myHashMap.put(key, node);
            doublyLinkedList.add(node);
        }
    }

    @Override
    public V get(K key) {
        if(!myHashMap.containsKey(key)) return null;
        DoublyLinkedList.Node<K, V> node = myHashMap.get(key);
        doublyLinkedList.moveToFront(node);
        return node.value;
    }

    @Override
    public boolean evict(K key) {
        if(!myHashMap.containsKey(key)){
            DoublyLinkedList.Node<K, V> node = myHashMap.get(key);
            doublyLinkedList.remove(node);
            myHashMap.remove(node.key);
            return true;
        }
        return false;
    }

    @Override
    public void printMemory() {

    }
}
