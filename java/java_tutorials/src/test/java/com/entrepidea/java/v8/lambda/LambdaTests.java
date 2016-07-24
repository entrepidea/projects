package com.entrepidea.java.v8.lambda;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

/**
 * Lambda was introduced since JDK 1.8. The basic idea is that functions can be used as data.
 * To be continued...
 * {@link TBD}
 * 
 * */
public class LambdaTests {

	//in this example, runnable and callable as a thread worker can be a good candidate for lambda.
	//these two interfaces have only one method respectively (run and call) therefore Java is smart 
	//enough to infer that out. That's how it looks: () -> 
	@Test
	public void lambdaTest() throws InterruptedException, ExecutionException{
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Runnable r = () -> System.out.println("Hello, I am programmed in Lambda way.");
		executor.submit(r);
		executor.shutdown();
		executor = Executors.newCachedThreadPool();
		Callable<String> c = () ->  "Hello.";
		Future<String> f = executor.submit(c);
		Assert.assertEquals("Hello.", f.get());
		executor.shutdown();
		
	}
}
