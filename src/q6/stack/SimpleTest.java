package stack;

import org.junit.Assert;
import org.junit.Test;

import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

public class SimpleTest {

    ReentrantLock lock = new ReentrantLock();

    @Test
    public void testLockFreeStack() {
        LockFreeStack stack = new LockFreeStack();
        Stack<Integer> javaStack = new Stack<Integer>();
        makeThread(stack, javaStack);
        try {
            while (true) {
                Integer i = stack.pop();
                Integer j = javaStack.pop();
                Assert.assertEquals(i, j);
            }
        }
        catch (EmptyStack e) {  }
    }

    private void makeThread(LockFreeStack stack, Stack<Integer> javaStack) {
        Thread[] threads = new Thread[3];
        threads[0] = new Thread(new MyThread(0, 1000, stack, javaStack));
        threads[1] = new Thread(new MyThread(0, 2000, stack, javaStack));
        threads[2] = new Thread(new MyThread(1000, 3000, stack, javaStack));
        threads[1].start(); threads[0].start(); threads[2].start();

        for (Thread thread : threads) {
            try { thread.join(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private class MyThread implements Runnable {

        int begin;
        int end;
        LockFreeStack stack;
        Stack<Integer> javaStack;

        MyThread(int begin, int end, LockFreeStack stack, Stack<Integer> javaStack) {
            this.begin = begin;
            this.end = end;
            this.stack = stack;
            this.javaStack = javaStack;
        }

        @Override
        public void run() {
            for (int i = begin; i <= end; i++) {
                lock.lock();
                stack.push(i);
                javaStack.push(i);
                lock.unlock();
            }
            try {
                for (int i = begin; i < end / 2; i++) {
                    lock.lock();
                    stack.pop();
                    javaStack.pop();
                    lock.unlock();
                }
            }
            catch (EmptyStack e) {  }
        }
    }

}
