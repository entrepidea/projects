package com.entrepidea.algo.misc;

import org.junit.Assert;
import org.junit.Test;
/**
 * Double-pointer problems. Find the largest volume of a water container.
 * https://www.bilibili.com/video/av68840933
 *
 * 01/10/21
 *
 * */
public class WaterContainer {
    private int volume(int[] bars){
        int idx = 0;
        int lastIdx = bars.length - 1;
        int vol = 0;
        while(idx!=lastIdx){
            int v = 0;
            if(bars[idx]<bars[lastIdx]){
                v = (lastIdx - idx)*bars[idx];
                idx++;
            }
            else{
                v = (lastIdx - idx)*bars[lastIdx];
                lastIdx--;
            }
            if(v>vol){
                vol = v;
            }
        }

        return vol;
    }

    @Test
    public void test(){
        int[] bars = new int[]{1,8,6,2,5,4,8,3,7};
        Assert.assertEquals(49, volume(bars));

        bars = new int[]{1,8,6,2,5,4,8,3,8};
        Assert.assertEquals(56, volume(bars));

        bars = new int[]{1,8,6,2,5,4,8,3,2};
        Assert.assertEquals(40, volume(bars));
    }

}
