package org.example;

import java.util.HashMap;

class LFUCache<K, V> implements ConfigurableCache<K, V> {

    /*
    This implementation of LFUCache should have a time complexity of O(1)
    */

    int maxSize;
    HashMap<K, LFUNode<K,V>> keyToNodeMap = new HashMap<>();
    HashMap<Integer, NodesHavingThisFrequency<K,V>> freqToNodeListMap = new HashMap<>();
    int minimumFrequency = 1;

    public LFUCache(int maxSize) {
        this.maxSize = maxSize;
    }

    public V get(K key) {
        LFUNode<K,V> node = keyToNodeMap.get(key);
        // if data Entry exists, then increment it's frequency
        if (node != null) {
            incrementFrequency(node);
            return node.value;
        } else {
            return null;
        }
    }

    public void put(K key, V value) {

        if(maxSize <= 0) return;

        if(!keyToNodeMap.containsKey(key)){
            LFUNode<K,V> node = new LFUNode<>(key, value);                            //Create new node

            if(keyToNodeMap.size() == maxSize){                                          //Cache is full
                LFUNode<K,V> lastNodeFromLFUListToRemove = freqToNodeListMap.get(minimumFrequency)
                        .removeLastNode();                              //Remove LFU node from lastNodeFromLFUListToRemove
                keyToNodeMap.remove(lastNodeFromLFUListToRemove.key);                                 //Remove LFU node from cache
            }

            incrementFrequency(node);                                                   //Add to frequency map
            keyToNodeMap.put(key, node);                                                  //Add to cache
        }
    }

    private void incrementFrequency(LFUNode<K,V> node){

        int oldFrequency = node.frequency;
        int newFrequency = oldFrequency + 1;                                            //Increment frequency

        if(freqToNodeListMap.containsKey(oldFrequency)){                                   //Frequency already exists
            NodesHavingThisFrequency<K,V> listOfNodesHavingOldFrequency = freqToNodeListMap.get(oldFrequency);      //Get frequency linkedlist
            listOfNodesHavingOldFrequency.remove(node);                                    //Remove current node
            if(oldFrequency == minimumFrequency &&                                    //If this frequency was the minimum freq.
                    listOfNodesHavingOldFrequency.length == 0){                                        //and no node is having this freq anymore
                minimumFrequency++;                                                     //Increment minimum frequency
            }

        }


        node.frequency = newFrequency;                                                  //Set new frequency to node
        NodesHavingThisFrequency<K,V> listOfNodesHavingNewFrequency =                     //Get or create the LinkedList for
                freqToNodeListMap.getOrDefault(                                                //this new frequency
                        newFrequency, new NodesHavingThisFrequency<>()
                );
        listOfNodesHavingNewFrequency.add(node);                                                   //Add node to the freq linkedlist
        freqToNodeListMap.put(newFrequency, listOfNodesHavingNewFrequency);                           //Put freq linkedlist to freqNodeDLLMap
    }



}

