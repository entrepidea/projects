package com.entrepidea.algo.tests.misc;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/excel-sheet-column-title/
 * @Description: convert the number to the excel column title
 * @Date: 09/14/19
 */
public class LE168ExcelColumnTitle {

    private String convertToTile(int n){
        StringBuilder stringBuilder = new StringBuilder();
        while(n>0){
            stringBuilder.append((char)(--n%26+'A'));
            n /=26;
        }
        return stringBuilder.reverse().toString();
    }

    @Test
    public void test(){
        Assert.assertEquals(convertToTile(1), "A");
        Assert.assertEquals(convertToTile(2), "B");
        Assert.assertEquals(convertToTile(26), "Z");
        Assert.assertEquals(convertToTile(27), "AA");
        Assert.assertEquals(convertToTile(701), "ZY");
        Assert.assertEquals(convertToTile(702), "ZZ");
        Assert.assertEquals(convertToTile(703), "AAA");
    }

}
