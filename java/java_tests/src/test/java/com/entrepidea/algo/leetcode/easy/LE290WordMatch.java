package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LE290WordMatch {

    private boolean match(String pattern, String testStr){
        char[] ps = pattern.toCharArray();
        String[] words = testStr.split(" ");
        Assert.assertEquals(ps.length, words.length);
        Map<Character, String> m = new HashMap<>();
        for(int i=0;i<ps.length;i++) {
            if (m.get(ps[i]) != null) {
                if (!words[i].equals(m.get(ps[i]))) {
                    return false;
                }
            } else {
                m.put(ps[i], words[i]);
            }
        }
        return true;
    }

    @Test
    public void test(){
        Assert.assertTrue(match("abba", "dog cat cat dog"));
        Assert.assertFalse(match("abba", "dog cat cat fish"));
    }
}
