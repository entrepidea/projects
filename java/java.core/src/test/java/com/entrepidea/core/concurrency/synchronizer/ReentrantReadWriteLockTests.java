package com.entrepidea.core.concurrency.synchronizer;

import org.junit.Test;

import java.io.IOException;

/**
 * ReentrantReadWriteLock allows threads share a reading lock, but when writing starts, only one thread can acquire the
 * write lock. In another word, Reading lock is shared, while write lock is exclusive.
 *
 *
 * Note:
 * from interview "BGC phone interview, 12/02/19": Explain reentrantreadwritelock?
 * if you are interested in knowing the internal mechanism of ReentrantReadWriteLock, see this article in Zhihu (Chinese):
 * https://zhuanlan.zhihu.com/p/389679512
 *
 * Date: 09/22/21
* */

public class ReentrantReadWriteLockTests {

    //As a cross exam, what is the reentrancy in Lock? Reentrancy is default behavior for a lock, it's so designed as to
    //avoid dead lock. This stackoverflow post offers sample code to explain this concept:
    //https://stackoverflow.com/questions/27900332/reentrant-lock-java-concurrency-in-practice

    class Widget {
        public synchronized void doSomething() {
            System.out.println(toString() + ": calling superclass doSomething");
        }


    }

    class LoggingWidget extends Widget {
        public synchronized void doSomething() {
            System.out.println(toString() + ": calling subclass doSomething");
            super.doSomething(); //now the lock (LoggingWidget.class) has been acquired at line #35, this line,
                                 //if executed, is another attempt to acquire the same lock and it will fail if without reentrancy.
        }
    }

    @Test
    public void test() {
        //According to the stackoverflow link, if reentrancy is not in place, when super's synchronized method is called,
        //the sub tries to acquire a lock that is already held, and the sub is never able to get it, thus a deadlock.
        LoggingWidget lw = new LoggingWidget();
        lw.doSomething();
        System.out.println("another test...");
        Widget w = new LoggingWidget();
        w.doSomething();
    }
}
