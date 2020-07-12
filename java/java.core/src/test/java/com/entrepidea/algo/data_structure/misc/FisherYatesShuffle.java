package com.entrepidea.algo.data_structure.misc;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

/**
 * @Desc:
 * Fisher-Yates shuffle requires to pick up M random and non-dup numbers from a N-length array ordered from 1 to N.
 * @Source: https://www.zhihu.com/question/358255792/answer/974431591?utm_source=com.ideashower.readitlater.pro&utm_medium=social&utm_oi=809364293245075456
 *
 * @Date: 07/11/20
 *
 * */
public class FisherYatesShuffle {

    private int[] arr;

    @Before
    public void setup(){
        arr = new int[100];
        for(int i=0;i<100;i++){
            arr[i] = i+1;
        }
    }

    //pick up ONE random number. Easy
    @Test
    public void test(){
        int i = (int)(Math.random()*100);
        System.out.println(arr[i]);
    }

    //Now pick up 50 random numbers from the array and they CAN'T have dups!
    //use Fisher-Yates shuffle to create the required list
    //https://www.geeksforgeeks.org/shuffle-a-given-array-using-fisher-yates-shuffle-algorithm/
    int[] shuffle(int m){
        int len = arr.length;
        for(int i=len-1;i>0;i--){
            int j = new Random().nextInt(i+1);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        int[] ret = new int[m];
        System.arraycopy(arr, 0, ret, 0, ret.length);

        return ret;
    }
    @Test
    public void test2(){
        Arrays.stream(shuffle(10)).forEach(System.out::println);
    }
}
