package com.entrepidea.algo.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * @Desc: find intersection of two arrays
 * @Source: the solution below is from: https://leetcode.com/problems/intersection-of-two-arrays-ii/discuss/577370/Simple-java-Solution
 * @Date: 04/15/20
 * */
public class LE350TwoArrayIntersect2 {

    int[] intersect(int[] num1, int[] num2){
        int[] smallerArr = num1.length<num2.length?num1:num2;
        Map<Integer, Integer> seen = new HashMap<>();
        Arrays.stream(smallerArr).forEach(val -> seen.put(val, seen.getOrDefault(val,0)+1));
        int[] largerArr = smallerArr == num1?num2:num1;
        return Arrays.stream(largerArr).filter(val -> {
            int count = seen.getOrDefault(val,0);
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
        int[] num1 = new int[]{1,2,2,1};
        int[] num2 = new int[]{2,2};
        Arrays.stream(intersect(num1,num2)).forEach(System.out::println);

        num1 = new int[]{4,9,5};
        num2 = new int[]{9,4,9,8,4};
        Arrays.stream(intersect(num1,num2)).forEach(System.out::println);
    }
}
