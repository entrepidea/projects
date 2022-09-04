package com.entrepidea.core.concurrency.archive;

import org.junit.Test;

import java.util.Random;

/**
 * Created by jonat on 10/5/2017.
 *
 * The code is borrowed from here: https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html
 * It explains how wait/notify is constructed to create a producer-consumer pattern.
 *
 */

public class InterThreadCommTests {

    //shared resource class
    class Drop<T>{
        private boolean isEmpty = true;
        private T value;
        public synchronized T take() throws InterruptedException {
            while(isEmpty){
                wait();
            }
            isEmpty = true;
            notifyAll();
            return value;
        }

        public synchronized  void put(T val) throws InterruptedException {
            while(!isEmpty){
                wait();
            }
            isEmpty = false;
            value = val;
            notifyAll();
        }
    }

    class Producer implements Runnable{
        private Drop<Integer> drop;

        public Producer(Drop<Integer> drop){
            this.drop = drop;
        }

        @Override
        public void run() {
            try {
                Integer i = new Random().nextInt();
                System.out.println("Putting in: "+i);
                drop.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable{
        private Drop<Integer> drop;

        public Consumer(Drop<Integer> drop){
            this.drop = drop;
        }

        @Override
        public void run() {
            try {
                System.out.println("taking out: "+drop.take());
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test(){
        Drop<Integer> drop = new Drop<>();
        Producer p = new Producer(drop);
        Consumer c = new Consumer(drop);
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);
        t2.start();
        t1.start();
    }


    //TODO: What's thread starvation? (Blackrock, phone interview with Kenny Ma, 08/18/14)


    //TODO: What's the wait() condition? (Blackrock, phone interview with Kenny Ma, 08/18/14)

    //TODO: How to wake up a sleeping thread?(Blackrock, phone interview with Kenny Ma, 08/18/14)

    //Instinet corp, phone interview
    //TODO Whats the difference b/w wait and sleep?
}
