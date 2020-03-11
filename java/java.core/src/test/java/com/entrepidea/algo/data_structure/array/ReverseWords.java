package com.entrepidea.algo.data_structure.array;

import org.junit.Test;

/**
 * Question C1: given a sentence without punctuation, print the words in
 reverse. For eg, "I like Teresa Teng songs most" would become "most
 songs Teng Teresa like I"
 Please don't use any split() function from some library.

 @source: c++/algo/SQL IV questions - BAML, 10/11/17, courtesy of Tanbin

 @Note: Now I feel the most time-and-space efficient solution can use
 back-scan character by character, and a local stack.
 * */
public class ReverseWords {

 private static String test = "I like Teresa Teng songs most";

    //a silly one...
  @Test
  public void test1(){
    String[] s = test.split(" ");
    for(int i=s.length-1;i>=0;i--){
        System.out.print(s[i]+" ");
    }
      System.out.println();
  }

    //more sophosicated one
    @Test
    public void test2() {
        char[] origArr = test.toCharArray();
        char[] newArr = new char[origArr.length];
        int endIdx = origArr.length-1;
        int j=0;
        while(endIdx!=0){
            int startIdx = endIdx;
            while(origArr[startIdx] !=' ' && origArr[startIdx]!='\t'){
                startIdx--;
            }
            for(int i=startIdx+1;i<=endIdx;i++){
                newArr[j++] = origArr[i];
            }
            newArr[j++] = ' ';
            endIdx = startIdx-1;
        }

        newArr[origArr.length-1] = origArr[0];

        for(int i =0;i<newArr.length;i++){
            System.out.print(newArr[i]);
        }

        System.out.println();
    }

}
