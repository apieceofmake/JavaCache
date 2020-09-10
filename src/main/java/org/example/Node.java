package org.example;

// Define Node with pointers to the previous and next items and a key, value pair
class Node<K, V> {
    Node<K, V> previous;
    Node<K, V> next;
    K key;
    V value;

    public Node(Node<K, V> previous, Node<K, V> next, K key, V value) {
        this.previous = previous;
        this.next = next;
        this.key = key;
        this.value = value;
    }
}

