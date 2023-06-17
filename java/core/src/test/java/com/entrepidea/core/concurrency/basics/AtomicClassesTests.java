package com.entrepidea.core.concurrency.basics;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

/**
 * @Desc:
 * AtomicInteger, AtomicLong, AtomicReference, AtomicIntegerArray, etc are classes that are capable of performing atomic compounded operations
 * It takes advantage of hardwired computer CAS instructions so that extra locking is not required when used in multi-threading environment, still the integrety is preserved.
 *
 * @Source:
 * http://winterbe.com/posts/2015/05/22/java8-concurrency-tutorial-atomic-concurrent-map-examples/
 * */
public class AtomicClassesTests {

    Logger log = LoggerFactory.getLogger(AtomicClassesTests.class);

    /*
    * Interview questions:
    */

    //i++, is this statement thread safe, why? And how to fix it? (10/15/14, Markit on site)
    //No. This is a compound computation, in which thread-interferes can occur to comprise the data integrity or cause stale data. Use AtomicInteger to resolve the issue.

    //Morgan Stanley, onsite, 05/09/12
    //1.Write a thread safe class, getValue, incr (int val++) (sync on both, what if use volatile, or what if use AtomicInteger);
    //answer: sync or atomicInteger works. Volatile doesn't guarantee atomicity of compound computations.
    static class Foo{
        private int val;
        public Foo(int val){
            this.val = val;
        }
        public Foo(){
            this(0);
        }
        public  int getVal(){
            return val;
        }
        public synchronized void incr(){
            val++;
        }
    }

    @Test
    public void testFoo1(){
        final Foo foo = new Foo();
        final int THREAD_NUM = 10;
        ExecutorService es = Executors.newFixedThreadPool(THREAD_NUM);
        CountDownLatch cdl = new CountDownLatch(THREAD_NUM);
        for(int i=0;i<THREAD_NUM;i++){
            es.submit(()->{
                for(int k=0;k<1000;k++) //if the iteration is too small, like 100, sometime it works even w/o "synchronized", but it's a lucky illusion.
                    foo.incr();
                cdl.countDown();
            });

        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(10000, foo.getVal());
    }

    //2.If AtomicInteger is used, do u know how its method incrementAndSet work? oldVal = value; newVal = value; if(newVal != value)…
    //answer: I believe that internally the method use hard-wired CAS instructions. In which there is an expected value to compare with the retrieved one, if they are the same, meaning
    //no other threads update the variable in question, therefore we can go ahead updating it. Otherwise, we might set the expected to be the trtrieved one, and spin a while, and repeat the process above.
    @Test
    public void test2(){
        final int THREAD_NUM = 2;
        final AtomicInteger ai = new AtomicInteger(0);
        ExecutorService es = Executors.newFixedThreadPool(THREAD_NUM);
         final CountDownLatch cdl = new CountDownLatch(THREAD_NUM);
        for(int n=0;n<THREAD_NUM;n++) {
            es.submit(() -> {
                for (int i = 0; i < 1000; i++)
                    ai.incrementAndGet();
                cdl.countDown();
            });
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(THREAD_NUM*1000, ai.get());
    }

    /*
    *   End of interview questions.
    */


    //utility method
    private void holdoff(ExecutorService es){
        try {
            es.awaitTermination(10, TimeUnit.SECONDS);
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

    //The test was from the book "java特种兵", Cpt 5.3.4.
    //In multi-threading environment, result of AtomicInteger's increment and get remains constant and expected. Not the case for volatile variables.
    static class IndexFoo{
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
                    IndexFoo.index++;
                    IndexFoo.TEST_INTEGER.incrementAndGet();
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

        System.out.println("Atomic result: "+IndexFoo.TEST_INTEGER);
        System.out.println("Volatile result: "+IndexFoo.index);
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


    //test of AtomicIntegerArray. Operations on the elements of this instance is threadsafe, as demoed below
    //source: 实战Java高并发程序设计, 4.4.6
    static class AddTask implements Runnable{
        private AtomicIntegerArray arr;
        public AddTask(AtomicIntegerArray arr){
            this.arr = arr;
        }
        @Override
        public void run(){
            for(int i=0;i<10000;i++){
                arr.getAndIncrement(i%arr.length());
            }
        }
    }

    @Test
    public void testAtomicIntegerArray(){
        final AtomicIntegerArray arr = new AtomicIntegerArray(10);
        Thread ts[] = new Thread[10];
        for(int i=0;i<10;i++){
            ts[i] = new Thread(new AddTask(arr));
        }
        for(Thread t : ts){
            t.start();
        }
        for(Thread t: ts){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<arr.length();i++) {
            //System.out.println(arr.get(i));
            Assert.assertEquals(10000, arr.get(i));
        }


    }


    /**
     * BNP Paribas onsite 02/18/20
     * AtomicInteger, besides self incremental, what else operations?
     *
     * */
    //Compare and Set
}
