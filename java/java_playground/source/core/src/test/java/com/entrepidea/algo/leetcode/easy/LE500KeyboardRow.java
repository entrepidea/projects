package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
/**
 * @Source: https://leetcode.com/problems/keyboard-row/
 * @Date: 10/20/20
 * */
public class LE500KeyboardRow {
    private boolean inSameRow(String word){
        boolean ret = true;
        char[][] keyboard = new char[][]{
            {'Q','W','E','R','T','Y','U','I','O','P' },
                {'A','S','D','F','G','H','J','K','L'},
                {'Z','X','C','V','B','N','M'}
        };
        char[] chars = word.toCharArray();
        int myRow = -1;
        for(char c : chars) {
            for (int row = 0; row < keyboard.length; row++) {
                for (int col = 0; col < keyboard[row].length; col++) {
                    if(Character.toUpperCase(c)==keyboard[row][col]){
                        if(myRow==-1){
                            myRow = row;
                        }
                        else{
                            if(row!=myRow){
                                ret = false;
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }

    @Test
    public void test(){
        Assert.assertTrue(inSameRow("Dad"));
        Assert.assertTrue(inSameRow("Alaska"));
        Assert.assertFalse(inSameRow("Hello"));
        String[] words = new String[]{"Hello", "Alaska", "Dad", "Peace"};
        Arrays.stream(words).filter(this::inSameRow).forEach(System.out::println);
    }
}
