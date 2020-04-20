package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc:
 *
 * Given a string s and a string t, check if s is subsequence of t.
 *
 * You may assume that there is only lower case English letters in both s and t. t is potentially a very long (length ~= 500,000) string, and s is a short string (<=100).
 *
 * A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of the characters without disturbing the relative positions of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).
 *
 * @Source: https://leetcode.com/problems/is-subsequence/
 *
 * @Date: 04/19/20
 *
 * */
public class LE392Subsequence {

    private boolean isSubsequence(String s, String t){
        char[] tChars = t.toCharArray(); //target sub seq
        char[] sChars = s.toCharArray(); //source string
        if(tChars.length>sChars.length){
            return false;
        }
        int tCur = tChars.length-1;
        char c = tChars[tCur];
        int sCur = sChars.length-1;
        while(sCur!=0 && sChars[sCur]!=c){sCur--;}
        if(sCur==0){ //iterate the source but no concurrence found
            return false;
        }
        if(tCur==0){
            return true;
        }

        tCur--;
        sCur--;

        while(tCur>=0){
            c = tChars[tCur--];
            while(sCur>=0 && sChars[sCur]!=c){sCur--;}
            if(sCur==-1){
                return false;
            }
        }
        return true;
    }

    @Test
    public void test(){
        String t = "abc", s = "ahbgdc";
        Assert.assertTrue(isSubsequence(s,t));

        t = "axc";
        s = "ahbgdc";
        Assert.assertFalse(isSubsequence(s,t));

        t = "ahbgdc";
        s = "ahbgdc";
        Assert.assertTrue(isSubsequence(s,t));

        t = "ahbdc";
        s = "ahbgdc";
        Assert.assertTrue(isSubsequence(s,t));

        t = "ahcb";
        s = "ahbgdc";
        Assert.assertFalse(isSubsequence(s,t));


        t = "ahc";
        s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahbgdccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc";
        Assert.assertTrue(isSubsequence(s,t));
    }
}
