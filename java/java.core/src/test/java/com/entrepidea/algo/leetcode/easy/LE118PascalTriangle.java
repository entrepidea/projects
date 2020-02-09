package com.entrepidea.algo.leetcode.easy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description: Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
 * @Source: https://leetcode.com/problems/pascals-triangle/
 * @Date: 08/24/19
 * */
public class LE118PascalTriangle {

    private List<List<Integer>> generate(int rowNum){
        List<List<Integer>> l = new ArrayList<>();
        for(int i=0;i<rowNum;i++){
            l.add(new ArrayList<>());
        }
        List<Integer> firstRow = l.get(0);
        firstRow.add(1);
        for(int idx=1;idx<rowNum;idx++){
            List<Integer> preRow = l.get(idx-1);
            List<Integer> row = l.get(idx);
            row.add(1);
            for(int j=1;j<idx;j++){
                row.add(preRow.get(j-1)+preRow.get(j));
            }
            row.add(1);
        }
        return l;
    }

    @Test
    public void test(){
        List<List<Integer>> ret = generate(7);
        for(List<Integer> row : ret){
            for(Integer col : row) {
                System.out.print(col);
                System.out.print(",");
            }
            System.out.println();
        }
    }
}
