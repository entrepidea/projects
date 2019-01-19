package com.entrepidea.algo.tests.array;

import org.junit.Test;

/*
* Mogan stanley and HSBC interviews (by tan bin) 07/18/17
	flat a 2-d array to 1-d array
	flat a 3-d array to 1-d array
	the 1-d array has been pre-allocated, just do the pigeon hole insertion.
*/
public class FlattenArrays {

    private int[] flat(int[][] source){
        int rows = source.length;
        int cols = source[0].length;
        int[] ret = new int[rows*cols];
        for (int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                ret[i*cols+j] = source[i][j];
            }
        }
        return ret;
    }


    @Test
    public void test(){
        int[][] target = {
                {1,2,3},
                {4,5,6},
                {7,8,9},
                {10,11,12}
        };

        int[] ret = flat(target);

        for(int i: ret){
            System.out.print(i+"\t");
        }
        System.out.println();
    }


    private int[] flat2(int[][][] source){
        int len = source.length;
        len *= source[0].length;
        len *= source[0][0].length;

        int[] ret = new int[len];
        int x = 0;
        for (int i=0;i<source.length;i++){
            for(int j=0;j<source[0].length;j++){
                for (int k=0;k<source[0][0].length;k++){
                    ret[x++] = source[i][j][k];
                }
            }
        }
        return ret;
    }

    @Test
    public void test2(){
        int[][][] source = new int[][][]{
                {
                        {0,1,2},
                        {3,4,5}
                },
                {
                        {6,7,8},
                        {9,10,11}
                },
                {
                        {12,13,14},
                        {15,16,17}
                },
        };
        int[] result = flat2(source);
        for (int i=0;i<result.length;i++){
            System.out.print(result[i]+"\t");
        }
        System.out.println();
    }

}
