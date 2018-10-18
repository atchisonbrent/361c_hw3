package queue;

import java.util.concurrent.locks.ReentrantLock;

public class LockQueue implements MyQueue {

    private ReentrantLock enqLock , deqLock;
    private Node head;
    private Node tail;

  public LockQueue() {
      head = new Node(null);
      tail = head;
      enqLock = new ReentrantLock();
      deqLock = new ReentrantLock();
  }
  
  public boolean enq(Integer value) {
    if (value == null) { throw new NullPointerException(); }
    enqLock.lock();
    try {
        Node e = new Node(value);
        tail.next = e;
        tail = e;
    } finally { enqLock.unlock(); }
    return true;
  }
  
  public Integer deq() {
      Integer result;
      deqLock.lock();
      try {
          if (head.next == null) { return null; }
          result = head.next.value;
          head = head.next;
      } finally { deqLock.unlock(); }
    return result;
  }
  
  protected class Node {
	  public Integer value;
	  public Node next;
		    
	  public Node(Integer x) {
		  value = x;
		  next = null;
	  }
  }
}
