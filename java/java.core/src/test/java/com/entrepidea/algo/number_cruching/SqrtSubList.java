package com.entrepidea.algo.number_cruching;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

public class SqrtSubList {
    /**
     * Goldman Sachs onsite interview, 01/27/20, 11:00AM
     * For an array, 1,2,3 ... 100, find out the sub array that can be square rooted. Performance is important.
     * */
    //solution#1
    private Integer[] sList(int[] arr){
        Set<Integer> s = new HashSet<>();
        for(int i=1;i<=(int)Math.sqrt(arr[arr.length-1]);i++){
            s.add(i*i);
        }
        List<Integer> l = new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            if(s.contains(arr[i])){
                l.add(arr[i]);
            }
        }
        Integer[] ret = new Integer[l.size()];
        ret = l.toArray(ret);
        return ret;
    }

    @Test
    public void test(){
        Integer[] arr = sList(IntStream.rangeClosed(1,100).toArray());
        Arrays.stream(arr).forEach(System.out::println);
    }

    //4=1+3
    //9=1+3+5
    //16=1+3+5+7
    //25=1+3+5+7+9
    //...
    //you can find the pattern
    //solution#2
    @Test
    public void test2(){
        int rst = 1;
        int num1=rst;
        int num2=num1;
        while(rst<=1000){
            System.out.println(rst);
            num1 = rst;
            num2 +=2;
            rst = num1+num2;
        }
    }
}
