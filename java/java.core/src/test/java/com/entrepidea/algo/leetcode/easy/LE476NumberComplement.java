package com.entrepidea.algo.leetcode.easy;

import com.google.common.collect.Streams;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @Desc:
 * Given a positive integer num, output its complement number. The complement strategy is to flip the bits of its binary representation.
 * @Source: https://leetcode.com/problems/number-complement/
 * @Date: 08/26/20
 * */
public class LE476NumberComplement {

    private int pow(int base, int exp){
        int ret = 1;
        for(int i=1;i<=exp;i++){
            ret *=base;
        }
        return ret;
    }

    @Test
    public void testPow(){
        Assert.assertEquals(1, pow(2,0));
        Assert.assertEquals(2, pow(2,1));
        Assert.assertEquals(4, pow(2,2));
        Assert.assertEquals(8, pow(2,3));
        Assert.assertEquals(16, pow(2,4));
        Assert.assertEquals(9, pow(3,2));
        
    }

    private int[] num2Bin(int n){
        int[] ret=null;
        if(n<=1){
            ret = new int[1];
            ret[0] = n;
            return ret;
        }
        int count=0;
        int remainder = n;
        while(pow(2,count)!=remainder){
            if(pow(2, count)>remainder){
                if(ret==null) {
                    ret = new int[count];
                }
                int len = ret.length;
                ret[len-count]=1;
                remainder -= pow(2,count-1);
                count=0;
            }
            else {
                count++;
            }
        }
        if(ret==null){
            ret = new int[count+1];
            ret[0]=1;
        }
        else {
            ret[ret.length - count - 1] = 1;
        }
        return ret;
    }

    @Test
    public void testNum2Bin(){
        Assert.assertArrayEquals(new int[]{0}, num2Bin(0));
        Assert.assertArrayEquals(new int[]{1,1}, num2Bin(3));
        Assert.assertArrayEquals(new int[]{1,0,0}, num2Bin(4));
        Assert.assertArrayEquals(new int[]{1,0,1}, num2Bin(5));
        Assert.assertArrayEquals(new int[]{1,1,0}, num2Bin(6));
        Assert.assertArrayEquals(new int[]{1,1,1}, num2Bin(7));
        Assert.assertArrayEquals(new int[]{1,0,0,0}, num2Bin(8));
        Assert.assertArrayEquals(new int[]{1,0,1,0}, num2Bin(10));
        Assert.assertArrayEquals(new int[]{1,0,0,0,0}, num2Bin(16));
    }

    private int bin2Num(int[] bin){
        return IntStream.range(0, bin.length).mapToObj(idx -> bin[idx]*pow(2, bin.length-1-idx)).reduce(0, Integer::sum);
    }

    @Test
    public void testBin2Num(){
        Assert.assertEquals(8, bin2Num(new int[]{1,0,0,0}));
        Assert.assertEquals(12, bin2Num(new int[]{1,1,0,0}));
        Assert.assertEquals(14, bin2Num(new int[]{1,1,1,0}));
        Assert.assertEquals(15, bin2Num(new int[]{1,1,1,1}));
        Assert.assertEquals(16, bin2Num(new int[]{1,0,0,0,0}));
    }

    private int[] flip(int[] num){
        return Arrays.stream(num).map(i->1-i).toArray();
    }

    @Test
    public void testFlip(){
        Assert.assertArrayEquals(new int[]{0,1,1,1}, flip(new int[]{1,0,0,0}));
    }


    private int flipNum(int n){
        return bin2Num(flip(num2Bin(n)));
    }

    @Test
    public void test(){
        int[] testNum = new int[]{1,1,0}; //6
        Assert.assertEquals(1, bin2Num(flip(testNum)));

        testNum = new int[] {1,0,0,1}; //9
        Assert.assertEquals(6, bin2Num(flip(testNum)));

        int n = 5;
        Assert.assertEquals(2, bin2Num(flip(num2Bin(n))));

        Assert.assertEquals(0, flipNum(1));

    }
}
