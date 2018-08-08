package com.entrepidea.algo.tests.array;

/*
* Mogan stanley and HSBC interviews (by tan bin) 07/18/17
TODO
	flat a 2-d array to 1-d array
	flat a 3-d array to 1-d array
	the 1-d array has been pre-allocated, just do the pigon hold intesion.
*/
public class FlatenArrays {

    private static int[] flat(int[][] target){
        int rows = target.length;
        int cols = target[0].length;
        int[] ret = new int[rows*cols];
        for (int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                ret[i*cols+j] = target[i][j];
            }
        }
        return ret;
    }

    public static void main(String[] args){
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
}
