package com.entrepidea.algo.tests.sort;

import org.junit.Test;
/**
 * desc: quick sort, use last element as pivot. The purpose of method partition is to move all elements less than pivot to the left, and those greater to the right.
 * source: https://www.geeksforgeeks.org/quick-sort/
 * date: 01/11/2019
 *
 * */
public class QuickSort2 {


    private int partition(int arr[], int low, int high){
        int i = low-1;
        for(int j=low;j<high;j++){
            if(arr[j]<arr[high]){
                i++;
                int temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        return (i+1);
    }

    private void quickSort(int[] arr, int low, int high){
        if(low<high){
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
    }
    @Test
    public void test(){
        //int[] arr = {10,80,30,90,40,50,70};
        int[] arr = {3,1,5,2,9,8,0};
        quickSort(arr,0,6);

        for(int x: arr){
            System.out.print(x);
            System.out.print(",");
        }
        System.out.println();
    }

}
