package com.entrepidea.algo.data_structure.misc;

import org.junit.Assert;
import org.junit.Test;

public class SwapValues {
    //3 rounds of Interview with Neuberger Berman
    //TODO 2. write code to swap values of variable A and B without using a temp vaiaralbe in a bit shifting way
    //https://stackoverflow.com/questions/14836228/is-there-a-standardized-method-to-swap-two-variables-in-python
    @Test
    public void swap(){
        int x = 7, y = 9;
        x = x^y;
        y = y^x;
        x = x^y;

        Assert.assertTrue(x==9);
        Assert.assertTrue(y==7);
    }

}
