import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/**
 * @description: merge sorting is known as divide and conquer argo.
 * complexity is nlog(n) 
 * 
 * http://www.vogella.com/articles/JavaAlgorithmsMergesort/article.html
 * 
 * */
class Mergesort {
	  private int[] numbers;
	  private int[] helper;

	  private int number;

	  public void sort(int[] values) {
	    this.numbers = values;
	    number = values.length;
	    this.helper = new int[number];
	    mergesort(0, number - 1);
	  }

	  private void mergesort(int low, int high) {
	    // Check if low is smaller then high, if not then the array is sorted
	    if (low < high) {
	      // Get the index of the element which is in the middle
	      int middle = low + (high - low) / 2;
	      // Sort the left side of the array
	      mergesort(low, middle);
	      // Sort the right side of the array
	      mergesort(middle + 1, high);
	      // Combine them both
	      merge(low, middle, high);
	    }
	  }

	  private void merge(int low, int middle, int high) {

	    // Copy both parts into the helper array
	    for (int i = low; i <= high; i++) {
	      helper[i] = numbers[i];
	    }

	    int i = low;
	    int j = middle + 1;
	    int k = low;
	    // Copy the smallest values from either the left or the right side back
	    // to the original array
	    while (i <= middle && j <= high) {
	      if (helper[i] <= helper[j]) {
	        numbers[k] = helper[i];
	        i++;
	      } else {
	        numbers[k] = helper[j];
	        j++;
	      }
	      k++;
	    }
	    // Copy the rest of the left side of the array into the target array
	    while (i <= middle) {
	      numbers[k] = helper[i];
	      k++;
	      i++;
	    }

	  }
	}

public class MergeSortingTest {

	  private int[] numbers;
	  private final static int SIZE = 7;
	  private final static int MAX = 20;

	  @Before
	  public void setUp() throws Exception {
	    numbers = new int[SIZE];
	    Random generator = new Random();
	    for (int i = 0; i < numbers.length; i++) {
	      numbers[i] = generator.nextInt(MAX);
	    }
	  }

	  @Test
	  public void testMergeSort() {
	    long startTime = System.currentTimeMillis();

	    Mergesort sorter = new Mergesort();
	    sorter.sort(numbers);

	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Mergesort " + elapsedTime);

	    for (int i = 0; i < numbers.length - 1; i++) {
	      if (numbers[i] > numbers[i + 1]) {
	        fail("Should not happen");
	      }
	    }
	    assertTrue(true);

	  }

	  @Test
	  public void itWorksRepeatably() {
	    //for (int i = 0; i < 200; i++) {
	      numbers = new int[SIZE];
	      Random generator = new Random();
	      for (int a = 0; a < numbers.length; a++) {
	        numbers[a] = generator.nextInt(MAX);
	        System.out.print(numbers[a]+"\t");
	      }
	      System.out.println();
	      Mergesort sorter = new Mergesort();
	      sorter.sort(numbers);
	      
	      for (int a = 0; a < numbers.length; a++) {
		        System.out.print(numbers[a]+"\t");
		      }
		      System.out.println();
	      
	      for (int j = 0; j < numbers.length - 1; j++) {
	        if (numbers[j] > numbers[j + 1]) {
	          fail("Should not happen");
	        }
	      }
	      assertTrue(true);
	   // }
	  }

	  @Test
	  public void testStandardSort() {
	    long startTime = System.currentTimeMillis();
	    Arrays.sort(numbers);
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("Standard Java sort " + elapsedTime);

	    for (int i = 0; i < numbers.length - 1; i++) {
	      if (numbers[i] > numbers[i + 1]) {
	        fail("Should not happen");
	      }
	    }
	    assertTrue(true);
	  }
}

