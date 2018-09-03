package com.entrepidea.algo.tests.sort;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * Morgan Stanley, onsite, 05/09/12
 * Sorting types. Bubble sorting, selection sorting, insertion sorting, merge sorting and quick sorting. Performance of each, in big O notation. For merge sorting, is O( nlogn). N for divide and logn for merge.
 *
 * below is selection sorting. Be aware that performance suffers when numbers become large.
 *
 * https://www.tutorialspoint.com/data_structures_algorithms/selection_sort_algorithm.htm
 *
 * */
public class SelectionSort {


    private int[] findMin(int[] arr, int start, int end){
        int[] ret = new int[2];
        int min = arr[start];
        int pos = 0;
        for(int i=start+1;i<end;i++){
            if(arr[i]<min){
                min = arr[i];
                pos = i-start;
            }
        }
        ret[0] = min; ret[1] = pos;

        return ret;
    }

    private void sort(int[] arr){
        int k = arr.length;
        //int[] origArr = Arrays.copyOfRange(arr,0,arr.length);
        int currPos = 0;
        while(k>0){
            int[] valPos = findMin(arr, currPos, arr.length);
            int min = valPos[0]; int pos = valPos[1];
            //swap
            int temp = arr[currPos];
            arr[currPos] = min;
            arr[currPos+pos] = temp;

            currPos++;
            k--;
        }
    }

    @Test
    public void testSort(){
        int[] arr = ThreadLocalRandom.current().ints(0,100).limit(20).toArray();
        sort(arr);
        for(int i : arr){
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }
}
