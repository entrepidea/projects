package com.entrepidea.java.concurrency.tests.basics;

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
 * These tests are about volatile variables.
 * volatile variables are mostly relevant in the multi-threading environment, it helps keep the data integration, to some extends.
 * volatile guarantees data's visibility, tackling execution re-ordering issues, but fails to maintain operational atomicity.
 * Given that, one of the appropriate scenarios of using volatile variables is a toggle flag, where the operation is most likely a simple assignment operation: set the flag.
 *
 * */
public class VolatileTests {

    private final Logger log = LoggerFactory.getLogger(VolatileTests.class);


    /**
    volatile variables don't guarantee atomicity. inc++ is not an atomic operation.
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


}
