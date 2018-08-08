package com.entrepidea.algo.tests.misc;

import org.junit.Test;

/**
 * Given a positive integer num, write a function which returns True if num is a perfect square else False.

 Note: Do not use any built-in library function such as sqrt.

 Example 1:

 Input: 16
 Returns: True
 Example 2:

 Input: 14
 Returns: False
 *
 * */
public class PerfectSqre {
    private static long[] range(long low, long high, long num){
        long span = (long)(high - low)/2;

        long tmp = span+low;
        if(tmp*tmp==num){
            return new long[]{tmp, -1};
        }
        if(high-1<=low){
            return new long[]{low,high};
        }
        if(tmp*tmp>num){
            high = tmp;
        }
        else{
            low = tmp;
        }
        return range(low, high, num);
    }

    @Test
    public void testPerfectSqre(){
        boolean matched = false;
        long start = System.nanoTime();
        long num = 1000000;
        long[] r = range(0,num,num);
        for(long i : r){
            if(i*i==num){
                matched = true;
                break;
            }
        }
        System.out.println(matched);
        System.out.println(System.nanoTime() - start);
    }
}
