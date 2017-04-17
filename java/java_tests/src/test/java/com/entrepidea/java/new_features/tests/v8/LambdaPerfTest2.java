package com.entrepidea.java.new_features.tests.v8;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/*
 * Lamda replaces anonymous inner class hence improves the readability; 
 * and the retrofitted collections for lambda use internal iteration rather than using external
 * iteration. these are the two good things I can perceived.
 * As of performance, there is no evidence that lambda is better than non-lambda version.
 * 
 * https://www.reddit.com/r/java/comments/2suvir/java_8_lambda_performance_is_not_great/  
 * 
 * The article above address Lambda performance issue, it's also claimed that Stream is like LINQ and LINQ is an abstract 
 * working on data source like SQL.
 *  
 * */
public class LambdaPerfTest2 {

	@Test
	public void test() {
		List<Double> list = Arrays.asList(new Double[]{1.2,3.2,4.5,6.6,10.01,-0.45,0.09});
		
		long start = System.nanoTime();
		double n = list.stream().min(Double::compare).get();
		System.out.println("take : "+(System.nanoTime()-start) +" ns to complete. The min is: "+n);
		
		start = System.nanoTime();
		double min = Double.MAX_VALUE;
		for(Double d : list){
			if(d<min){min = d;}
		}
		System.out.println("take : "+(System.nanoTime()-start) + " ns to complete the regular way.");
	}
}
