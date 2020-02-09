package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Source: https://leetcode.com/problems/isomorphic-strings/
 * @Desc: check the link.
 * Created by jonat on 11/6/2019.
 */
public class LE205Isomorphic {

    private boolean isomorphic(String s, String t){
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        Assert.assertEquals(s1.length, t1.length);
        int len = s1.length;
        Map<Character, Character> map = new HashMap<>();
        for(int i=0;i<len;i++){
            if(!map.containsKey(s1[i])){
                map.put(s1[i],t1[i]);
            }
            else{
                char tVal = map.get(s1[i]);
                if(tVal !=t1[i]){
                    return false;
                }
            }
        }

        return true;

    }

    @Test
    public void test(){
        Assert.assertTrue(isomorphic("egg","add"));
        Assert.assertTrue(isomorphic("paper","title"));
        Assert.assertFalse(isomorphic("foo","bar"));
    }
}
