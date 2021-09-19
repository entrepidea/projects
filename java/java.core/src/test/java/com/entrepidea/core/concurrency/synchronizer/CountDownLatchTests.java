package com.entrepidea.core.concurrency.synchronizer;

/**
 * CountDownLatch is a synchronizer working in such a way as: a specific thread set up a Latch and wait for other
 * threads to play out before it can proceed. A working scenario is that on server side, the process wait for certain
 * services to complete before it can move on. Some said that CountDownLatch is useful to wait for events to take place.
 *
 * @Source: https://stackoverflow.com/questions/17827022/how-is-countdownlatch-used-in-java-multithreading
 *
 * @Date: 09/19/21
 *
 * @Note: pay attention to the difference b/w CountDownLatch and CyclicBarrier.
 *
 * */
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTests {

    public static class ProcessThread implements Runnable {

        CountDownLatch latch;
        long workDuration;
        String name;

        public ProcessThread(String name, CountDownLatch latch, long duration){
            this.name= name;
            this.latch = latch;
            this.workDuration = duration;
        }


        public void run() {
            try {
                System.out.println(name +" Processing Something for "+ workDuration/1000 + " Seconds");
                Thread.sleep(workDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+ " completed its works");
            //when task finished.. count down the latch count...

            // basically this is same as calling lock object notify(), and object here is latch
            latch.countDown();
        }
    }

    //this test is very simple and its from the source: https://stackoverflow.com/questions/17827022/how-is-countdownlatch-used-in-java-multithreading
    //date: 09/19/21
    @Test
    public void test1(){
        // Parent thread creating a latch object
        CountDownLatch latch = new CountDownLatch(3);

        new Thread(new ProcessThread("Worker1",latch, 2000)).start(); // time in millis.. 2 secs
        new Thread(new ProcessThread("Worker2",latch, 6000)).start();//6 secs
        new Thread(new ProcessThread("Worker3",latch, 4000)).start();//4 secs


        System.out.println("waiting for Children processes to complete....");
        try {
            //current thread will get notified if all chidren's are done
            // and thread will resume from wait() mode.
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All Process Completed....");
        System.out.println("Parent Thread Resuming work....");
    }

    //test2:
    //BGC phone interview, 12/02/19
    //If one of the threads  fails and ends prematurely, how will that affect the countDownLatch?
    //my answer: since the countDown never reaches 0, the main thread might just hang in there unless a timeout option was added.
    public static class BadboyThread implements Runnable {

        CountDownLatch latch;
        long workDuration;
        String name;

        public BadboyThread(String name, CountDownLatch latch, long duration){
            this.name= name;
            this.latch = latch;
            this.workDuration = duration;
        }


        public void run() {
            try {
                System.out.println(name +" Processing Something for "+ workDuration/1000 + " Seconds");
                Thread.sleep(workDuration);
                throw new InterruptedException();
                //System.out.println(name+ " completed its works");
                //when task finished.. count down the latch count...
                // basically this is same as calling lock object notify(), and object here is latch
                //latch.countDown();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test2(){
        // Parent thread creating a latch object
        CountDownLatch latch = new CountDownLatch(4);

        new Thread(new ProcessThread("Worker1",latch, 2000)).start(); // time in millis.. 2 secs
        new Thread(new ProcessThread("Worker2",latch, 6000)).start();//6 secs
        new Thread(new ProcessThread("Worker3",latch, 4000)).start();//4 secs
        new Thread(new BadboyThread("Badboy",latch, 4000)).start();//4 secs

        System.out.println("waiting for Children processes to complete....");
        try {
            //current thread will get notified if all chidren's are done
            // and thread will resume from wait() mode.
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All Process Completed....");
        System.out.println("Parent Thread Resuming work....");
    }
}
