package com.entrepidea.algo.leetcode.easy;

import org.junit.Assert;
import org.junit.Test;

/**
 * @source: https://leetcode.com/problems/next-greater-element-i/
 * @Date: 10/20/20
 * */
public class LE496NextGreatElement {
    private int[] nextGreatElement(int[] num1, int[] num2){

        int[] ret = new int[num1.length];
        int idx = 0;
        for(int num : num1){
            int index = -1;
            for(int j=0;j<num2.length;j++){
                if(num==num2[j]){
                    index = j;
                    break;
                }
            }
            if(index==-1){
                ret[idx++] = -1;
            }
            else{
                boolean found = false;
                for(int k=index+1;k<num2.length;k++){
                    if(num2[k]>num){
                        ret[idx++] = num2[k];
                        found = true;
                        break;
                    }
                }
                if(!found){
                    ret[idx++] = -1;
                }
            }
        }
        return ret;
    }

    @Test
    public void test(){
        int[] num1 = new int[]{4,1,2};
        int[] num2 = new int[]{1,3,4,2};
        int[] ret= nextGreatElement(num1,num2);
        for( int i : ret){
            System.out.println(i);
        }

        Assert.assertArrayEquals(new int[]{-1,3,-1}, ret);
    }
}
