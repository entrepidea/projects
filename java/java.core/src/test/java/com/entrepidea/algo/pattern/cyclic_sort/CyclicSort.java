package com.entrepidea.algo.pattern.cyclic_sort;

import org.junit.Test;

public class CyclicSort {

    private void sort(int[] arr){
        if(arr==null||arr.length==0||arr.length==1){
            return ;
        }


        for(int cycSortPos = 0;cycSortPos<arr.length-1;cycSortPos++){
            int item = arr[cycSortPos];
            int pos = cycSortPos;
            while(arr[pos]<item&pos<arr.length-1){
                pos++;
            }
            int tmp = arr[pos];
            arr[pos] = item;
            item = tmp;
        }
    }
    @Test
    public void test(){
        int arr[] = new int[]{10, 5, 2, 3};
        sort(arr);
        //System.out.println(arr);
        for(int i: arr){
            System.out.println(i);
        }

    }
}
