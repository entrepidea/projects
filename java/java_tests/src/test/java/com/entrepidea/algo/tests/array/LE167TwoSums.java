package com.entrepidea.algo.tests.array;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: find out the index of two elements in a sorted array, the sum of which will be equal to the given number.
 * @Source: https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 * @Date: on 9/12/2019.
 */
public class LE167TwoSums {
    @Before
    public void setup(){

    }

    class Pair{
        public int i;
        public int j;
        public Pair(int ii, int jj){
            i = ii;
            j=jj;
        }
    }
    private List<Pair> findPairs(int[] array, int sum){

        List<Pair> l = new ArrayList<>();

        for(int i=0;i<array.length-1;i++){
            for(int j=i+1;j<array.length;j++){
                if((array[i]+array[j])==sum){
                    l.add(new Pair(i+1,j+1));
                }
            }
        }
        return l;
    }
    @Test
    public void test(){
        int[] numbers = new int[]{2,7,11,15};
        List<Pair> l = findPairs(numbers,9);
        Assert.assertTrue(l.size()==1);
        Assert.assertTrue(l.get(0).i==1 && l.get(0).j == 2);
    }
}
