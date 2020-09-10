package org.example;

public interface ConfigurableCache<K,V> {
    public V get(K key);
    public void put(K key, V value);
}
