package com.myredis.server.data.structures;

public class DoublyLinkedList <K, V> {

    private Node<K, V> head;
    private Node<K, V> tail;
    private int size;

    public DoublyLinkedList() {
        head = new Node<>(null, null);
        tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    public void add(Node<K, V> node){
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
        size += 1;
    }

    public void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size -= 1;
    }

    public void moveToFront(Node<K, V> node){
        remove(node);
        add(node);
    }

    public Node<K, V> removeLast(){
        if (tail.prev == head) return null;
        Node<K, V> node = tail.prev;
        remove(node);
        return node;
    }

    public int getSize() {
        return size;
    }

    public static class Node <K, V>{
        public K key;
        public V value;
        public Node<K, V> prev;
        public Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
