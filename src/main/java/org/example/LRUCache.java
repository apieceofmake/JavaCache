package org.example;

import java.util.HashMap;

public class LRUCache<K, V> implements ConfigurableCache<K, V> {

    private HashMap<K, LRUNode<K, V>> cache;
    private LRUNode<K, V> leastRecentlyUsed;    // LRU
    private LRUNode<K, V> mostRecentlyUsed;     // MRU
    private final int maxSize;
    private int currentSize;

    public LRUCache(int maxSize){
        this.maxSize = maxSize;
        this.currentSize = 0;
        leastRecentlyUsed = new LRUNode<>(null, null, null, null);
        mostRecentlyUsed = leastRecentlyUsed;
        cache = new HashMap<>();
    }

    public V get(K key){
        LRUNode<K, V> currentNode = cache.get(key);
        if (currentNode == null){
            return null;
        } else {
            // If this is MRU then no update required
            if (currentNode == mostRecentlyUsed){
                return mostRecentlyUsed.value;
            } else {
                // Get the next and previous nodes
                LRUNode<K, V> nextNode = currentNode.next;
                LRUNode<K, V> previousNode = currentNode.previous;

                // If this is LRU, we need to update LRU
                if (currentNode == leastRecentlyUsed){
                    nextNode.previous = null;
                    leastRecentlyUsed = nextNode;
                } else {
                    // If we are in the middle, we need to update the items before and after our item
                    previousNode.next = nextNode;
                    nextNode.previous = previousNode;
                }

                // if currentNode is not MRU, make it MRU
                currentNode.previous = mostRecentlyUsed;
                mostRecentlyUsed.next = currentNode;
                mostRecentlyUsed = currentNode;
                mostRecentlyUsed.next = null;

                return currentNode.value;
            }
        }
    }

    public void put(K key, V value){
        // If the object with this key is not already in the Cache
        if (!cache.containsKey(key)){
            // Create newNode and put it on the top of the linked-list, now newNode is MRU
            LRUNode<K, V> newNode = new LRUNode<>(mostRecentlyUsed, null, key, value);
            mostRecentlyUsed.next = newNode;
            cache.put(key, newNode);

            // Update MRU & LRU
            mostRecentlyUsed = newNode;
            //if this is a first added entry then LRU=MRU=newNode
            if (currentSize == 0){
                leastRecentlyUsed = newNode;
            }

            if (currentSize == maxSize){
                // delete current LRU, update LRU
                cache.remove(leastRecentlyUsed.key);
                leastRecentlyUsed = leastRecentlyUsed.next;
                leastRecentlyUsed.previous = null;
            } else {
                // Update cache size,
                currentSize++;
            }
        }
    }
}

