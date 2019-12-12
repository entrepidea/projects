package com.entrepidea.algo.data_structure.sort;

import org.junit.Test;

/**
 * desc: Given an array A[] consisting 0s, 1s and 2s, write a function that sorts A[]. The functions should put all 0s first, then all 1s and all 2s in last.
 * source: https://www.geeksforgeeks.org/sort-an-array-of-0s-1s-and-2s/
 * date: 01/04/19
 * */
public class Sort012Array {

    @Test
    public void test(){
        //int[] arr = {0, 1, 2, 0, 1, 2};
        int[] arr = {0, 1, 1, 0, 1, 2, 1, 2, 0, 0, 0, 1};

        int[] a = new int[3];

        for (int i=0;i<arr.length;i++){
            if(arr[i]==0){
                a[0]++;
            }
            else if(arr[i]==1){
                a[1]++;
            }
            else if(arr[i]==2){
                a[2]++;
            }
        }

        int[] rst = new int[arr.length];
        int count = 0;
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[i];j++)
                rst[count++] = i;
        }

        for(int i=0;i<rst.length;i++){
            System.out.print(rst[i]);
            System.out.print(",");
        }
        System.out.println();
    }
}
