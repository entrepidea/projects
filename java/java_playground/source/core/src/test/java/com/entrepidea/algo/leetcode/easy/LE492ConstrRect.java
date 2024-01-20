package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class LE492ConstrRect {

    private int[] constructRect(int area){
        int l = 1;
        while(l*l<area){
            l+=1;
        }
        if(l*l==area){
            return new int[]{l,l};
        }
        else{
            int w = l-1;
            while(l*w!=area){
                l+=1;
                w-=1;
            }
            return new int[]{l,w};
        }

    }    @Test
    public void test(){
        //Assert.assertArrayEquals(new int[]{2,2}, constructRect(4));
        //Assert.assertArrayEquals(new int[]{6,3}, constructRect(18));
        for(int x:constructRect(156)){
            System.out.println(x);
        }
    }
}
