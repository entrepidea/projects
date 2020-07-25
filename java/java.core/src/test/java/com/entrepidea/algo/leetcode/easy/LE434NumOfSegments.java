package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: find out the segments of a string
 * @Source: https://leetcode.com/problems/number-of-segments-in-a-string/
 * @Date: 05/15/20
 *
 * */
public class LE434NumOfSegments {

    private int numOfSeg(String str){
        char[] arr = str.toCharArray();
        int i, j=0;
        int seg=0;
        while(j<arr.length){
            while(j<arr.length && arr[j]!=' ' ){
                j++;
            }
            i=j;
            while(i<arr.length && arr[i]==' '){
                i++;
            }
            j=i;
            seg++;
        }
        return seg;
    }

    @Test
    public void test(){
        String str = "Hello, my name is         John";
        Assert.assertEquals(numOfSeg(str),5);
    }
}
