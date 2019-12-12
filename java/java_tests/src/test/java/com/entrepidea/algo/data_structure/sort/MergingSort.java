package com.entrepidea.algo.data_structure.sort;


import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

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


    //redo, 02/23/19
    int[] merge2(int[] l1, int[] l2){
        int n1 = l1.length;
        int n2 = l2.length;
        int[] l = new int[n1+n2];
        int k1=0,k2=0,k=0;
        while (k1<n1 && k2<n2){
            if(l1[k1]<=l2[k2]){
                l[k++] = l1[k1++];
            }
            else{
                l[k++] = l2[k2++];
            }
        }

        while(k2<n2){
            l[k++] = l2[k2++];
        }
        while(k1<n1){
            l[k++] = l1[k1++];
        }

        return l;
    }

    int[] mergeSorting2(int[] arr){
        int len = arr.length;
        if(len==1){
            return arr;
        }
        int[] l1 = Arrays.copyOfRange(arr,0,len/2);
        int[] l2 = Arrays.copyOfRange(arr,len/2, len);
        int[] a = mergeSorting2(l1);
        int[] b = mergeSorting2(l2);

        return merge2(a,b);
    }

    @Test
    public void test2(){
        int[] arr = ArrayUtil.generateRandomArray(0,100,11);
        ArrayUtil.printArry(arr);
        int[] ret = mergeSorting2(arr);
        ArrayUtil.printArry(ret);
    }
}
