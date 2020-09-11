package org.example;

public class CacheBuilder<K,V>  {

    public ConfigurableCache<K,V> buildCache(int maximumSize, boolean useLFUevictionStrategy){
        if (useLFUevictionStrategy) {
            return new LFUCache<>(maximumSize);
        } else {
            return new LRUCache<>(maximumSize);
        }

    }
}
