package org.example;

class LFUNode<P, U>{
    P key;
    U value;
    int frequency;
    LFUNode<P, U> prev;
    LFUNode<P, U> next;

    LFUNode(P key, U value) {
        this.key = key;
        this.value = value;
    }
}
