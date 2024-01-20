package com.entrepidea.algo.leetcode.easy;

import org.junit.Test;

/**
 * @Source: https://leetcode.com/problems/reverse-bits/
 * @Desc: Reverse bits of a given 32 bits unsigned integer.
 * Created by jonat on 10/31/2019.
 */
public class LE190ReverseBits {

/*    private String reverse(String str){
        if(str.length()==1){
            return str;
        }

    }*/


    private void reverse(char[] chars){
        int last = chars.length -1;
        int mid = chars.length/2;
        for(int i=0;i<mid;i++){
            char temp = chars[i];
            chars[i] = chars[last-i];
            chars[last-i] = temp;
        }
    }
    private long convert(char[] chars){
        long sum=0;
        int count=0;
        for(int i=chars.length-1;i>=0;i--){
            int bit = (int)chars[i]-48;
            sum+= bit*Math.pow(2,count++);
        }
        return sum;
    }

    private void print(char[] chars){
        for(char c: chars){
            System.out.print(c);
        }
        System.out.println();
    }


    @Test
    public void testFoo(){
        String str = "00000010100101000001111010011100";
        char[] chars = str.toCharArray();
        print(chars);
        System.out.println(convert(chars));
        reverse(chars);
        print(chars);
        System.out.println(convert(chars));



    }

    @Test
    public void testFoo2(){
        String str = "11111111111111111111111111111101";
        char[] chars = str.toCharArray();
        print(chars);
        System.out.println(convert(chars));
        reverse(chars);
        print(chars);
        System.out.println(convert(chars));
    }
}
