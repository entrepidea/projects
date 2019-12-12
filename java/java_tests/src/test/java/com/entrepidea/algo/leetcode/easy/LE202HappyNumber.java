package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @Source: https://leetcode.com/problems/happy-number/solution/
 * @Desc: find out the happy number.
 *
 * Created by jonat on 11/5/2019.
 */
public class LE202HappyNumber {

    private int foo(int x, Set<Integer> s){
        //
        if(s.contains(x)){
            return x;
        }
        s.add(x);
        //break the number into array
        int sum = 0;
        while(x!=0){
            sum += Math.pow(x%10,2);
            x /=10;
        }
        return foo(sum, s);
    }

    @Test
    public void test(){
        Set<Integer> s = new HashSet<>();
        int i = foo(19,s);
        Assert.assertEquals(1,i);
        s = new HashSet<>();
        Assert.assertEquals(58,foo(116,s));
        s = new HashSet<>();
        Assert.assertEquals(1,foo(7,s));
    }

    private int getNext(int n){
        int sum = 0;
        while(n!=0){
            sum += Math.pow(n%10,2);
            n /= 10;
        }
        return sum;
    }

    private boolean areYouHappy(int n){

        Set<Integer> s = new HashSet<>();

        while(n!=1 && !s.contains(n)){
            s.add(n);
            n = getNext(n);
        }
        return n==1;
    }

    @Test
    public void test2(){
        Assert.assertTrue(areYouHappy(7));
        Assert.assertTrue(areYouHappy(19));
        Assert.assertFalse(areYouHappy(116));
    }
}
