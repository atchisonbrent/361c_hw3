package q5;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseGrainedListSet implements ListSet {
  private LinkedList<Integer> list = new LinkedList<>();
  private ReentrantLock lock = new ReentrantLock();
	
  public CoarseGrainedListSet() {
	// implement your constructor here	  
  }
  
  public boolean add(int value) {
      lock.lock();
      Node n = new Node(value);
      try {
          if (list.contains(n.value)) return false;
          return list.add(n.value);
      } finally {
          lock.unlock();
      }
  }
  
  public boolean remove(int value) {
      lock.lock();
      Node n = new Node(value);
      try {
          return list.remove(n.value);
      } finally {
          lock.unlock();
      }
  }
  
  public boolean contains(int value) {
      lock.lock();
      Node n = new Node(value);
      try {
          return list.contains(n.value);
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

      @Override
      public String toString() {
          return value.toString();
      }
  }

  /*
  return the string of list, if: 1 -> 2 -> 3, then return "1,2,3,"
  check simpleTest for more info
  */
  public String toString() {
      list.sort(Comparator.comparingInt(Integer::intValue));
      return list.toString().replaceAll("\\s|\\[|\\]","") + ",";
  }
}
