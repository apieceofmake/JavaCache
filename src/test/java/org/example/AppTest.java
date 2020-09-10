package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    LRUCache<Integer, String> sampleCache = new LRUCache<>(2);


    @Test
    public void testNoDataStored() {
        assertEquals(sampleCache.get(1), null);
    }

    @Test
    public void testNumEntriesBelowMaxSize() {
        System.out.println("Test case: n <= maxSize ");
        sampleCache.put(1, "a1");
        assertEquals(sampleCache.get(1), "a1");
        assertEquals(sampleCache.get(2), null);
        sampleCache.put(2, "a2");
        assertEquals(sampleCache.get(1), "a1");
        assertEquals(sampleCache.get(2), "a2");
    }

    @Test
    public void testNumEntriesEqualsMaxSize() {
        sampleCache.put(1, "a1");
        sampleCache.put(2, "a2");
        sampleCache.put(3, "a3");
        assertEquals(sampleCache.get(1), null);
        assertEquals(sampleCache.get(2), "a2");
        assertEquals(sampleCache.get(3), "a3");
    }

    @Test
    public void testEviction() {
        sampleCache.put(1, "a1");
        sampleCache.put(2, "a2");
        assertEquals(sampleCache.get(1), "a1");
        sampleCache.put(3, "a3");
        assertEquals(sampleCache.get(1), "a1");
        assertEquals(sampleCache.get(2), null);
        assertEquals(sampleCache.get(3), "a3");
    }
}
