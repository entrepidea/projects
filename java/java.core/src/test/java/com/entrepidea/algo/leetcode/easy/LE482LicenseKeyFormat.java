package com.entrepidea.algo.leetcode.easy;

import org.junit.Test;

/**
 * @Desc:
 * You are given a license key represented as a string S which consists only alphanumeric character and dashes. The string is separated into N+1 groups by N dashes.
 *
 * Given a number K, we would want to reformat the strings such that each group contains exactly K characters, except for the first group which could be shorter than K, but still must contain at least one character. Furthermore, there must be a dash inserted between two groups and all lowercase letters should be converted to uppercase.
 *
 * Given a non-empty string S and a number K, format the string according to the rules described above.
 *
 * @Source: https://leetcode.com/problems/license-key-formatting/
 *
 * @Date: 08/27/20
 *
 * */
public class LE482LicenseKeyFormat {

    private String format(String original, int k){
        StringBuilder sb = new StringBuilder();
        for(char c : original.toCharArray()){
            if(c!='-') {
                sb.append(c);
            }
        }

        char[] charset = sb.toString().toCharArray();
        int remainder = charset.length % k;
        sb = new StringBuilder();
        if(remainder>0){
            for(int i=0;i<remainder;i++){
                sb.append(charset[i]);
            }
            sb.append('-');
        }
        int idx = remainder;
        while(idx<charset.length){
            for(int i=idx;i<idx+k;i++){
                sb.append(charset[i]);
            }
            sb.append('-');
            idx += k;
        }
        return sb.deleteCharAt(sb.length()-1).toString().toUpperCase();
    }
    @Test
    public void test(){
        String s = "5F3Z-2e-9-w";
        int k = 4;
        String newS = format(s,k);
        System.out.println(newS);

        s = "2-5g-3-J";
        k = 2;
        System.out.println(format(s,k));

    }
}
