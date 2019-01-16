package com.entrepidea.algo.tests.sort;

import org.junit.Test;
/**
 * desc: bubble sorting: like a bubble rises from the bottom of the water to the top.
 * Iterate from the lest to the right, if the element is greater than the one next right to it, swap.
 * source: https://www.geeksforgeeks.org/bubble-sort/
 * date: 01/13/19
 * */
public class BubbleSorting {

	@Test
	public void test(){
		int[] a = ArrayUtil.generateRandomArray(1,100,10);
		ArrayUtil.printArry(a);
		for(int i=1;i<a.length;i++){
			boolean isSorted = true;
			for(int j=0;j<a.length-i;j++){
				if(a[j]>a[j+1]){
					ArrayUtil.swap(a,j,j+1);
				}
				isSorted = false;
			}
			if(isSorted){
				break;
			}
		}
		ArrayUtil.printArry(a);

	}

}
