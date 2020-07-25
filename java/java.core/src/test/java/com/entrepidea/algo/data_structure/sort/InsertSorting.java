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



    //another test - redo again, this is not very elegant.
    private void sort(int[] arr){
        int count = arr.length -1;

        int startIdx = arr.length-1;
        while(count!=0){
            int curIdx = -1;
            for(int i=0;i<=startIdx-1;i++){
                if(arr[startIdx]<arr[i]){
                    curIdx = i;
                    break;
                }
            }
            if(curIdx==-1){
                curIdx = startIdx;
            }
            int tmp = arr[startIdx];
            for(int k=startIdx-1;k>=curIdx;k--){
                arr[k+1] = arr[k];
            }
            arr[curIdx] = tmp;

            if(curIdx==startIdx){
                startIdx--;
            }
            count--;
        }
    }
    @Test
    public void test2(){


        int[] arr = new int[]{5,3,1,4,2};
        sort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();

        arr = new int[]{5,1,3,4,2};
        sort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();

        arr = new int[]{5,4,3,2,1};
        sort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();


        arr = new int[]{1,2,3,4,5};
        sort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();

    }

    //do it again. Insert sort can also be used to pick up the K top ones. As shown below. 04/14/20.
    private void insertSort(int[] arr){
        int len = arr.length;
        int topMax=2;
        for(int i=1;i<len;i++){
            int curIdx = i;
            int idx = 0;
            while(idx<curIdx && arr[curIdx]<arr[idx]){
                idx++;
            }
            if(idx==curIdx){
                continue;
            }
            int temp = arr[curIdx];
            for(int j=curIdx-1;j>=idx;j--){
                arr[j+1] = arr[j];
            }
            arr[idx] = temp;
            topMax--;
            if(topMax==0){
                break;
            }
            Arrays.stream(arr).forEach(System.out::print);
            System.out.println();
        }
    }

    @Test
    public void test3(){
        int[] arr = new int[]{5,1,3,4,2};
        insertSort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();

        /*
        arr = new int[]{5,4,3,2,1};
        insertSort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();

        arr = new int[]{1,2,3,4,5};
        insertSort(arr);
        Arrays.stream(arr).forEach(System.out::print);
        System.out.println();
        */


    }

    private void insertSort2(int[] arr){
        for(int i=1;i<arr.length;i++){
            for(int j=0;j<i;j++){
                if(arr[i]<arr[j]){
                    int temp = arr[i];
/*                    for(int k=i-1;k>=j;k--){
                        arr[k+1] = arr[k];
                    }*/
                    //the above commented code can be refactored to one line below
                    System.arraycopy(arr, j, arr, j + 1, i - j);
                    arr[j] = temp;
                }
            }
        }
    }

    @Test
    public void testInsertSort2(){
        int[] arr = new int[]{10,5,7,3,16};
        insertSort2(arr);
        Arrays.stream(arr).forEach(System.out::println);
    }

}
