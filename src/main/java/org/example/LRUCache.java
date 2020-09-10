package org.example;

import java.util.HashMap;

public class LRUCache<K, V> implements configurableCache <K, V>{

    private HashMap<K, LRUNode<K, V>> cache;
    private LRUNode<K, V> leastRecentlyUsed;
    private LRUNode<K, V> mostRecentlyUsed;
    private final int maxSize;
    private int currentSize;

    public LRUCache(int maxSize){
        this.maxSize = maxSize;
        this.currentSize = 0;
        leastRecentlyUsed = new LRUNode<K, V>(null, null, null, null);
        mostRecentlyUsed = leastRecentlyUsed;
        cache = new HashMap<K, LRUNode<K, V>>();
    }

    public V get(K key){
        LRUNode<K, V> tempNode = cache.get(key);
        if (tempNode == null){
            return null;
        }
        // If MRU leave the list as it is
        else if (tempNode.key == mostRecentlyUsed.key){
            return mostRecentlyUsed.value;
        }

        // Get the next and previous nodes
        LRUNode<K, V> nextNode = tempNode.next;
        LRUNode<K, V> previousNode = tempNode.previous;

        // If at the left-most, we update LRU
        if (tempNode.key == leastRecentlyUsed.key){
            nextNode.previous = null;
            leastRecentlyUsed = nextNode;
        }

        // If we are in the middle, we need to update the items before and after our item
        else if (tempNode.key != mostRecentlyUsed.key){
            previousNode.next = nextNode;
            nextNode.previous = previousNode;
        }

        // Finally move our item to the MRU
        tempNode.previous = mostRecentlyUsed;
        mostRecentlyUsed.next = tempNode;
        mostRecentlyUsed = tempNode;
        mostRecentlyUsed.next = null;

        return tempNode.value;

    }

    public void put(K key, V value){
        if (cache.containsKey(key)){
            return;
        }

        // Put the new node at the right-most end of the linked-list
        LRUNode<K, V> myNode = new LRUNode<K, V>(mostRecentlyUsed, null, key, value);
        mostRecentlyUsed.next = myNode;
        cache.put(key, myNode);
        mostRecentlyUsed = myNode;

        // Delete the left-most entry and update the LRU pointer
        if (currentSize == maxSize){
            cache.remove(leastRecentlyUsed.key);
            leastRecentlyUsed = leastRecentlyUsed.next;
            leastRecentlyUsed.previous = null;
        }

        // Update cache size, for the first added entry update the LRU pointer
        else if (currentSize < maxSize){
            if (currentSize == 0){
                leastRecentlyUsed = myNode;
            }
            currentSize++;
        }
    }

}

