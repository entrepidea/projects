package com.entrepidea.algo.data_structure.misc;

/**
 * addition operation w/o using operator + or -
 * https://leetcode.com/problems/sum-of-two-integers/description/
 *
 * solution:
 * https://www.geeksforgeeks.org/add-two-numbers-without-using-arithmetic-operators/
 * */
public class XorUsage {
    public static int add(int x, int y){
        if(y==0) return x;

        return add(x^y, (x&y)<<1);
    }

    public static void main(String[] args){
        System.out.println(add(3,4));
    }
}
