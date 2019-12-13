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
                    bq.add(i);
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
                    System.out.println("consumer: "+bq.poll());
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
}
