package q5;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

public class LockFreeListSet implements ListSet {

  private ConcurrentSkipListSet<Integer> set = new ConcurrentSkipListSet<>(Comparator.comparingInt(Integer::intValue));
	
  public LockFreeListSet() {
      /* No Constructor */
  }
	  
  public boolean add(int value) {
      if (!set.contains(value)) {
          return set.add(value);
      }
      return false;
  }
	  
  public boolean remove(int value) {
      return set.remove(value);
  }
	  
  public boolean contains(int value) {
      return set.contains(value);
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
    return set.toString().replaceAll("\\s|\\[|]","") + ",";
  }
}
