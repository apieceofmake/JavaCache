package org.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // new
        System.out.println("Testing Cache Builder");

        System.out.println("Build LFU Cache with maxSize = 3");
        CacheBuilder<Integer,String> cacheBuilder = new CacheBuilder<>();
        configurableCache <Integer,String> cacheLFUsize3 = cacheBuilder.buildCache(3, true);

        System.out.println("Build LRU Cache with maxSize = 3");
        configurableCache <Integer,String> cacheLRUsize3 = cacheBuilder.buildCache(3, false);

        quickTest(cacheLRUsize3);
        quickTest(cacheLFUsize3);

    }

    public static void quickTest(configurableCache <Integer,String> cache){
        cache.put(0, "a0");
        cache.put(1, "a1");
        cache.put(2, "a2");
        cache.put(3, "a3");
        cache.put(4, "a4");
        cache.get(2);
        cache.get(2);
        cache.put(5, "a5");
        //cache3.put(6, "a6");

        System.out.println(cache.get(0));
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
        System.out.println(cache.get(5));
        System.out.println(cache.get(6));
        System.out.println(cache.get(7));
    }
}
