package com.entrepidea.java.concurrency.lock;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: this is going to use explicit lock Lock and Lock#newCondition to create a blocking queue
 * */
public class BlockingQueue2<T> {
    private List<T> buffer;
    private int capacity;
    public BlockingQueue2(int c){
        capacity = c;
        buffer = new ArrayList<>(capacity);
    }
    public BlockingQueue2(){
        this(16); //set default value to be 16
    }

    //public APIs
    public T poll() throws InterruptedException{
        return null;
    }

    public void add(T ele) throws InterruptedException {

    }
}
