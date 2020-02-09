package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

public class LE326PowerOf3 {

    //most intuitive way
    private boolean isPowOfThree(int num){
        if(num==1){
            return true;
        }
        int n = 1;
        while(n<=num){
            n *= 3;
            if(n==num){
                return true;
            }
        }
        return false;
    }
    @Test
    public void test(){
        Assert.assertTrue(isPowOfThree(27));
        Assert.assertTrue(isPowOfThree(9));
        Assert.assertFalse(isPowOfThree(45));
    }

    //solution2, use base 3
    private boolean isPowOfThree2(int num){
        String str = Integer.toString(num, 3);
        char[] chars = str.toCharArray();
        if(chars.length==1 && chars[0]=='1'){
            return true;
        }
        for(int i=1;i<chars.length;i++){
            if(chars[i]!='0'){
                return false;
            }
        }
        return true;
    }

    @Test
    public void test2(){
        Assert.assertTrue(isPowOfThree2(27));
        Assert.assertTrue(isPowOfThree2(9));
        Assert.assertTrue(isPowOfThree2(1));
        Assert.assertFalse(isPowOfThree2(45));
    }

    //solution 3, use recursion
    private boolean isPowOfThree3(int num){
        if(num==1){
            return true;
        }
        int remain = num%3;
        if(remain!=0){
            return false;
        }
        return isPowOfThree3(num/3);
    }

    @Test
    public void test3(){
        Assert.assertTrue(isPowOfThree3(81));
        Assert.assertTrue(isPowOfThree3(27));
        Assert.assertTrue(isPowOfThree3(9));
        Assert.assertTrue(isPowOfThree3(1));
        Assert.assertFalse(isPowOfThree3(45));
    }
}
