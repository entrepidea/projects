package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: An anagram of a string is another string that contains same characters, only the order of characters can be different.
 * For example, “abcd” and “dabc” are anagram of each other.
 * */
public class LE242ValidAnagram {

    private boolean  isAnagram(String s, String target){
        int[] alpha = new int[26];
        char[] arr = s.toCharArray();
        for (char c : arr){
            alpha[c-'a']++;
        }
        arr = target.toCharArray();
        for(char c : arr){
            if(alpha[c-'a']==0){
                return false;
            }
            else{
                alpha[c-'a']--;
            }
        }
        for(int i: alpha){
            if(i>0){
                return false;
            }
        }
        return true;
    }

    @Test
    public void test(){
        Assert.assertTrue(isAnagram("anagram","nagaram"));
        Assert.assertFalse(isAnagram("anagram","nigaram"));
        Assert.assertFalse(isAnagram("rat","car"));
        Assert.assertFalse(isAnagram("test","ttesu"));
    }
}
