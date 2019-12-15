package interview;

import org.junit.Assert;

/**
 * @Desc: this is an on-site interview question from Nomura. 11/20/19
 * You are climbing a stair case. It takes n steps to reach to the top.
 *
 * Each time you can either climb 1 or 2 steps. In how many distinct ways
 * can you climb to the top?
 *
 * This is a variant of Fibonacci numbers:
 * if n = 1, ways of climbing n stairs is 1.
 * if n = 2, ways of climbing n stairs is 2
 * (one way is one step a time, the other ways is to take two steps in the first round).
 * for n >= 3,
 * let f(n) be the number of ways to climb n stairs,
 * then f(n) = f(n-1) + f(n-2)
 * where we begin to take either one step or two steps in the first round.
 * If we take one step in the first round, then we have f(n-1) ways to finish the rest steps.
 * If we take two steps in the first round, then we have f(n-2) ways to finish the rest steps.
 *
 * */
public class Stairs {

    private int climb(int n){
        if(n<3) return n;
        return climb(n-1) + climb(n-2);
    }
    public static void main(String[] args){
        Stairs stairs = new Stairs();
        Assert.assertTrue(stairs.climb(1)==1);
        Assert.assertTrue(stairs.climb(2)==2);
        Assert.assertTrue(stairs.climb(3)==3);
        System.out.println(stairs.climb(5));
    }
}
