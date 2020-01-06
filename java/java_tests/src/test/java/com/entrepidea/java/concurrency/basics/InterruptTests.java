package com.entrepidea.java.concurrency.basics;

import org.junit.Test;

/**
 * @Desc:
 * there are three methods involving interrupting a running thread: interrupt(), isInterrupted() and interrupted(), the last one is a static method of class Thread.
 * interrupt() set a interrupt flag, then doing nothing.
 * isInterrupted() is to check that interrupt flag, then you might want to do something once it's true.
 * interrupted() is to check and clean the flag.
 *
 * @Note:
 * this is a decent way to stop a running thread.
 * Another way is to create a volatile flag and a stopMe() method to flag it.
 * (stop() method is too brutal in the sense that it releases all the locks it acquired right away, leaving any shared resources an inconsistent state).
 *
 * @Source:
 * 实战Java高并发程序设计
 *
 * @Date: 12/18/19
 *
 * */
public class InterruptTests {
    @Test
    public void test() throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(true){
                Thread.yield();
            }
        });
        t1.start();
        Thread.sleep(2000);
        t1.interrupt(); //this line only set the flag. Since the thread t1 doesn't have code to respond to this change, no effect is inflicted into the running of t1
    }

    @Test
    public void test1() throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(true){
                if(Thread.currentThread().isInterrupted()){//now the thread is doing something in response upon being flagged.
                    System.out.println("Interrupted!");
                    break;
                }
            }
            Thread.yield();
        });
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();
    }
}
