package com.entrepidea.java.concurrency.tests.synchronizer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BlockQueueTests {

    //Morgan Stanley, onsite, 05/09/12
    //TODO 1.Write a blockingqueue using wait / notify
    class BlockQueue<T>{
        private int capacity;
        private List<T> buffer;

        public BlockQueue(){
            this(10);
        }
        public BlockQueue(int capacity){
            this.capacity = capacity;
            buffer = new ArrayList<>();
        }


        public void put(T item) throws InterruptedException {
            synchronized (buffer){
                while(buffer.size()>=capacity){
                    System.out.println("I am full - waiting...");
                    buffer.wait();//wait() always in the loop
                }
                buffer.add(item);
                buffer.notifyAll();
            }
        }

        public T pop() throws InterruptedException {
            synchronized (buffer){
                while(buffer.size()==0){
                    System.out.println("I am empty - waiting...");
                    buffer.wait();
                }
                T ret = buffer.get(0);
                buffer.remove(0);
                buffer.notifyAll();
                return ret;
            }
        }

        public boolean isEmpty(){
            return buffer.isEmpty();
        }
    }

    class Producer implements Runnable{
        private BlockQueue<Integer> bq;
        private boolean slowDown;
        public Producer(BlockQueue<Integer> q, boolean s){
            this.bq = q;
            slowDown = s;
        }

        @Override
        public void run(){
            int i =0;
            while(true){
                try {
                    System.out.println("Add in: "+i);
                    bq.put(new Integer(i++));
                    if(slowDown)
                        TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable{
        private BlockQueue<Integer> bq;
        private boolean slowDown;
        public Consumer(BlockQueue<Integer> q, boolean s){
            this.bq = q;
            this.slowDown = s;
        }

        @Override
        public void run(){
            while(true){
                try {

                    Integer I = bq.pop();
                    System.out.println("popup: "+I);
                    if(slowDown)
                        TimeUnit.SECONDS.sleep(1);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void blockQueueTest(){
        BlockQueue<Integer> bq = new BlockQueue<>(10);
        boolean slowDown = true;
        Runnable task1 = new Producer(bq, slowDown);
        new Thread(task1).start();
        Runnable task2 = new Consumer(bq,!slowDown);
        new Thread(task2).start();
        while(true){;}
    }
}
