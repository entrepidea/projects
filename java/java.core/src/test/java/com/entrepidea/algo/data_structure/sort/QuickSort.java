package com.entrepidea.algo.data_structure.sort;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
Quick sorting: the idea of quick sorting is to pick up a random element (known as "pivot"), 
to compare with the rest. Those less than the pivot form a new array, those greater forms another, 
these two as well as the pivot will form an array like less_array + pivot + greater_array. 
The above steps are repeated against those less and greater respectively (recursion).

**/
public class QuickSort {

	//non-recursive implementation
	private int[] concatenate(int[] less, int pivot, int[] greater) {
		int[] ret = new int[less.length + greater.length + 1];
		for (int i = 0; i < less.length; i++) {
			ret[i] = less[i];
		}
		ret[less.length] = pivot;
		for (int i = 0; i < greater.length; i++) {
			ret[less.length + 1 + i] = greater[i];
		}
		return ret;
	}

	private int[] sort(int[] array) {
		int[] ret = array;
		if (array.length <= 1) {
			return ret;
		} else {
			// select and remove a pivot
			int pivot_index = array.length / 2;
			int pivot = array[pivot_index];
			int[] less = new int[array.length];
			int[] greater = new int[array.length];
			int index = 0, index1 = 0, index2 = 0;
			for (int x : array) {
				if (index == pivot_index) {
					index++;
					continue; // skip the pivot element
				}
				if (x <= pivot) {
					less[index1++] = x;
				} else {
					greater[index2++] = x;
				}
				index++;
			} // ~for~
			int[] new_less = new int[index1];
			for (int i = 0; i < index1; i++) {
				new_less[i] = less[i];
			}
			int[] new_greater = new int[index2];
			for (int i = 0; i < index2; i++) {
				new_greater[i] = greater[i];
			}
			less = null;
			greater = null;
			ret = concatenate(sort(new_less), pivot, sort(new_greater));
			return ret;
		}
	}

	@Test
	public void testSorting() {

		int[] testArray = ThreadLocalRandom.current().ints(0,100).limit(10).toArray();


		for (int i : testArray) {
			System.out.print(i + ",");
		}
		System.out.println();

		int[] ret = new QuickSort().sort(testArray);
		for (int i : ret) {
			System.out.print(i + ",");
		}
		System.out.println();
	}


	//recursive implementation
	//Lomuto partition scheme to select a pivot
	//below code follows the psudo code from https://en.wikipedia.org/wiki/Quicksort
	private int partition(int[] arr, int lo, int hi){
		int pivot = arr[hi];
		int i = lo;

		for( int j=lo; j<hi;j++){
			if (arr[j]<pivot){
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
			}
		}
		int temp = arr[i];
		arr[i] = arr[hi];
		arr[hi] = temp;
		return i;
	}

	private void sort(int[] arr, int lo, int hi){
		if(lo<hi){
			int p = partition(arr, lo, hi);
			sort(arr, lo, p-1);
			sort(arr, p+1, hi);
		}
	}

	@Test
	public void testSorting2(){
		int[] arr = ThreadLocalRandom.current().ints(0,100).limit(20).toArray();
		sort(arr, 0, arr.length-1);
		for (int a : arr){
			System.out.print(a);
			System.out.print("\t");
		}
		System.out.println();
	}

}

