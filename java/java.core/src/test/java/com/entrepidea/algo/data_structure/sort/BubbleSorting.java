package com.entrepidea.algo.data_structure.sort;

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


	//redo, 02/26/19
	@Test
	public void test2(){
		int[] a =new int[]{50,32,67,18};
		ArrayUtil.printArry(a);
		for (int i=1;i<a.length;i++){
			boolean isSorted = true;
			for (int j=0;j<a.length-i;j++){
				if(a[j]<a[j+1]){
					int temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
					isSorted = false;
				}

			}
			if(isSorted){
				break;
			}

		}
		ArrayUtil.printArry(a);
	}

	//redo, 03/08/19
	@Test
	public void test3(){
		int[] a = ArrayUtil.generateRandomArray(1,100,10);
		ArrayUtil.printArry(a);
		for(int i=1;i<a.length;i++){
			boolean isSorted = true;
			for (int j=0;j<a.length-1;j++){
				if(a[j]>a[j+1]){
					int temp = a[j];
					a[j] = a[j+1];
					a[j+1] = temp;
					isSorted = false;
				}

			}
			if(isSorted){
				break;
			}
		}
		ArrayUtil.printArry(a);
	}


	//a little better,pay attention to the k variable.
	@Test
	public void testX(){
		int[] arr = new int[]{3,4,1,10,9,345,2,-1,300,210};
		int len = arr.length;
		int k=0;
		for(int i=1;i<len;i++){
			boolean sorted = true;
			for (int j=0;j<len-1-k;j++) {
				if (arr[j] > arr[j + 1]) {
					//swap
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
					sorted = false;
				}
			}
			if(sorted){
				break;
			}
			k++;
			ArrayUtil.printArry(arr);
		}

		//Arrays.asList(arr).forEach(System.out::println);
		/*for(int i: arr){
			System.out.println(i);
		}
*/	}

	/*Bubble sorting can be used to pick up the top k elements in the array, only k loops are required in the outter loop */
	@Test
	public void topKelements(){
		int[] arr = new int[]{3,210,4,1,10,9,345,2,-1,300,21};
		int len = arr.length;
		int k =3;
		for(int n=0,i=1;i<len & n<k;i++,n++){
			boolean isSorted = true;
			for(int j=0;j<len-1;j++){
				if(arr[j]>arr[j+1]){
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
					isSorted = false;
				}
			}
			if(isSorted){
				break;
			}
		}

		ArrayUtil.printArry(arr);
	}





}
