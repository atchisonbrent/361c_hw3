package q5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedListSet implements ListSet {
    // you are free to add members
    private Node head;

    public FineGrainedListSet() {
        // implement your constructor here
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);


    }

    public boolean add(int value) {
        // implement your add method here
        head.lock.lock();
        Node previous = head;
        try{
            Node current = previous.next;
            current.lock.lock();
            try{
                while(current.value < value){
                    previous.lock.unlock();
                    previous = current;
                    current = current.next;
                    current.lock.lock();
                }
                if(current.value == value){
                    return false;
                }
                Node node = new Node(value);
                node.next = current;
                previous.next = node;
                return true;
            }
            finally{
                current.lock.unlock();
            }
        }
        finally{
            previous.lock.unlock();
        }
    }

    public boolean remove(int value) {
        // implement your remove method here
        Node previous = null;
        Node current;
        head.lock.lock();
        try{
           previous = head;
           current = previous.next;
           current.lock.lock();
           try{
               while(current.value < value){
                   previous.lock.unlock();
                   previous = current;
                   current = current.next;
                   current.lock.lock();
               }
               if(current.value == value){
                   previous.next = current.next;
                   return true;
               }
               return false;
           }
           finally{
               current.lock.unlock();
           }
        }
        finally{
            previous.lock.unlock();
        }
    }

    public boolean contains(int value) {
        // implement your contains method here
        head.lock.lock();
        Node previous = head;
        try{
            Node current = previous.next;
            current.lock.lock();
            try{
                while(current.value < value){
                    previous.lock.unlock();
                    previous = current;
                    current = current.next;
                    current.lock.lock();
                }
                if(current.value == value){
                    return true;
                }
                return false;
            }
            finally{
                current.lock.unlock();
            }
        }
        finally{
            previous.lock.unlock();
        }
    }

    protected class Node {
        public Integer value;
        public Node next;
        public Lock lock = new ReentrantLock();

        public Node(Integer x) {
            value = x;
            next = null;
        }
    }

    /*
    return the string of list, if: 1 -> 2 -> 3, then return "1,2,3,"
    check simpleTest for more info
    */
    public String toString() {
        String result = "";
        Node previous = head.next;
        while (previous.next != null) {
            result = result + previous.value + ",";
            previous = previous.next;
        }

        return result;
    }
}
