package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

public class LE344ReverseString {

    private void reverseString(char[] input){
        for(int i=0;i<input.length/2;i++){
            char temp = input[i];
            input[i] = input[input.length-1-i];
            input[input.length-1-i] = temp;
        }
    }

    @Test
    public void test(){
        char[] input = new char[]{'h','e','l','l','o'};
        reverseString(input);

        Assert.assertEquals(String.valueOf(input), "olleh");
        input = new char[]{'h','e','l','l'};
        reverseString(input);
        Assert.assertEquals(String.valueOf(input), "lleh");

        input = new char[]{'H','a','n','n','a','h'};
        reverseString(input);
        Assert.assertEquals(String.valueOf(input), "hannaH");
    }
}
