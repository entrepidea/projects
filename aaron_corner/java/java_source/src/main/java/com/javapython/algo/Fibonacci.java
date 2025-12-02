package com.javapython.app;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
    private static List<Integer> fibSequence;
    public static void main(String[] args) {
        fibSequence = new ArrayList<>();
        fibSequence.add(0);
        fibSequence.add(1);
        int n = 100; // Change this value to generate more or fewer Fibonacci numbers
        for (int i = 2; i < n; i++) {
            int nextFib = fibSequence.get(i - 1) + fibSequence.get(i - 2);
            fibSequence.add(nextFib);
        }
        System.out.println(fibSequence);
    }
}
