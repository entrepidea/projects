package com.entrepidea.java.basic.tests;

import org.junit.Test;

/**
 * This is a collection of Regular expression tests
 * */
public class RegexTests {
    @Test
    public void testSplit(){
        String testStr = "Hello, Ellen!";
        String[] rets= testStr.split("[eE]");
        for(String s : rets){
            System.out.println(s);
        }
    }
}
