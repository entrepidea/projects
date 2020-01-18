package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class LE299BullCow {
    private String getHint(String secret, String guess){
        char[] secChars =secret.toCharArray();
        int len = secret.toCharArray().length;
        char[] guessChars = guess.toCharArray();
        int A = 0;
        int B= 0;
        int[] num = new int[10];
        for(int i=0;i<len;i++){
            if(secChars[i]==guessChars[i]){
                A++;
            }
            else {
                if(num[secChars[i]-'0']++ <0)
                    B++;
                if(num[guessChars[i]-'0']-- > 0)
                    B++;
            }
        }

        StringBuilder sb = new StringBuilder();
        return sb.append(A).append("A").append(B).append("B").toString();
    }

    @Test
    public void test(){
        Assert.assertTrue(getHint("1807","7810").equals("1A3B"));
        Assert.assertTrue(getHint("1123","0111").equals("1A1B"));
    }
}
