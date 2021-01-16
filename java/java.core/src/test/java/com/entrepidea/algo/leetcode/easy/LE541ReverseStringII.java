package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;


/**
 * Given a string and an integer k, you need to reverse the first k characters for every 2k characters counting
 * from the start of the string. If there are less than k characters left, reverse all of them.
 * If there are less than 2k but greater than or equal to k characters, then reverse the first k characters
 * and left the other as original.
 *
 * @Source: https://leetcode.com/problems/reverse-string-ii/
 * @Date: 11/03/20
 * */
public class LE541ReverseStringII {

    private String reverse(String str, int k){
        char[] chars = str.toCharArray();
        int i=0;
        int pnt = 0;
        while((pnt= i*2*k)<chars.length-k){
            int end = pnt + k/2;
            int lastIdx = pnt+k-1;
            for(int j=pnt;j<end;j++){
                char temp = chars[pnt];
                chars[pnt] = chars[lastIdx];
                chars[lastIdx--] = temp;
            }
            i++;
        }
        return new String(chars);
    }

    @Test
    public void test(){
        String s = "abcdefg";
        Assert.assertEquals("bacdfeg", reverse(s,2));
        Assert.assertEquals("cbadefg", reverse(s,3));
    }
}
