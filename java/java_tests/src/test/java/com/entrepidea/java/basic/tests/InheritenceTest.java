package com.entrepidea.java.basic.tests;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * Created by jonat on 3/8/2017.
 * This is an interview question from Morgan Stanley
 *
 * some notes: Be aware that in Java, every method in a class is born "virtual".
 * This is different from C++, in which only those functions with keywords "virtual" are virtual functions.
 * When a derived class is referenced by its base class type, and a virtual function is called (in Java, every method qualify),
 * the specific implementation of the method in the derived class is called, rather than that in the base class.
 *
 *
 */
public class InheritenceTest {
    public static Logger log = LoggerFactory.getLogger(InheritenceTest.class);

    class Base{
        public void print(){
            log.info("base");
        }

        public void foo(){
            print();
        }
    }

    class Derive extends Base{
        public void print(){
            log.info("derive");
        }
    }

    @Test
    public void test(){
        Base  obj = new Derive();
        obj.print();//should be "derive"
        obj.foo(); //should be "derive" too.

    }
}
