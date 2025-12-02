package com.javapython.app;

public class BubbleSorting {
    static int[] generateRandomNumbers(int size){
        int[] numbers = new int[size];
        for(int i=0;i<size;i++){
            numbers[i] = (int)(Math.random()*100);
        }
        return numbers;
    }
    int[] bubbleSort(int[] arr){
        int n = arr.length;
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-i-1;j++){
                if(arr[j]>arr[j+1]){
                    //swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }
    public static void main(String[] args){
        BubbleSorting bs = new BubbleSorting();
        int randomArray[] = generateRandomNumbers(10);
        for (int n : randomArray){
            System.out.print(n + " ");
        }
        System.out.println();
        for (int p : bs.bubbleSort(randomArray)){
            System.out.print(p + " ");
        }
    }
}