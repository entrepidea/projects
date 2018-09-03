package com.entrepidea.algo.tests.sort;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/*
//  Morgan Stanley, onsite, 05/09/12
//  TODO Sorting types. Bubble sorting, selection sorting, insertion sorting, merge sorting and quick sorting. Performance of each, in big O notation. For merge sorting, is O( nlogn). N for divide and logn for merge.
*   the code below not working.
* */
public class MergingSort {

    private int[] merge(int[] a, int[] b){

        int count=0;

        int[] temp = new int[a.length+b.length];

        while(a.length>0 && b.length>0){
            if(a[0]>b[0]){
                temp[count++] = b[0];
                b = Arrays.copyOfRange(b,1, b.length);
            }
            else{
                temp[count++] = a[0];
                a = Arrays.copyOfRange(a, 1, a.length);
            }
        }

        while (a.length>0){
            temp[count++] = a[0];
            a = Arrays.copyOfRange(a, 1, a.length);
        }
        while(b.length>0){
            temp[count++] = b[0];
            b = Arrays.copyOfRange(b,1,b.length);
        }
        return temp;
    }


    private int[] mergeSort(int[] l){
        if (l.length==1){
            return l;
        }

        int[] a = Arrays.copyOfRange(l, 0, l.length/2);
        int[] b = Arrays.copyOfRange(l, l.length/2, l.length);
        int[] ret = new int[a.length+b.length];

        mergeSort(a);
        mergeSort(b);

        ret = merge(a,b);
        return ret;
    }



    @Test
    public void testMergingSorting(){
        int[] arr = ThreadLocalRandom.current().ints(0,100).limit(5).toArray();
        int[] res = mergeSort(arr);
        for(int e : res){
            System.out.print(e);
            System.out.print("\t");
        }
        System.out.println();
    }

    @Test
    public void testCopyRange(){
        int[] arr = ThreadLocalRandom.current().ints(0,100).limit(10).toArray();
        for (int i : arr){
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
        int[] orig = arr;

        int[] ret = new int[arr.length];

        int count=0;
        while(arr.length>0){
            ret[count++] = arr[0];
            arr = Arrays.copyOfRange(arr,1,arr.length);
        }
        Assert.assertArrayEquals(ret, orig);
    }
}
