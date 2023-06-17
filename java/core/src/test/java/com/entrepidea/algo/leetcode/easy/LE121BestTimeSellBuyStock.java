package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;
/**
 * @Description:
 *  Say you have an array for which the ith element is the price of a given stock on day i.
    If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock),
    design an algorithm to find the maximum profit.
    Note that you cannot sell a stock before you buy one.

 * @Source: https://www.baeldung.com/java-initialize-array
 * @Date: 08/25/19
 * @Comment: TODO should be a better way
 *
 * */
public class LE121BestTimeSellBuyStock {

    private int maxProfit(int[] prices){
        int max = 0;
        for(int idx=0;idx<prices.length-1;idx++){
            int buyPrice = prices[idx];
            for(int j=idx+1;j<prices.length;j++){
                if(prices[j]>buyPrice){
                    if((prices[j]-buyPrice)>max){
                        max = prices[j]-buyPrice;
                    }
                }
            }
        }
        return max;
    }

    @Test
    public void test(){
        int[] array = {7,1,5,3,6,4};
        Assert.assertTrue(maxProfit(array)==5);
    }
}
