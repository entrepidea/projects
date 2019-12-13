package com.entrepidea.java.concurrency;

/*
* This class includes all tests that don't seem fit in any categories, but still related to Concurrency
* */
public class MiscTests {
    //TODO What's the difference b/w process and thread? (Blackrock, phone interview with Kenny Ma, 08/18/14)

    //TODO If there is a low priority thread working for too long, how a JVM will handle it and give the CPU to a higher priority thread? (Blackrock, phone interview with Kenny Ma, 08/18/14)

    //10/01/14, 5:30PM, BofA phone interview with Wilson
    //TODO 10. Explain Executor framework and Threadpool

    //Instinet corp, phone interview
    //TODO Have you use the ExecuteService, what does it do?

    //HSBC 12/17/13 interview (2nd round, video conf with London)
    //TODO How do you scale up your applicaiton? In another word, how do you implement parallelism in your application.
    //TODO in the newFixedThreadPool, it's initial capacity is 10. Now there are 100 requests (runnable tasks) coming in, analyze what would be the possible consequence? For example, what happens to the queue, what if the queue is full, what'll happen? How to handle it when it takes place?

    //3 rounds of Interview with Neuberger Berman
    //TODO 24. can you synchnoize consturctor and why ?
    //my explanation: I think not. synchronizing a method means that "this" was implicitly used as a lock. "this" won't be there before the constructor is fully realized.
    //need to look up for more.

    //MOrgan Stanley Interview 07-30-13, 2:30 with James Lin
    /*
    TODO 2. Class Test{
    Synchronized m1();
    static synchronized m2();
    }

    T t = new T();
    Thread1 uses t.m1(), Thread2 uses t.m2(), what's the implication.
    */
}
