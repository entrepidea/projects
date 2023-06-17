package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

public class LE345ReverseVowels {

    private boolean isVowel(char c){
        return (c=='a' || c=='e' || c=='i' || c=='o' || c=='u');
    }

    private String reverseVowels(String input){
        char[] chars = input.toCharArray();
        for(int i=0, j=chars.length-1; i<j;){
            if(isVowel(chars[i])) {
                if(isVowel(chars[j])){
                    char temp = chars[i];
                    chars[i] = chars[j];
                    chars[j] = temp;

                }
                j--;
            }
            else {
                i++;
            }
        }
        return String.valueOf(chars);
    }
    @Test
    public void test(){
        Assert.assertEquals(reverseVowels("hello"), "holle");
        Assert.assertEquals(reverseVowels("leetcode"), "leotcede");
        Assert.assertEquals(reverseVowels("hell"),"hell");
    }
}
