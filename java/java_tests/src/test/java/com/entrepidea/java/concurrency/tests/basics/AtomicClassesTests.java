package com.entrepidea.java.concurrency.tests.basics;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * These are tests focusing on the usage of atomic classes such as AtomicInteger...
 * See also: http://winterbe.com/posts/2015/05/22/java8-concurrency-tutorial-atomic-concurrent-map-examples/
 * */
public class AtomicClassesTests {

    Logger log = LoggerFactory.getLogger(AtomicClassesTests.class);

    //utility method
    private void holdoff(ExecutorService es){
        try {
            es.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch(InterruptedException e){}
    }

    //AtomicInteger internally uses CAS to guarantee atomicity and data integrity, with a better performance than synchronized block.
    @Test
    public void testAtomicInteger(){
        AtomicInteger ai = new AtomicInteger(0);
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i=0;i<10;i++){
            IntStream.range(0,1000).forEach(x->es.submit(ai::incrementAndGet));
        }
        holdoff(es);

        log.info("Final number is: {}", ai.get());
    }

    //updateAndGet applies customized algorithm on collections
    //the code snippet below runs 1000 tasks and each of them increments an integer (initilized with 0) by 2
    @Test
    public void testUpdateAndGet(){
        AtomicInteger ai = new AtomicInteger(0);
        ExecutorService es = Executors.newCachedThreadPool();
        IntStream.range(0,1000).forEach( i -> es.submit(()->ai.updateAndGet(n->n+2)));
        holdoff(es);
        log.info("The final number is {}", ai.get());
    }


    //TODO i++, is this statement thread safe, why? And how to fix it? (10/15/14, Markit on site)

    //Morgan Stanley, onsite, 05/09/12
    //TODO 1.Write a thread safe class, getValue, incr (int val++) (sync on both, what if use volatile, or what if use AtomicInteger);
    //TODO 2.If AtomicInteger is used, do u know how its method incrementAndSet work? oldVal = value; newVal = value; if(newVal != value)…


    //The test was from the book "java特种兵", Cpt 5.3.4.
    //In multi-threading environment, result of AtomicInteger's increment and get remains constant and expected. Not the case for volatile variables.
    static class Foo{
        public static volatile int index;
        public final static AtomicInteger TEST_INTEGER = new AtomicInteger(0);
    }
    @Test
    public void testAtomicIncrementAndGetAndVolatileCompare(){
        final CountDownLatch cdl = new CountDownLatch(1);
        final Thread[] threads = new Thread[10];
        for(int i=0;i<10;i++){
            threads[i] = new Thread(()->{
                try{
                    cdl.await();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }

                for(int j=0;j<10;j++){
                    Foo.index++;
                    Foo.TEST_INTEGER.incrementAndGet();
                }

            });
            threads[i].start();
        }
        try {
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        cdl.countDown();
        for(Thread t : threads){
            try {
                t.join();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println("Atomic result: "+Foo.TEST_INTEGER);
        System.out.println("Volatile result: "+Foo.index);
    }

    //Below is a test of AtomicReference - the value it hosts can be checked and updated by any thread safely
    //code is from the book "java特种兵", Cpt 5.3.4.
    @Test
    public void testAtomicRefCompareAndSet(){
        final CountDownLatch cdl = new CountDownLatch(1);
        final AtomicReference<String> atomicReference = new AtomicReference<>("ABC");
        final Random RANDOM_NUMBER = new Random();
        final Thread[] threads = new Thread[20];
        for(int i=0;i<20;i++){
            final int num = i;
            threads[i] = new Thread(()->{
                String oldVal = atomicReference.get();
                try {
                    cdl.await();
                    Thread.sleep(RANDOM_NUMBER.nextInt() & 1000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                atomicReference.compareAndSet(oldVal, oldVal+num);

            });
            threads[i].start();
        }

        try{
            Thread.sleep(200);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

        cdl.countDown();

        for(Thread t : threads){
            try {
                t.join();
            }
            catch(InterruptedException e ){
                e.printStackTrace();
            }
        }

        System.out.println(atomicReference.get());

    }
}
