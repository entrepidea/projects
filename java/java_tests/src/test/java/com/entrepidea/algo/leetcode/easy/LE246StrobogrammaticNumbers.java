package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: see the explanation of Strobogrammatic  number from here. https://www.geeksforgeeks.org/strobogrammatic-number/
 * @Note: TODO it seems the below  implemmentation is problematic- need revisit
 * @Date: 12/08/19
 * */
public class LE246StrobogrammaticNumbers {

    private boolean compatible(int x, int  y){
        return (x==6 && y==9) || (x==9&&y==6);
    }

    private boolean isStrobogrammatic(int num, int digitLen){
            int mid = digitLen/2;
            int step = 0;
            int[] arr = new int[digitLen];
            while(num!=0){
                int  temp= num%10;
                if(temp!=0&&temp!=1&&temp!=8&temp!=6&temp!=9){
                    return false;
                }
                arr[digitLen-1- step] = num%10;
                num /=10;
                step++;
            }
            for(int i=0;i<mid;i++){
                if(arr[i]!=arr[digitLen-1-i] && !compatible(arr[i],arr[digitLen-1-i])){
                    return false;
                }
            }
            return true;
    }
    @Test
    public void test(){
        Assert.assertFalse(isStrobogrammatic(1234,4));
        Assert.assertTrue(isStrobogrammatic(1691,4));
    }

    private  int[] range(int digitLen){
        int[] ret = new int[2];
        int min =1;
        int max = 10;
        for(int i=1;i<digitLen;i++){
            min = min*10;
            max = max*10;
        }
        ret[0] = min;
        ret[1] = max;
        return ret;
    }
    @Test
    public void test2(){
        int[] x = range(4);
        Assert.assertEquals(1000, x[0]);
        Assert.assertEquals(10000, x[1]);

        x =  range(1);
        Assert.assertEquals(1, x[0]);
        Assert.assertEquals(10, x[1]);

        x = range(2);
        Assert.assertEquals(10, x[0]);
        Assert.assertEquals(100, x[1]);
    }

    @Test
    public void test3(){
        int[]  x = range(4);
        for(int i=x[0];i<x[1];i++){
            if(isStrobogrammatic(i,4)){
                System.out.println(i);
            }

        }
    }
}
