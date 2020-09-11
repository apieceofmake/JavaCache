package org.example;
// Each Cache Entity is node of doubly-linked list
class LRUNode<K, V> {
    LRUNode<K, V> previous;
    LRUNode<K, V> next;
    K key;
    V value;
    public LRUNode(LRUNode<K, V> previous, LRUNode<K, V> next, K key, V value) {
        this.previous = previous;
        this.next = next;
        this.key = key;
        this.value = value;
    }
}

