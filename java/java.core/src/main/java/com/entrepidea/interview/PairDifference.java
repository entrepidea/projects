package com.entrepidea.interview;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Desc: Having an array of integers and a number X, find all pairs of integers in the array which have difference equal to the number X.
 * @Note: Morgan Stanley exercise
 * @Date: 06/08/20
 * */
public class PairDifference {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 1};
        LinkedList<Pair> results = pairDifferByX(arr, -1);
        for (Pair elem: results)
            System.out.print(elem + " ");
        //print out (1,2) (2,3) (2,1) (3,4)
    }

    //Return a list of pair of elements in arr whose difference is x.
    static LinkedList<Pair> pairDifferByX(int[] arr, int x) {
        LinkedList<Pair> results = new LinkedList<Pair>();
        for (int i = 0; i < arr.length-1; i++)
            for (int j = i+1; j < arr.length; j++)
                if (arr[i] - arr[j] == x || arr[j] - arr[i] == x)
                    results.add(new Pair(arr[i], arr[j]));

        return results;
    }
}

//Similar to Point class in jdk, which specifies (x,y) location in the coordinate space.
class Pair {
    private int m; //the first number of a pair
    private int n; //the other number of a pair

    public Pair(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public Pair getPair() {
        return new Pair(m, n);
    }

    @Override
    public String toString() {
        return "(" + m + "," + n + ")";
    }
}