package com.entrepidea.algo.interview;

import org.junit.Assert;
import org.junit.Test;

/**
 * @Desc:
 * The details of the question can be found in here:
 * https://www.geeksforgeeks.org/stock-buy-sell/
 * However there seemed to have two variations of this problem:
 * 1. Buy or sell only once each day
 * 2. Buy 1 sell any times each day
 *
 * The link above covers both scenarios, and my friend Victor Tan gave a solution for the 2nd variation too:
 * https://github.com/tiger40490/repo1/blob/py1/py/algo_arr/maxProfit_buy1sellAny.py
 *
 * @date: 03/13/20, 07/09/20
 *
 * @Note:
 * This question was asked on a 01/27/20 Goldman onsite interview and again in a Bank of New York HankRank test on 02/18/20.
 *
 * */

public class BuySellStock4MaxProfit {
    //buy one, sell multiple
    int profit(int[] prices, int start, int end){
        if(end<=start){
            return 0;
        }
        int maxProfit =0;
        for(int i=start;i<end;i++){
            for(int j=i+1;j<=end;j++){
                if(prices[j]>prices[i]){
                    int curProfit = prices[j]-prices[i]
                            +profit(prices,start, i-1)
                            + profit(prices, j+1, end);
                    maxProfit = Math.max(maxProfit, curProfit);
                }
            }
        }
        return maxProfit;
    }

    @Test
    public void test(){
        int[] prices = new int[]{100, 180, 260, 310, 40, 535, 695};
        int p = profit(prices, 0, prices.length-1);
        Assert.assertEquals(p, 865);
    }

    //buy/sell once only for each day
    int profit2(int[] prices){
        int maxProfit = prices[1] - prices[0];
        int minPrice = prices[0];
        for(int i=1;i<prices.length;i++){
            int diff = prices[i] - minPrice;
            if(diff>maxProfit){
                maxProfit = diff;
            }
            if(prices[i]<minPrice){
                minPrice = prices[i];
            }
        }
        return maxProfit;
    }

    @Test
    public void test2(){
        int[] prices = new int[]{1, 2, 90, 10, 110};
        int p = profit2(prices);
        Assert.assertEquals(p, 109);
    }

    //I wrote below algo on 07/09/20. Miss anything? The test passed.
    int profit3(int[] arr){
        int low = arr[0];
        int high = arr[0];
        int p = 0;
        for (int i=1;i<arr.length;i++){
            if(arr[i]>high){
                high = arr[i];
            }
            else{
                if(high>low) {
                    p += (high - low);
                }
                low = high = arr[i];
            }
        }
        if(high>low){
            p+=(high - low);
        }
        return p;
    }

    @Test
    public void test3(){
        int[] quotes = new int[]{100,180,260,310,40,535,695};
        Assert.assertEquals(865, profit3(quotes));
        quotes = new int[]{100,90,80,70,60,50,40};
        Assert.assertEquals(0, profit3(quotes));
        quotes = new int[]{100,90,80,70,60,50,695};
        Assert.assertEquals(645, profit3(quotes));
    }
}
