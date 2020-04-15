package com.entrepidea.algo.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * */
public class LE350TwoArrayIntersect2 {

    int[] intersect(int[] num1, int[] num2){
        int[] smallerArr = num1.length<num2.length?num1:num2;
        Map<Integer, Integer> seen = new HashMap<>();
        Arrays.stream(smallerArr).forEach(val -> seen.put(val, seen.getOrDefault(val,0)+1));
        int[] largerArr = smallerArr == num1?num2:num1;
        return Arrays.stream(largerArr).filter(val -> {
            int count = seen.get(val);
            if(count==0){
                return false;
            }
            else{
                seen.put(val, count-1);
                return true;
            }
        }).toArray();
    }

    @Test
    public void test(){

    }
}
