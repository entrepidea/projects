package com.entrepidea.java.concurrency.tests.basics;

import java.util.Random;
import java.util.UUID;
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
 * Be sure to read JCP 3.3.3
  *
 */



public class ThreadLocalTests {

    /**
        * Code below shows that the SerialNumber instance is passed to 8 thread but each keeps a copy of it, so manipulating it won't be affected by other threads
        *
        * @reference: http://www.builder.com.cn/2007/0529/404695.shtml
        *
     */
    class SerialNumber {
        private ThreadLocal<Integer> tl;
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


	/*
	* 12. Can thread has its own local members? Where are they saved?

13. Explain ThreadLocal. How is it used in real project?

14. Difference b/w process and thread.

15. How do processes communicate with each other?

16. If I have a primary process running on a node, two other processes running on two other nodes as standby. How do you implement the fail-over?


TODO: 10/15/14, Markit on site
	*
	* **/

	//TODO How to make a variable visible only to the current thread? (Blackrock, phone interview with Kenny Ma, 08/18/14)


    //BNP Paribas onsite, Jersey City, GWT UI programmer position, 10/14/2014
    //ThreadLocal, how do you use it? How is it used with a session?
    //The code below seems working, but once the Thread.sleep is removed, it seems becoming problematical.
    //TODO testing more...
    class SessionIdGenerator {
        private ThreadLocal<UUID> sessionId;
        public void generate(){
            sessionId = ThreadLocal.withInitial(()->UUID.randomUUID());
        }
        public String getId(){
            return sessionId.get().toString();
        }
    }

    class SessionHandler implements Runnable{
        private Logger log = LoggerFactory.getLogger(SessionHandler.class);
        private SessionIdGenerator session;
        public SessionHandler(SessionIdGenerator s){
            session = s;
            session.generate();
        }

        public void begin() throws InterruptedException{
            Thread.sleep(new Random().nextInt(100));
            log.info("Starting ... [Thread={}, session id={}]", Thread.currentThread().getName(), session.getId());
        }

        public void wip() throws InterruptedException{
            Thread.sleep(new Random().nextInt(100));
            log.info("Working in progress ... [Thread={}, session id={}]", Thread.currentThread().getName(), session.getId());
        }

        public void terminating() throws InterruptedException{
            Thread.sleep(new Random().nextInt(100));
            log.info("Session ends... [Thread={}, session id={}]", Thread.currentThread().getName(), session.getId());
        }


        @Override
        public void run() {
            try {
                begin();
                wip();
                terminating();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void threadLocalDemo() throws InterruptedException{
        //System.out.println(UUID.randomUUID().toString());
        int threadNumber;
        ExecutorService service = Executors.newFixedThreadPool(threadNumber = Runtime.getRuntime().availableProcessors());
        SessionIdGenerator generator = new SessionIdGenerator();
        for(int i=0;i<threadNumber;i++){
            service.submit(new SessionHandler(generator));
        }

        service.awaitTermination(5, TimeUnit.SECONDS);

    }



}
