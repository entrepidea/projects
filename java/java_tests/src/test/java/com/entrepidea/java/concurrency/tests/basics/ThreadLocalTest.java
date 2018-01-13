package com.entrepidea.java.concurrency.tests.basics;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @description ThreadLocal enables each thread to own a local copy of some variables thus prevent data tampering in a multi-threading environment.
 *
 * Synchronization is heavy, and extreme care is required. In many cases ThreadLocal provides a simple alternative.
  *
 */



public class ThreadLocalTest {

    /**
        * Code below shows that the SerialNumber instance is passed to 8 thread but each keeps a copy of it, so manipulating it won't be affected by other threads
        *
        * @reference: http://www.builder.com.cn/2007/0529/404695.shtml
        *
     */
    class SerialNumber {
        private ThreadLocal<Integer> tl = null;
        public SerialNumber(Integer id){
            tl = ThreadLocal.withInitial(()->id); //static method withInital was introduced since 1.8
        };
        public void increaseId(){
            tl.set(tl.get()+1);
        }

        public Integer getId(){
            return tl.get();
        }
    }

    class Task implements Runnable{
        private Logger log = LoggerFactory.getLogger(Task.class);
        private SerialNumber sn;
        public Task(SerialNumber sn){
            this.sn = sn;
        }

        @Override
        public void run(){
            for(int j=0;j<3;j++) {
                sn.increaseId();
                log.info("{}, sn={}",Thread.currentThread(),sn.getId());
            }
        }
    }

	@Test
	public void test() throws InterruptedException {

	    int threadNum = Runtime.getRuntime().availableProcessors();
		ExecutorService es = Executors.newFixedThreadPool(threadNum);
		SerialNumber sn = new SerialNumber(0);
		for(int i=0;i<threadNum;i++){//kick off three threads
			Task t = new Task(sn);
			es.submit(t);
		}
		es.shutdown(); // no more thread is allowed in.

		es.awaitTermination(1, TimeUnit.MINUTES); //block until all threads complete their tasks. 1 min should be long enough
	}

}
