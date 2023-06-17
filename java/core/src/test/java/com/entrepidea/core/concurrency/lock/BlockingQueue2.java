package com.entrepidea.core.concurrency.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Desc:
 * this is going to use explicit lock Lock and Lock#newCondition to create a blocking queue
 * @Date: 01/05/20
 *
 * @Note: I wrote this w/o any reference and the test seemed working. Better to take a online example as reference.
 * */
public class BlockingQueue2<T> {
    private volatile List<T> buffer;
    private int capacity;

    private Lock lock;
    private Condition empty;
    private Condition full;

    public BlockingQueue2(int c){
        capacity = c;
        buffer = new ArrayList<>();
        lock = new ReentrantLock();
        empty = lock.newCondition();
        full = lock.newCondition();
    }
    public BlockingQueue2(){
        this(16); //set default value to be 16
    }

    //public APIs
    public T take() throws InterruptedException{
        T ret = null;
        //if the buffer is empty, while looping
        while(buffer.isEmpty()){//if buffer is empty, no reading is allowed
            empty.await();
        }
        lock.lockInterruptibly();
        try {
            ret = buffer.remove(0); //the buffer is no longer full if it used to be, send signal to other threads
            full.signal();
        }
        finally {
            lock.unlock();
        }
        return ret;
    }

    public void put(T ele) throws InterruptedException {
        lock.lockInterruptibly();
        while(buffer.size()==capacity){ //if the buffer is full, no writing is allowed
            full.await();
        }
        try{
            buffer.add(ele);
            empty.signal(); //the buffer is no longer empty if it used to be, send signal to other threads.
        }finally {
            lock.unlock();
        }
    }
}
