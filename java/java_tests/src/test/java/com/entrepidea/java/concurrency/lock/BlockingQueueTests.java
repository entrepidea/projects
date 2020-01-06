package com.entrepidea.java.concurrency.lock;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BlockingQueueTests {
    class Producer implements Runnable{
        BlockingQueue<Integer> bq;
        Producer(BlockingQueue<Integer> bq){
            this.bq = bq;
        }
        @Override
        public void run(){
            for(int i=0;i<17;i++){
                try {
                    System.out.println("Producer: "+i);
                    bq.put(i);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable{
        BlockingQueue<Integer> bq;
        Consumer(BlockingQueue<Integer> bq){
            this.bq = bq;
        }
        @Override
        public void run(){
            try {
                while(true) {
                    System.out.println("consumer: "+bq.take());
                }
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test(){
        BlockingQueue<Integer> bq = new BlockingQueue<>(3);
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new Producer(bq));
        service.submit(new Consumer(bq));
        try {
            service.awaitTermination(2, TimeUnit.SECONDS);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void test2() { //test the BlockingQueue implementation that uses explicit lock
        ExecutorService es = Executors.newCachedThreadPool();
        final BlockingQueue2<Integer> bq2 = new BlockingQueue2<>(10);

        es.submit(() -> { //fast producing thread
            int i=0;
            while(true) {
                try {
                    System.out.println("Producer: "+i);
                    bq2.put(i++);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        es.submit(() -> { //slow consuming thread
            while (true) {
                try {
                    System.out.println("consumer: "+bq2.take());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try{
            es.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch(InterruptedException e){
            //eat me
        }
    }
}
