package queue;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue implements MyQueue {

    private AtomicReference<Node> head = new AtomicReference<>();
    private AtomicReference<Node> tail = new AtomicReference<>();

  public LockFreeQueue() {
      Node n = new Node(null);
      head.set(n);
      tail.set(n);
  }

  public boolean enq(Integer value) {
      Node newNode = new Node(value);
      while (true) {
          Node currTail = tail.get();
          Node tailNext = currTail.next.get();
          if (currTail == tail.get()) {
              if (tailNext != null) {
                  tail.compareAndSet(currTail, tailNext);
              } else {
                  if (currTail.next.compareAndSet(null, newNode)) {
                      tail.compareAndSet(currTail, newNode);
                      return true;
                  }
              }
          }
      }
  }

  public Integer deq() {
      while (true) {
          Node first = head.get();
          Node last = tail.get();
          Node next = first.next.get();
          if (first == head.get()) {
              if (first == last) {
                  if (next == null) { return null; }
                  tail.compareAndSet(last, next);
              } else {
                  Integer value = next.value;
                  if (head.compareAndSet(first, next)) {
                      return value;
                  }
              }
          }
      }
  }

  protected class Node {
	  public Integer value;
	  public AtomicReference<Node> next;

	  public Node(Integer x) {
		  value = x;
		  next = null;
	  }
  }
}
