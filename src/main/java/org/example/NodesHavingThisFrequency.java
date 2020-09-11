package org.example;

class NodesHavingThisFrequency<K,V>{

    LFUNode<K,V> head, tail;
    int length;

    //Add a node to top
    public void add(LFUNode<K,V> node) {



        if (head == null) {                                                               //Empty list
            //Remove old pointers
            node.prev = null;
            node.next = null;

            head = node;
            tail = node;
        } else {
            node.next = head;                                                         //Forward linking
            head.prev = node;                                                         //Backward linking
            head = node;
        }
        length++;
    }

    //Remove a node
    public void remove(LFUNode<K,V> node) {

        if (node == head) {                                                               //Need to remove head node
            if (node == tail) {                                                           //Tail node is the same (list size = 1)
                tail = null;                                                            //Make tail null
            }
            head = head.next;                                                         //Make head point to the next node
        } else {                                                                         //Need to remove later node
            node.prev.next = node.next;                                               //Forward linking

            if (node == tail) {                                                           //Need to remove tail node
                tail = node.prev;                                                     //Point tail to prev node
            } else {
                node.next.prev = node.prev;                                           //Backward linking
            }
        }
        length--;

    }

    //Remove last node
    LFUNode<K,V> removeLastNode() {
        LFUNode<K,V> tailNode = tail;
        if (tailNode != null) {                                                         //LastNode exists
            remove(tailNode);
        }
        return tailNode;
    }
}

