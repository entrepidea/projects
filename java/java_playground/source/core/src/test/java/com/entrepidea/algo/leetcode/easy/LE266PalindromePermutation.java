package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: to find out if a string is the permutation of a palindrome (forward/backward reading are the same for a word)
 * @Source: https://leetcode.com/articles/palindrome-permutation/
 * @Date: 12/16/19
 * */
public class LE266PalindromePermutation {

    private boolean isPalindrome(String str, boolean lenIsEven){
        char[] chars = str.toCharArray();
        int[] alphaLetters = new int[26];
        int count=0;
        for(char c : chars){
            alphaLetters[c-'a']++;
        }
        for(int i: alphaLetters){
            if(i==0){ //filter out letters that not existent
                continue;
            }
            if(lenIsEven){
                if(i!=2){
                    return false;
                }
            }
            else{
                if(i!=2 && i!=1){
                    return false;
                }
                if(i==1){
                    count++;
                }
            }
        }
        if(!lenIsEven && count>1){
            return false;
        }
        return true;
    }

    private boolean isPalindrome(String str){
        int len = str.length();
        if (len%2==0){
            return isPalindrome(str, true);
        }
        else{
            return isPalindrome(str, false);
        }
    }

    @Test
    public void test(){
        Assert.assertTrue(isPalindrome("aab"));
        Assert.assertFalse(isPalindrome("aacdbb"));
        Assert.assertFalse(isPalindrome("abc"));
        Assert.assertFalse(isPalindrome("code"));
        Assert.assertTrue(isPalindrome("carerac"));
    }
}
