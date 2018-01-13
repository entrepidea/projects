package com.entrepidea.java.basic.tests;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jonat on 3/8/2017.
 * This is an interview question from Morgan Stanley
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
