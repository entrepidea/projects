package com.entrepidea.java.concurrency.lock;

import java.util.ArrayList;
import java.util.List;

/**
 * @Desc: this is an interview question from Well Fargo on 11/29/19. It's asked to implement a blockingQueue, after I did it using intrinsic lock,
 * he further asked to do it using explicit lock
 *
 * @comments:
 *
 * */
public class BlockingQueue<T> {
    private List<T> buffer;
    private int capacity;
    public BlockingQueue(int c){
        capacity = c;
        buffer = new ArrayList<>(capacity);
    }
    public BlockingQueue(){
        this(16); //set default value to be 16
    }

    //public APIs
    public T poll() throws InterruptedException{
        synchronized (buffer){
            while(buffer.isEmpty()){
                buffer.wait();
            }
            T ret = buffer.remove(0);
            buffer.notifyAll();
            return ret;
        }
    }

    public void add(T ele) throws InterruptedException {
        synchronized (buffer){
            while(buffer.size() == capacity){
                buffer.wait();
            }
            buffer.add(ele);
            buffer.notifyAll();
        }
    }
}
