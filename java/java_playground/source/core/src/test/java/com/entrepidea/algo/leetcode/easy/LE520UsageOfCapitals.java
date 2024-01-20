package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

/**
 * Given a word, you need to judge whether the usage of capitals in it is right or not.
 *
 * We define the usage of capitals in a word to be right when one of the following cases holds:
 *
 * All letters in this word are capitals, like "USA".
 * All letters in this word are not capitals, like "leetcode".
 * Only the first letter in this word is capital, like "Google".
 * Otherwise, we define that this word doesn't use capitals in a right way.
 *
 * @Source: https://leetcode.com/problems/detect-capital/
 * @Date: 11/01/2020
 *
 * */
public class LE520UsageOfCapitals {

    private boolean usageOfCapitals(String word){
        String rule1 = word.toUpperCase();
        String rule2 = word.toLowerCase();
        String rule3 = String.valueOf(word.charAt(0)).toUpperCase() + rule2.substring(1);
        return word.equals(rule1) || word.equals(rule2) || word.equals(rule3);
    }

    @Test
    public void test(){
        Assert.assertTrue(usageOfCapitals("USA"));
        Assert.assertTrue(usageOfCapitals("Google"));
        Assert.assertTrue(usageOfCapitals("happy"));
        Assert.assertFalse(usageOfCapitals("HapPy"));

    }
}
