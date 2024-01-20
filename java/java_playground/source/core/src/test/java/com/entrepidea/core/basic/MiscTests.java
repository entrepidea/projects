package com.entrepidea.core.basic;

import org.junit.Assert;
import org.junit.Test;

/*
* This class include unit tests of knowledge points that don't fit into any categories
* */
public class MiscTests {
    //TODO Have you done Unit tests? Are you familiar with Mockito? (Blackrock, phone interview with Kenny Ma, 08/18/14)

    //TODO 3. What's the skill is the most important for a developer? (phone Interview with Ted from BNP Paribas, 09/26/14)

    //10/08/14 phone interview with BNP Paribas, GWT UI developer position, Jersey City
    //TODO 5. How to tune GC parameters
    //TODO 16. How do you do unit checkBalancedBinaryTree and integration checkBalancedBinaryTree? Have you used mockit?
    //TODO 17. Maven. The "Runtime" or "Test" inside Dependency tag, what are they used for?


    //BNP Paribas onsite, Jersey City, GWT UI programmer position, 10/14/2014
    //TODO 8. What's your JVM setting for Mms Mmx and permGen? Why do you set it that way?
    //TODO 11. I have a java application running fine for the first two days, then it start slowing down a lot. How do you troubleshooting it? What's your approach?


    /*
        onsite BofA interview, continuance of the phone interview on 10/01/14
    * TODO 1) code on Eclipse

        There is a folder containing stock files, each line of which includes info as below, and delimiter is "\t".
        date in the format "YYYY-MM-DD"
        indicator (BUY/SELL), this is counter party BUY from or SELL to BofA
        symbol
        counter party
        quantity
        price

        There are 100 such files and each file contains 10,000 lines.

        In the same folder there is also a short file, marks.txt in which there are two columns in each line showing the symbol and market price, for example:
        AAPL 350.00

        Task: reading the file and figure out the long positions that BofA hold on the stocks, list top 20 in the descendant order with regard to value (quantity*price) and the respective market value. For instance, BofA might buy/sell AAPL, if the net is long, you have to figure it out and count it as required above.


        TODO 2) white board

                Create a high efficient cache solution for computation, it must be thread safe.

                public interface Computable <K, V> {
                  public V compute(K k);
                }

                public class Cache <K,V> implements Computable<K,V>{
                    //the Caculator below is a delegate to do the heavy lift, but the result can be cached so it's supposed to call only once
                    private Computable<K,V> caculator = new Caculator<K,V>();
                    private Map<K,V> cache = new HashMap<K,V>();

                    public V compute(K k){
                            if(cache.contains(k){
                              return cache.get(k);
                            }
                            V val = caculator.compute(k);
                             cache.put(k,val);
                             return val;
                    };
                }

        What's the problem with the above implementation? How to make it highly performant and thread safe?
        I wrote a good implementation with synchronization and he complimented "nearly perfect" but he continue to request to create a "lock-free" solution, I couldn't do it and he wrote one using FutureTask, I don't remember the details. And he also suggested a even better one using AKKA's actor.
        I didn't say but IMHO the common idea that synchronization is a performance penalty is just a urban legend - with the nowaday's modern Java compiler the overhead of synchronization is just trivial.


        TODO    3) White board Singlton pattern.
        TODO    4) Eclipse a Lock implementation, use only AtomicBoolean
                public interface Lock {
                  public void lock();
                  public void unlock();
                }

                public class LockImpl implements Lock{
                  private AtomicBoolean bool = new AtomicBoolean();
                  public void lock(){
                    //What's in here?
                  }

                  public void unlock(){
                    //what's in here?
                  }
                }

    *
    * */

    //Morgan Stanley phone interview, 05/14/18
    //TODO 1. A simple "hello world" c or C++ program, when it's compiled for Windows, can the binary be executed on Linux platform?
    //TODO 8. Explain the memory model of a JVM. PermGen, young Gen, that's kind of stuff.
    //TODO 10. Besides Heap and Stack what other area in there in the memory;
    //TODO 11. What's inside Memory model.
    //TODO 12. Have you heard of "Happen-before"? can you explain?

    //ICAP Java Swing position - phone interview 08/17/15
    //TODO TCP/IP, explains on high level.
    //TODO what's multicast?
    //TODO how to create a round button in Java?
    //TODO how to make sure that the button only responds to the click inside the circle, but ignore the click outside the circle but inside the rectangle area?


    //Renaissance Technologies, 05/08/14, 3PM
    //TODO Write a Integer class mimic that one in JDK?
    //TODO What’s the size of an object of that self-designed Integer class inside the memory?
    //TODO ArrayList and LinkedList? Pro and con? Whats the size of the reference in LinkedList's node? JVM 64 bit v.s 32 bit.


    //The test below seems super strange but explainable.
    //a, b falling into the range of a cache in which the value is cache thus references are the same
    //c,d didn't fall into the range of the cache therefore they represent difference references.
    //look the source of Integer#valueOf, then you will know.
    @Test
    public void testIntegerCompare(){
        Integer a = 1;
        Integer b = 1;
        Assert.assertTrue(a==b);

        Integer c = 200;
        Integer d = 200;
        Assert.assertFalse(c==d);

    }
}
