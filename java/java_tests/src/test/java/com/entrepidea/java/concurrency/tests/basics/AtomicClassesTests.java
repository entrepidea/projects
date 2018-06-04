package com.entrepidea.java.concurrency.tests.basics;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * These are tests focusing on the usage of atomic classes such as AtomicInteger...
 * See also: http://winterbe.com/posts/2015/05/22/java8-concurrency-tutorial-atomic-concurrent-map-examples/
 * */
public class AtomicClassesTests {

    Logger log = LoggerFactory.getLogger(AtomicClassesTests.class);

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

}
