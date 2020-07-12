package com.entrepidea.algo.leetcode.easy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @Desc:
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 *
 * Find all the elements of [1, n] inclusive that do not appear in this array.
 *
 * Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
 *
 * @Source: https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/
 *
 * @Date: 06/27/20
 *
 * */
public class LE448FindDisappearNum {

    private Integer[] foundDisappearNum(int[] input){
        Arrays.sort(input);
        List<Integer> l = new ArrayList<>();
        //Arrays.stream(input).forEach(System.out::println);
        for(int i=0;i< input.length-1;i++){
            int diff = input[i+1]-input[i];
            if(diff>1){
                for(int j=1;j<diff;j++){
                    l.add(input[i]+j);
                }
            }
        }
        Integer[] ret = new Integer[l.size()];
        ret = l.toArray(ret);
        return ret;
    }

    @Test
    public void test(){
        int[] arr = new int[]{4,3,2,6,8,2,3,1};
        Integer[] found = foundDisappearNum(arr);
        Arrays.stream(found).forEach(System.out::println);
    }
}
