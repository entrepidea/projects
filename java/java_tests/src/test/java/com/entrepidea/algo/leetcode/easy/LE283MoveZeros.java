package com.entrepidea.algo.leetcode.easy;

import org.junit.Test;

import java.util.Arrays;
/**
 * @Desc:
 * if the element in an array is zero, move to the most right, other remains the original order.
 *
 * @Source: https://leetcode.com/problems/move-zeroes/
 *
 * @Date: 01/13/20
 *
 * */
public class LE283MoveZeros {

    int[] arr = {0,11,1,0,3,12,0,4,0,0,0,9,0,8};

    private void bubbleSort(int[] arr){
        for(int i=1;i<arr.length;i++){
            boolean isSorted = true;
            for (int j=0;j<arr.length-i;j++){
                if(arr[j]>arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    isSorted = false;
                }
            }
            if(isSorted){
                break;
            }
        }
    }

    @Test
    public void testBubbleSort(){
        bubbleSort(arr);
        Arrays.stream(arr).forEach(System.out::println);
    }
    private void moveZeros(int[] arr){
        int k=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i]!=0){
                arr[k]=arr[i];
                if(i!=k)
                    arr[i]=0;
                k++;
            }
        }
    }
    @Test
    public void test(){
        moveZeros(arr);
        Arrays.stream(arr).forEach(System.out::println);
    }
}
