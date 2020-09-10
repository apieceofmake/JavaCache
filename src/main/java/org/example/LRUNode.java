package org.example;

// Define Node with pointers to the previous and next items and a key, value pair
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

