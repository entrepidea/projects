package com.entrepidea.algo.tests.misc;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @Source: https://leetcode.com/problems/count-primes/
 * @Desc: Count the number of prime numbers less than a non-negative number, n.

 * Created by jonat on 11/6/2019.
 */
public class LE204PrimaryNumbers {

    private boolean isPrime(int x){
        for(int i=2;i<=x/2;i++){
            if(x%i==0){
                return false;
            }
        }
        return true;
    }
    private int[] foo(int n){
        return IntStream.range(2,n).filter(x->isPrime(x)).toArray();
    }

    @Test
    public void test(){
        Arrays.stream(foo(10)).forEach(System.out::println);
    }
}
