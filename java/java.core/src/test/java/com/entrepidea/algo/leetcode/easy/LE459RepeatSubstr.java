package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc:
 * Given a non-empty string check if it can be constructed by taking a substring of it and appending multiple copies of the substring together.
 * You may assume the given string consists of lowercase English letters only and its length will not exceed 10000.
 *
 * @Note: The idea goes like: start a window with length 1, slide through the array;
 * if not pass, increase the window length and repeat the above process. Result will be either a string met the requirement or failure.
 *
 * @Source: https://leetcode.com/problems/repeated-substring-pattern/
 *
 * @Date: 07/05/20
 * */
public class LE459RepeatSubstr {

    private boolean isRepeatSubstr(String input) {
        char[] arr = input.toCharArray();
        boolean ret = false;
        int len = arr.length;
        int winLen = 1;
        while(winLen<len/2){
            int it = 1;
            boolean incWinLen = false;
            while(true) {
                int startPnt = it * winLen;
                int stopPnt = startPnt + winLen;
                if(startPnt>len || stopPnt> len){
                    ret = true;
                    break;
                }
                for (int k = startPnt; k < stopPnt; k++) {
                    if (arr[k] != arr[k - winLen]) {
                        incWinLen = true;
                        break;
                    }
                }
                if(incWinLen){
                    break;
                }
                it++;
            }
            winLen++;
        }

        return ret;
    }


    @Test
    public void test(){
        Assert.assertTrue(isRepeatSubstr("aaaaa"));
        Assert.assertTrue(isRepeatSubstr("aabaabaab"));
        Assert.assertTrue(isRepeatSubstr("abcdabcdabcd"));
        Assert.assertFalse(isRepeatSubstr("abcdabcdabce"));
        Assert.assertFalse(isRepeatSubstr("aba"));
    }
}
