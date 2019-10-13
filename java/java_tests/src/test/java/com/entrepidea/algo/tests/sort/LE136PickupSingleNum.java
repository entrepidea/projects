package com.entrepidea.algo.tests.sort;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Description: in a integer array, all elements come in pairs except for one. Fine it out.
 * @Source: https://leetcode.com/problems/single-number/
 * @Date: 08/25/19
 * @Solution: sort first and iterate the array
 * */
public class LE136PickupSingleNum {

    private void bubbleSort(int[] arr){
        for(int i=1;i<arr.length;i++){
            boolean isSorted = true;
            for(int j=0;j<arr.length-i;j++){
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

    private int singleNum(int[] arr){
        //sort fist
        bubbleSort(arr);
        ArrayUtil.printArry(arr);
        for(int i=0;i<arr.length;){
            if(i==arr.length-1){
                return arr[i];
            }
            if(arr[i]==arr[i+1]){
                i+=2;
            }
            else{
                return arr[i];
            }
        }
        return -1;
    }

    @Test
    public void testBubbleSorting(){
        int[] arr = {2,1,2};
        bubbleSort(arr);
        ArrayUtil.printArry(arr);
    }

    @Test
    public void testSingleNum(){
        int[] arr = {4,1,2,1,2,4,3};
        Assert.assertTrue(singleNum(arr)==3);
    }
}
