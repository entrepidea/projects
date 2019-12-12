package com.entrepidea.algo.data_structure.sort;

import org.junit.Test;

/**
 * desc: Given an unsorted array arr[0..n-1] of size n, find the minimum length subarray arr[s..e] such that sorting this subarray makes the whole array sorted.
 * source: https://www.geeksforgeeks.org/minimum-length-unsorted-subarray-sorting-which-makes-the-complete-array-sorted/
 * date: 01/10/19
 *
 * */
public class UnsortedSubArray {

    @Test
    public void test(){
        int[] arr = {10, 12, 20, 30, 25, 40, 32, 31, 35, 50, 60};
        boolean found = false;
        int startIdx = -1;
        for(int i=0;i<arr.length;i++){
            if(arr[i]>arr[i+1]){
                found = true;
                startIdx = i;
                break;
            }
        }
        if(!found){
            System.out.println("array already sorted");
            System.exit(0);
        }
        int endIdx = arr.length-1;
        for(int i=arr.length-1;i>0;i--){
            if(arr[i]<arr[i-1]){
                endIdx = i;
                break;
            }
        }

        int min = arr[startIdx];
        int max = arr[startIdx];
        for(int i=startIdx+1;i<=endIdx;i++){
            if(arr[i]<min) min=arr[i];
            if(arr[i]>max) max = arr[i];
        }
        for(int i=0;i<startIdx;i++){
            if(arr[i]>min){
                startIdx = i;
            }
        }

        for(int i=endIdx+1;i<arr.length;i++){
            if(arr[i]<max){
                endIdx = i;
            }
        }

       System.out.println("The range of the sub array is from: "+startIdx+" to "+endIdx);


    }
}
