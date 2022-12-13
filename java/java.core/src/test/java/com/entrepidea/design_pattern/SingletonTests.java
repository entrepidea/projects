package com.entrepidea.design_pattern;


import org.junit.Assert;
import org.junit.Test;

/**
 * refer to effective java 3rd ed.
 * Item 3: Enforce the singleton property with a private constructor or an enum type
*
 * note that DLC approach wasn't even mentioned.
 *
 * @Comment: Two popular idioms of Singleton are Initialization-on-demand holder idiom and Double Lock Check idiom.
 *
 * @Date: unknown, 04/16/20
 *
 * */
public class SingletonTests {

    //Initialization-on-demand holder idiom
    //Initialization-on-demand holder idiom
    //reference to wiki: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    static class SingleFoo{
        private SingleFoo(){}
        static class LazyHolder {
            private static final SingleFoo instance = new SingleFoo();
        }
        public static SingleFoo getInstance(){
            return LazyHolder.instance;
        }
    }
    @Test
    public void testLazyHolderIdiom(){
        SingleFoo foo = SingleFoo.getInstance();
        SingleFoo foo2 = SingleFoo.getInstance();
        Assert.assertTrue(foo==foo2);
        Assert.assertTrue(foo.getClass()==foo2.getClass());
    }

    /**
     *  Morgan Stanley Interview 05/17/17
     *  How Singleton is implemented?
     *
     *  10/01/14, 5:30PM, BofA phone interview with Wilson
     *  What design patterns can you name? What's singleton?
     *
     * 3 rounds of Interview with Neuberger Berman
     * give example of singleton implmentation in jdk
     * give examples of deisng patterns of classes in JDK
     */
    //TODO answer pending.

    /**
     * HSBC 12/17/13 interview (2nd round, video conf with London)
     * What design pattern did you use in real projects.
     * Elaborate Singleton in very details. How does DCL idiom work in details.
    */
    //TODO double-lock-check idiom

    /**
     * BNP Paribas on-site, 02/18/20
     * Most easy and reliable way of creating a singleton pattern?
     * */
    //use Enum.
    enum SingletonFoo{
        Instance;

        //APIs
        public void dummy(){
            System.out.println("I am a Singleton Dummy.");
        }

    }
    @Test
    public void test(){
        SingletonFoo.Instance.dummy();
    }

}


