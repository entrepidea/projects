package com.entrepidea.algo.misc;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class TappingRainWater {

    private int maxVal(int[] arr){
        int ret = 0;
        for (int i =0;i<arr.length; i++){
            if(arr[i]>ret){
                ret = arr[i];
            }
        }
        return ret;
    }

    private int[] lowerHeight(int[] arr){
        for(int k=0;k<arr.length;k++){
            if(arr[k]==0){
                continue;
            }
            arr[k]-- ;
        }
        int start = 0;
        int last = arr.length - 1;
        while(start<arr.length && start<=last && arr[start]==0){start++;}
        while(last >=0 && last >= start && arr[last]==0){last--;}
        int[] ret = new int[last - start+1];
        int k=0;
        for(int i=start;i<=last;i++){
            ret[k++] = arr[i];
        }
        return ret;

    }

    private boolean onlyOne(int[] arr){
        int count = 0;
        for(int k=0;k<arr.length;k++){
            if(arr[k]==0){
                count++;
            }
        }
        return (arr.length - count) == 1;
    }

    private boolean allZero(int[] arr){
        int count = 0;
        for(int k=0;k<arr.length;k++){
            if(arr[k]==0){
                count++;
            }
        }
        return (arr.length - count) == 0;
    }

    private int volume(int[] arr){
        int vol = 0;
        int max = maxVal(arr);
        int y=0;
        while(y<max){
            for(int i=0;i<arr.length;i++){
                if(arr[i]==0) vol++;
            }

            arr = lowerHeight(arr);
            if(onlyOne(arr) || allZero(arr)){
                break;
            }
            max = maxVal(arr);
        }

        return vol;
    }

    @Test
    public void testMax(){
        int[] arr = new int[]{3, 0, 2, 0, 4};
        //Assert.assertEquals(volume(arr), 7);
        arr = new int[]{2, 0, 2};
        Assert.assertEquals(volume(arr), 2);
        arr = new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        Assert.assertEquals(volume(arr), 6);
    }
}
