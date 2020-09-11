package org.example;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class AppTest
{
    /**
     * Unit test for CacheBuilder Class
     * Usage:
     *  1) Create an instance of the CacheBuilder<K,V>, where K - type of the keys, V - type of the objects stored in a Cache.
     *  2) Use method ConfigurableCache<K,V> buildCache(int maximumSize, boolean useLFUevictionStrategy) to create
     *  a Cache with the following parameters:
     *     size = maximumSize,
     *     LFU eviction strategy ( useLFUevictionStrategy = true)
     *     or
     *     LRU eviction strategy ( useLFUevictionStrategy = false).
     *
     *     The example below creates a LFU Cache with maxSize = 5. In this case all tests passed, except testLRUEviction()
     *
     *     If you try to change flag useLFUevictionStrategy to FALSE, then LRU Cache with maxSize = 5 will be created.
     *     In this case all tests passed, except testLFUEviction()
     */

    //Build LFU Cache with maxSize = 5
    CacheBuilder<Integer,String> cacheBuilder = new CacheBuilder<>();
    ConfigurableCache <Integer,String> sampleCache = cacheBuilder.buildCache(5, true);

    @Test
    public void testNoDataStored() {
        assertEquals(sampleCache.get(1), null);
    }

    @Test
    public void testNoEvictionOccurs() {
        sampleCache.put(1, "a1");
        sampleCache.put(2, "a2");
        assertEquals(sampleCache.get(1), "a1");
        assertEquals(sampleCache.get(2), "a2");
        assertEquals(sampleCache.get(3), null);
        assertEquals(sampleCache.get(4), null);
        assertEquals(sampleCache.get(5), null);

        sampleCache.put(3, "a3");
        sampleCache.put(4, "a4");
        sampleCache.put(5, "a5");
        assertEquals(sampleCache.get(1), "a1");
        assertEquals(sampleCache.get(2), "a2");
        assertEquals(sampleCache.get(3), "a3");
        assertEquals(sampleCache.get(4), "a4");
        assertEquals(sampleCache.get(5), "a5");

    }

    @Test
    public void testLRUEviction() {
        sampleCache.put(1, "a1");
        sampleCache.put(2, "a2");
        sampleCache.put(3, "a3");
        sampleCache.put(4, "a4");
        sampleCache.put(5, "a5");
        // this part should work the same way, regardless strategy used (LRU or LFU)
        sampleCache.put(6, "a6");
        assertEquals(sampleCache.get(1), null);
        assertEquals(sampleCache.get(2), "a2");
        assertEquals(sampleCache.get(3), "a3");
        // get #3 again
        sampleCache.get(3);
        // and again
        sampleCache.get(3);
        // now #3 have a frequency = 3

        assertEquals(sampleCache.get(4), "a4");
        assertEquals(sampleCache.get(5), "a5");
        assertEquals(sampleCache.get(6), "a6");
        // get #2 again, now #2 is MRU, but the frequency(#2) < frequency(#3) !
        assertEquals(sampleCache.get(2), "a2");
        sampleCache.put(1, "a1");
        // now #2 is MRU, so #3 is LRU and #3 should be evicted if LRU strategy is used
        // If LFU strategy is used, then #2 should be evicted and this test will not pass for LFU strategy
        // Check that #1..#5, except #3 are stored
        assertEquals(sampleCache.get(3), null);
        assertEquals(sampleCache.get(2), "a2");
        assertEquals(sampleCache.get(1), "a1");
        assertEquals(sampleCache.get(4), "a4");
        assertEquals(sampleCache.get(5), "a5");
        assertEquals(sampleCache.get(6), "a6");
    }

    @Test
    public void testLFUEviction() {
        sampleCache.put(1, "a1");
        sampleCache.put(2, "a2");
        sampleCache.put(3, "a3");
        sampleCache.put(4, "a4");
        sampleCache.put(5, "a5");

        sampleCache.get(1);
        sampleCache.get(1);
        sampleCache.get(1);

        sampleCache.get(2);
        sampleCache.get(2);

        sampleCache.get(3);
        sampleCache.get(3);

        sampleCache.get(4);
        sampleCache.get(4);
        // now entity #5 is LRU, but LFU
        sampleCache.get(5);

        // cache is full,  add a new entry
        sampleCache.put(6, "a6");

        // entity #5 is  LFU, thus it should be evicted
        assertEquals(sampleCache.get(1), "a1");
        assertEquals(sampleCache.get(2), "a2");
        assertEquals(sampleCache.get(3), "a3");
        assertEquals(sampleCache.get(4), "a4");
        assertEquals(sampleCache.get(5), null);
        assertEquals(sampleCache.get(6), "a6");

    }
}
