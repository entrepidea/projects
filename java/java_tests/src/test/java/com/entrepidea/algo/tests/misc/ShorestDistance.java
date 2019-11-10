package com.entrepidea.algo.tests.misc;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Desc: this is to find out the shortest distance between two words.
 * @Source: https://www.geeksforgeeks.org/minimum-distance-between-words-of-a-string/
 *
 * Goldman has a similar interview question.
 *
 * Created by jonat on 10/25/2019.
 */
public class ShorestDistance {
    private int foo(String str, String word1, String word2){
        String[] arr = str.split(" ");
        int shortestDis = arr.length+1;
        for(int i=0;i<arr.length;i++){
            if(arr[i].equals(word1)){
                for(int search =0;search<arr.length;search++){
                    if(arr[search].equals(word2)){
                        int dis = Math.abs(i-search)-1;
                        if(dis<shortestDis){
                            shortestDis = dis;
                        }
                    }
                }
            }
        }
        return shortestDis;

    }

    @Test
    public void test(){
        String s = "geeks for geeks contribute practice more contribute";
        System.out.println(foo(s, "geeks","practice"));
        System.out.println(foo(s, "contribute","practice"));
        System.out.println(foo(s, "for","more"));
    }
}
