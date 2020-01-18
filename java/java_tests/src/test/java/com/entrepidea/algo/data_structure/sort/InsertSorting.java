package com.entrepidea.algo.data_structure.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * desc: insert sorting is like playing card - start from the 2nd card, compare those on its left, insert the card in order.
 * Move to the 3rd, 4th, 5th, ... and repeat the above process.
 * source: https://www.geeksforgeeks.org/insertion-sort/
 * date: 01/13/19
 * *
 * */
public class InsertSorting {

    @Test
    public void test(){
        int[] a = {4,3,2,10,12,1,5,6,11,99,3,0,73};

        for(int i=1;i<a.length;i++){
            for(int j=0;j<i;j++){
                if(a[i]<a[j]){
                    int temp = a[i];
                    for(int k=i-1;k>=j;k--){//shift to the right by one position
                        a[k+1] = a[k];
                    }
                    a[j] = temp;
                }
            }
        }

        for(int e : a){
            System.out.print(e);
            System.out.print(",");
        }
        System.out.println();

    }


}
