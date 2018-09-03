package com.entrepidea.algo.tests.sort;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class BubbleSort {

	public void bubbleSort(int[] data)
	{
	   for (int k = 0; k < data.length - 1; k++)
	   {
	      boolean isSorted = true;

	      for (int i = 1; i < data.length - k; i++)
	      {
	         if (data[i] < data[i - 1])
	         {
	            int tempVariable = data[i];
	            data[i] = data[i - 1];
	            data[i - 1] = tempVariable;

	            isSorted = false;

	         }
	      }

	      if (isSorted)
	         break;
	   }
	}

	@Test
	public void testBubbleSorting(){

		int[] data = randomNum(0,1000, 30);
		bubbleSort(data);
		for (int i : data)
		{

			System.out.print(i);
			System.out.print(", ");
		}
		System.out.println();

	}

	//advanced way of creating an array of random numbers.
	//https://stackoverflow.com/questions/25793098/how-to-generate-random-array-of-ints-using-stream-api-java-8
	private int[] randomNum(int low, int high, int limit){
		return ThreadLocalRandom.current().ints(low,high).limit(limit).toArray();
	}

}
