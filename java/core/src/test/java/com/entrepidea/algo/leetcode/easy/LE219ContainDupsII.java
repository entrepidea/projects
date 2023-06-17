package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Source: https://leetcode.com/problems/contains-duplicate-ii/
 * @Desc: find if there are dups in an array and the distance between the dups is shorter or equal to k.
 * Created by jonat on 11/6/2019.
 */
public class LE219ContainDupsII {

    private boolean containDups(int[] arr, int k){
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<arr.length;i++){
            if(!map.containsKey(arr[i])){
                map.put(arr[i],i);
            }
            else{
                if((i-map.get(arr[i]))<=k){
                    return true;
                }
            }

        }
        return false;
    }

    @Test
    public void test(){
        Assert.assertTrue(containDups(new int[]{1,2,3,1},3));
        Assert.assertFalse(containDups(new int[]{1,0,1,1},1));
        Assert.assertTrue(containDups(new int[]{1,2,3,1,2,3},3));
    }


    @Test
    public void test2(){

    }

}
