package com.entrepidea.algo.tests.sort;

import java.util.concurrent.ThreadLocalRandom;

public class ArrayUtil {
    //advanced way of creating an array of random numbers.
    //https://stackoverflow.com/questions/25793098/how-to-generate-random-array-of-ints-using-stream-api-java-8
    public static int[] generateRandomArray(int low, int high, int size){
        return ThreadLocalRandom.current().ints(low, high).limit(size).toArray();
    }

    public static void printArry(int[] a){
        for(int i : a) {
            System.out.print(i);
            System.out.print(",");
        }
        System.out.println();
    }

    public static void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
