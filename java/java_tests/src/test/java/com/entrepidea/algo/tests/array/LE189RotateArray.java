package com.entrepidea.algo.tests.array;

import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/rotate-array/
 * @Desc: Given an array, rotate the array to the right by k steps, where k is non-negative.
 * Created by jonat on 10/31/2019.
 */
public class LE189RotateArray {

    private void foo(int[] arr, int k){
        int[] temp = new int[k];
        int len = arr.length;
        int count=0;
        for(int i=len-1;i>=len-k;i--){
            temp[count++] = arr[i];
        }
        count=len-1;
        for(int i=len-k-1;i>=0;i--){
            arr[count] = arr[i];
            count--;
        }
        count=0;
        for(int i=temp.length-1;i>=0;i--){
            arr[count++] = temp[i];
        }

    }

    @Test
    public void testFoo(){
        int[] arr = {1,2,3,4,5,6,7};
        foo(arr, 7);
        for(int i : arr){
            System.out.print(i+",");
        }
        System.out.println();

        arr = new int[]{-1,-100,3,99};
        foo(arr, 2);

        for(int i : arr){
            System.out.print(i+",");
        }
        System.out.println();

    }
}
