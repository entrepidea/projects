package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Desc: See this link: https://leetcode.com/problems/arranging-coins/
 * @Date: 06/27/20
 * */
public class LE441ArrangeCoins {

    int arrangeCoins(int n){
        int sum = 0;
        int i=1, k = 0;
        while(sum<=n){
            sum += i;
            i++;
            k++;
        }
        return k-1;
    }
    @Test
    public void test(){
        Assert.assertEquals(2, arrangeCoins(5));
        Assert.assertEquals(3, arrangeCoins(8));
    }

}
