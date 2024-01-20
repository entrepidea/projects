package com.entrepidea.core.concurrency.synchronizer;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockQueueTests {

    /**
     * Morgan Stanley, onsite, 05/09/12
     * Write a blockingqueue using wait / notify
     */
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

    /**
    * BNP Paribas, on-site, 02/18/2020
    * Write a Queue<T>. requirement: 1) Thread safe. 2)Callback is offered for both add() and poll()
    *
    * Date: 04/07/20, 04/08/20
    *
    */



    interface QueueListener {
        default void added(){}; //since Java 8 an interface can have default implementations for its methods.
        default void polled(){};
    }

    interface Queue<T> {
        void add(T e, QueueListener l) throws InterruptedException;
        T poll(QueueListener l) throws InterruptedException;
    }

    class BNPQueue<T> implements Queue<T> {

        private int capacity;
        private List<T> buf = new ArrayList<>();
        private Set<QueueListener> queueListeners = new HashSet<>();

        public BNPQueue(){
            this(16);
        }

        public BNPQueue(int cap){
            capacity = cap;
        }

        @Override
        public void add(T entity, QueueListener listener) throws InterruptedException{
            synchronized (buf){

                while(buf.size()==capacity){
                    buf.wait();
                }
                buf.add(entity);
                if(!queueListeners.contains(listener)){
                    queueListeners.add(listener);
                }
                for(QueueListener l : queueListeners){
                    l.added();
                }
                buf.notifyAll();
            }
        }

        @Override
        public T poll(QueueListener listener) throws InterruptedException{
            T ret;
            synchronized (buf){
                while(buf.size()==0){
                    buf.wait();
                }
                if(!queueListeners.contains(listener)){
                    queueListeners.add(listener);
                }
                for(QueueListener l : queueListeners){
                    l.polled();
                }
                ret = buf.remove(0);
                buf.notifyAll();
            }
            return ret;
        }
    }

    class ProduceTask implements Runnable, QueueListener {

        private Integer num;

        private Queue<Integer> queue;
        public ProduceTask(Queue<Integer> queue){
            this.queue = queue;
        }
        @Override
        public void added() {
            System.out.printf("[%s]: a random number:[%d]  has been added!\n",Thread.currentThread().getName(),num);
        }

        @Override
        public void run() {
            while(true) {
                try {
                    num = new Random().nextInt();
                    queue.add(num, this);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ConsumerTask implements Runnable, QueueListener{

        private Queue<Integer> queue;
        private Integer num;
        public ConsumerTask(Queue<Integer> queue){
            this.queue = queue;
        }

        @Override
        public void polled() {
            if(num!=null) {
                System.out.printf("[%s]: the number has been polled is:%d\n",Thread.currentThread().getName(), num);
            }
        }

        @Override
        public void run() {
            while(true){
                try {
                    num = queue.poll(this);
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Test
    public void testBNPQueue() throws InterruptedException {
        BNPQueue<Integer> q = new BNPQueue<>();
        Thread producer = new Thread(new ProduceTask(q));
        producer.start();

        Thread consumer = new Thread(new ConsumerTask(q));
        consumer.start();

        producer.join();
        consumer.join();
    }

    //an alternative way of writing up a block queue, using new Java Concurrency package's Lock&Condition
    class BNPQueue2<T> implements  Queue<T> {

        private int capacity;
        private final List<T> buf = new ArrayList<>();
        private final Lock lock = new ReentrantLock();
        private final Condition queueEmpty = lock.newCondition();
        private final Condition queueFull = lock.newCondition();
        private final Set<QueueListener> listeners = new HashSet<>();

        public BNPQueue2(){
            this(16);
        }

        public BNPQueue2(int cap){
            this.capacity = cap;
        }

        @Override
        public void add(T e, QueueListener listener) throws InterruptedException {
            lock.lock();
            try {
                while (buf.size() == capacity) {
                    queueFull.await();
                }
                if (buf.add(e)) {
                    queueEmpty.signalAll();
                }
                if (listener != null && !listeners.contains(listener)) {
                    listeners.add(listener);
                }
                for (QueueListener l : listeners) {
                    l.added();
                }
            }
            finally {
                lock.unlock();
            }
        }

        @Override
        public T poll(QueueListener listener) throws InterruptedException{
            T ret;
            lock.lock();
            try {
                while (buf.isEmpty()) {
                    queueEmpty.await();
                }
                ret = buf.remove(0);
                if (ret != null) {
                    queueFull.signalAll();
                }
                if (listener != null && !listeners.contains(listener)) {
                    listeners.add(listener);
                }
                for (QueueListener l : listeners) {
                    l.polled();
                }
            }
            finally {
                lock.unlock();
            }
            return ret;
        }
    }

    @Test
    public void testBNPQueue2()throws InterruptedException {
        BNPQueue2<Integer> q = new BNPQueue2<>();
        Thread producer = new Thread(new ProduceTask(q));
        producer.start();

        Thread consumer = new Thread(new ConsumerTask(q));
        consumer.start();

        producer.join();
        consumer.join();

    }
}
