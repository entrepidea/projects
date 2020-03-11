package com.entrepidea.core.concurrency.basics;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Desc:
 * The latest update of a volatile variable is visible across all threads sharing it.
 * volatile guarantees data's visibility, tackling execution re-ordering issues, but fails to maintain operational atomicity.
 * Given that, one of the appropriate scenarios of using volatile variables is a toggle flag, where the operation is most likely a simple assignment operation: set the flag.
 *
 * */
public class VolatileTests {

    private final Logger log = LoggerFactory.getLogger(VolatileTests.class);


    /**
        volatile variables don't guarantee atomicity
        (it's better put that volatile doesn't guarantee the atomicity of the compound operations such as inc++; for assignment to a single primitive type variable, including long or double type, I think the atomicity of those operations are still guaranteed)
        Once if multiple threads tapping on a shared volatile variable with no additional protection, the final result is undetermined.
        as shown below we expected the value to be 10,000, it normally won't happen
    */
    private volatile int inc = 0;
    private void increase(){
        inc++;
    }
    @Test
    public void testAtomicity(){
        List<Thread> l = new ArrayList<>();
        for (int i =0;i<10;i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            });
            l.add(t);
            t.start();
        }

        //this is a low level approach of awaiting all 10 threads to complete its respective task
        for(Thread t : l){
            try {
                t.join();
            }
            catch(InterruptedException e ){}
        }

        log.info("The final number is {}", inc);
    }

    /**
     * As a contrast, synchronized block guarantees both the visibility and operational atomicity of a variable in a multi-threading
     * environment, and the result is what we expected.
     * pay attention to how we use the new Executor framework to kick off Runnables
     * */
    private int inc2=0;
    private synchronized void increase2(){
        inc2++;
    }
    @Test
    public void testAtomicity2() throws InterruptedException{
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<10;i++) {
            es.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase2();
                }
            });
        }

        es.shutdown();

        //another way to await all tasks to complete before proceeding to the end of the program
        //see also: https://stackoverflow.com/questions/7939257/wait-until-all-threads-finish-their-work-in-java
        es.awaitTermination(1, TimeUnit.MINUTES);

        log.info("The final number is {}", inc2);
    }

    /**
     * synchronized block is, however, heavy. We are better off and can achieve the same goal by using AtomicInteger.
     * In another word, comparing to volatile variables, AtomicReference types are better in addressing atomicity.
     *
     * for more Atomic class examples, see AtomicClassesTests.java
     *
     * */
    @Test
    public void testAtomicity3() throws InterruptedException{
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i=0;i<10;i++){
            es.submit(()->{
                for(int j=0;j<1000;j++){
                    atomicInteger.incrementAndGet();
                }
            });
        }
        es.shutdown();

        es.awaitTermination(1, TimeUnit.MINUTES);

        log.info("The final number is: {}",atomicInteger.get());
    }


    //The following is to test the visibility of volatile variable. If removing the "volatile" keyword, the thread in question will run forever.
    //from "实战Java高并发程序设计", 2.3
    public static class NoVisibility {
        private static volatile boolean ready;
        private static int number;
        private static class ReadingTask implements Runnable {
            @Override
            public void run() {
                while (!ready);
                System.out.println(number);
            }
        }
    }
    @Test
    public void testVisibility() throws InterruptedException {
        new Thread(new NoVisibility.ReadingTask()).start();
        Thread.sleep(1000);
        NoVisibility.number = 42;
        NoVisibility.ready = true;
        Thread.sleep(2000);
    }

    //TODO 10. Explain JMM and happen-before model. (10/15/14, Markit on site)

    /*
    *
    *   Morgan Stanley phone interview, 05/14/18
        TODO 13.
            int a=100;
            volatile int x;
            if(x==1){
                print a;
            }

        In a single thread and multi-threading env, what would be result (or results) be? what if x is volatile and not volatile, what are the result(s)?

    * */


    //10/01/14, 5:30PM, BofA phone interview with Wilson
    //TODO 11. Explain volatile and transient


    //Citi onsite (Jersey City) 08/20/13, from 09AM to 11:30AM.
    //TODO Volatile in depth. How is the volatile variable updated? Does a thread sync up its local with the "main memory" of a volatile variable? How does volatile differ from a full-brown synchronization?
}
