package com.entrepidea.algo.data_structure.sort;

import org.junit.Test;

/**
 * desc: Given an unsorted array of integers, sort the array into a wave like array. An array ‘arr[0..n-1]’ is sorted in wave form if arr[0] >= arr[1] <= arr[2] >= arr[3] <= arr[4] >= …..
 * source: https://www.geeksforgeeks.org/sort-array-wave-form-2/
 * date: 01/08/19
 * */
public class WaveSortArray {

    @Test
    public void test(){
        int[] arr =  {10, 5, 6, 3, 2, 20, 100, 80};

        //bubble sorting
        for(int i=1;i<arr.length-1;i++){
            for(int k=0;k<i;k++){
                if(arr[k]>arr[i]){
                    int temp = arr[k];
                    arr[k]  = arr[i];
                    arr[i] = temp;
                }
            }
        }

        for(int i=0;i<arr.length;i+=2){
            int temp = arr[i];
            arr[i]  = arr[i+1];
            arr[i+1] = temp;
        }

        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]);
            System.out.print(",");
        }
        System.out.println();
    }
}
