package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Source: https://leetcode.com/problems/valid-palindrome/
 * @Date: 08/25/19
 *
 * */
public class LE125Palindrome {
    private boolean isAlphanumeric(char c){
        return Character.isDigit(c) || Character.isLetter(c);
    }
    private boolean isPalindrome(String testStr){
        char[] arr = testStr.toCharArray();
        int i = 0; int j = arr.length-1;
        while(i!=j){
            if(!isAlphanumeric(arr[i])){
                i++;
            }
            else{
                if(!isAlphanumeric(arr[j])){
                    j--;
                }
                else{
                    if(Character.toUpperCase(arr[i])==Character.toUpperCase(arr[j])) {
                        i++;
                        j--;
                    }
                    else{
                        return false;
                    }
                }
            }
        }
        return i==j;
    }

    @Test
    public void test(){
        Assert.assertTrue(isPalindrome("A man, a plan, a canal: Panama"));
    }
}
