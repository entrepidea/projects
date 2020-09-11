package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: https://leetcode.com/problems/heaters/
 * @Date: 08/21/20
 * */
public class LE475Heaters {
    private int minRadius(final int[] housePositions, final int[] heaterPositions){
        int min = 0;
        for(int i=1;i< heaterPositions.length;i++){
            int len = (heaterPositions[i]- heaterPositions[i-1])/2;
            if(len>min){
                min = len;
            }
        }
        if(heaterPositions[heaterPositions.length-1]==housePositions[housePositions.length-1]){
            return min>0||heaterPositions.length == housePositions.length?min:housePositions[housePositions.length-1]-housePositions[0];
        }
        else{
            if(housePositions[housePositions.length-1]-heaterPositions[heaterPositions.length-1]>min){
                min = housePositions[housePositions.length-1]-heaterPositions[heaterPositions.length-1];
            }
        }
        return min;
    }

    @Test
    public void test(){
        Assert.assertEquals(1, minRadius(new int[]{1,2,3}, new int[]{2}));
        Assert.assertEquals(1, minRadius(new int[]{1,2,3,4}, new int[]{1,4}));
        Assert.assertEquals(2, minRadius(new int[]{1,2,3,4}, new int[]{1,2}));
        Assert.assertEquals(1, minRadius(new int[]{1,2,3,4}, new int[]{1,3}));
        Assert.assertEquals(2, minRadius(new int[]{1,2,3,4,5}, new int[]{3}));
        Assert.assertEquals(0, minRadius(new int[]{1,2,3,4,5}, new int[]{1,2,3,4,5}));
        Assert.assertEquals(4, minRadius(new int[]{1,2,3,4,5}, new int[]{5}));
        Assert.assertEquals(2, minRadius(new int[]{1,2,3,4,5}, new int[]{1,5}));
    }
}
