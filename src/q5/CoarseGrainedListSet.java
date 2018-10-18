package q5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseGrainedListSet implements ListSet {
    // you are free to add members
    private Node head;
    private Lock lock = new ReentrantLock();


    public CoarseGrainedListSet() {
        // implement your constructor here
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);
    }

    public boolean add(int value) {
        // implement your add method here
        Node previous;
        Node current;
        lock.lock();
        try {
            previous = head;
            current = previous.next;
            while (current.value < value) {
                previous = current;
                current = previous.next;
            }
            if (value == current.value) {
                return false;
            }
            else {
                Node node = new Node(value);
                node.next = current;
                previous.next = node;
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(int value) {
        // implement your remove method here
        Node previous;
        Node current;
        lock.lock();
        try {
            previous = head;
            current = previous.next;
            while (current.value < value) {
                previous = current;
                current = previous.next;
            }
            if (value == current.value) {
                previous.next = current.next;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }

    }

    public boolean contains(int value) {
        // implement your contains method here
        Node previous;
        Node current;
        lock.lock();
        try {
            previous = head;
            current = previous.next;
            while (current.value < value) {
                previous = current;
                current = previous.next;
            }
            if (value == current.value) {
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    protected class Node {
        public Integer value;
        public Node next;

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
