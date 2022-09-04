package com.entrepidea.jersey.interview;

import org.junit.Test;

/**
 * @Desc:
 * Goldman Sachs code pad. See the email for details. The same question can be found in this link:
 * https://www.geeksforgeeks.org/trapping-rain-water/
 *
 * TODO
 *
 * @Date: 11/7/2019.
 */
public class Elevation {

    private int foo(Integer[] arr){
        if(arr.length==1){
            return 0;
        }
        int idx=1;
        while(idx<arr.length-1){
            //greater than left, less than right, move on
            //greater than left, greater than right, move on
            //
        }
        return 0;
    }

    @Test
    public void test(){
        int sum = foo(new Integer[]{0,1,3,0,1,2,0,4,2,0,3,0});
    }
}
