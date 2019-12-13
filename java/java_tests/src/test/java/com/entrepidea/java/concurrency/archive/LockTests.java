package com.entrepidea.java.concurrency.archive;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/*
* These tests address basic locks from different angles.
* */



public class LockTests {
    //supporting class.
    static class Resource {
        private static Object lock1 = new Object();
        private Object lock2 = new Object();
        private Thread currentThread;

        public Resource(Thread t){
            currentThread =t;
        }

        public void doSomething() throws InterruptedException {
            synchronized (lock1){
                System.out.println(currentThread.getName()+ ": Entered critical section locked by lock1...");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(currentThread.getName()+ ": leaving critical section locked by lock1, now");
            }
            //this lock2 instance is created
            synchronized (lock2){
                System.out.println(currentThread.getName()+ ": Entered critical section locked by lock2...");
                TimeUnit.SECONDS.sleep(5);
                System.out.println(currentThread.getName()+ ": leaving critical section locked by lock2, now");

            }
        }
    }
    @Test
    public void testMultiThreadAccessResource() throws InterruptedException {
        for(int i=0;i<10;i++) {
            Thread t = new Thread() {

                //resource object is created per thread, that makes the lock2 in that class NOT unique hence useless as a shared lock among threads
                //on the other hand, static lock1 is shared among all resource instance, hence can serve as a lock perfectly.
                final Resource res = new Resource(this);
                @Override
                public void run() {
                    try {
                        res.doSomething();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }

        TimeUnit.MINUTES.sleep(10);

    }

    //even we create Resource instance once and pass it to multiple Thread, non-final lock2 is still not encouraged to use
    //see this post: https://stackoverflow.com/questions/6910807/synchronization-of-non-final-field


    /*
    *   Morgan Stanley Interview 05/17/17
    *   TODO Sync block, Sync method and static sync method, difference?
        inside func A() is a sync block that calls func B(); inside func B() is a sync block (but on a different lock) that calls func A(). Thread1 call A(), thread2 call B(), will it be possible? Why?
        In a class are two syn methods A() and B(), can we call B inside A, like sync A{ B(); } ?
    * */



    /**
     * class MyClass {
     synchronized A();
     synchronized B();
     }

     Thread T1{
     MyClass my;
     my.A();
     }


     Thread T1{
     MyClass my;
     my.B();
     }

     TODO can the above two threads run simultaneously? what if both A and B add static in front? What if one of them add static in front? (10/15/14, Markit on site)
     *
     * */


    /**
     * Morgan Stanley Interview 05/17/17
     * inside func A() is a sync block that calls func B(); inside func B() is a sync block (but on a different lock) that calls func A().
     * Thread1 call A(), thread2 call B(), will it be possible? Why?
     *
     * result:
     * a endless invocation of each other of func A and B, resulting in StackOverflowError
     * */
    class Foo{
        Object lock1 = new Object();
        Object lock2 = new Object();
        public void funcA(){
            synchronized (lock1){
                funcB();
            }
        }
        public void funcB(){
            synchronized (lock2){
                funcA();
            }
        }
    }
    @Test
    public void test() throws InterruptedException {
        final Foo f = new Foo();
        Thread t1 = new Thread(()->{f.funcA();});
        Thread t2 = new Thread(()->{f.funcB();});
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(10);
    }

    /**
     * Morgan Stanley Interview 05/17/17
     * In a class are two syn methods A() and B(), can we call B inside A, like sync A{ B(); } ?
     * TODO
     * they use the same lock and it's a reentrance. doable
     * */

    //TODO 3. i++, is this statement thread safe, why? And how to fix it? (10/15/14, Markit on site)

    /*
    *
    * 10/15/14, Markit on site
        I have a class Foo{
            A;
            B;
        }

        two threads, T1, T2. In T1, run A#setParent(B), in T2, B#setChild(A).
        In another words, there is a circular references here. Will it lead to memory leak?

        TODO

    *
    * */
}
