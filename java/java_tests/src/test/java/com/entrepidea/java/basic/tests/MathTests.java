package com.entrepidea.java.basic.tests;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * This is to test math related API in Java
 * */
public class MathTests {

    //https://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
    @Test
    public void testDecimalPlace(){
        int yourScale = 12;
        System.out.println(BigDecimal.valueOf(0.42344534534553453453-0.42324534524553453453).setScale(yourScale, BigDecimal.ROUND_HALF_UP));
    }
}
