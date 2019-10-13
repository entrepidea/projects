package com.entrepidea.java.design_pattern.tests;


import org.junit.Assert;
import org.junit.Test;

/**
 * Morgan Stanley Interview 05/17/17
 * How Singleton is implemented?
 *
 * refer to effective java 3rd ed.
 * Item 3: Enforce the singleton property with a private constructor or an enum type
*
 * note that DLC approach wasn't even mentioned.
 *
 * @Comment: Two popular idioms of Singleton are Initialization-on-demand holder idiom and Double Lock Check idiom.
 * */
public class SingletonTests {

    //Initialization-on-demand holder idiom
    @Test
    public void testLazyHolderIdiom(){
        SingleFoo foo = SingleFoo.getInstance();
        SingleFoo foo2 = SingleFoo.getInstance();
        Assert.assertTrue(foo==foo2);
        Assert.assertTrue(foo.getClass()==foo2.getClass());
    }
    //TODO double-lock-check idiom

    //10/01/14, 5:30PM, BofA phone interview with Wilson
    //TODO 8. What design patterns can you name? What's singleton?

    //HSBC 12/17/13 interview (2nd round, video conf with London)
    //TODO What design pattern did you use in real projects.
    //TODO Elaborate Singleton in very details. How does DCL idiom work in details.

    //3 rounds of Interview with Neuberger Berman
    //TODO 10. give example of singleton implmentation in jdk
    //TODO 11. give examples of deisng patterns of classes in JDK

}

//Initialization-on-demand holder idiom
//reference to wiki: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
class SingleFoo{
    private SingleFoo(){}
    static class LazyHolder {
        private static final SingleFoo instance = new SingleFoo();
    }
    public static SingleFoo getInstance(){
        return LazyHolder.instance;
    }
}
