package com.entrepidea.java.concurrency.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * TODO: let's discuss asynchonous features in both Java and C#.
 * it's said that C# has a better design in this regard and their support is on the language level with async keyword
 * well, still much to delve in...
 *
 * */
public class AyncIOTests {

	class Task implements Callable<String>{
		private int id;
		Task(int id){
			this.id = id;
		}
		@Override
		public String call(){
			return "Task id = "+ id;
		}
	}


	/**
	 * Callable is used in conjunction with Future, a construct representing a result from an async computation
	 * (Note: Future might be called Promises in other languages, which "promise" to return something in the future ).
	 * When a task implements a Callable interface, it must specify a returned type, which is used by Future.

	 * These are the steps on how to use callable/future
	 * 1. write a callable implementation, with a specified type - this type will be used as the return type of call()
	 * 2. submit the callable implementation, and get a future reference;
	 * 3. once securing a future reference, you can use future's apis such as isDone(), get() to work on the callables.
	 *
	 * */
	@Test
	public void testCallable(){
		List<Future<String>> results = new ArrayList<Future<String>>();
		ExecutorService service = Executors.newSingleThreadExecutor();
		for(int i=0;i<10;i++){
			Future<String> f = service.submit(new Task(i));
			results.add(f);
		}
		
		for(Future<String> fs:results){
			try {
				if(fs.isDone()){
					System.out.println(fs.get());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			finally{
				service.shutdown();
			}
		}
	}


	//Barcap onsite interview, 10/09/14
	//TODO What's asynchronous IO? How to achieve highly efficient concurrent processing services.
	//In a scenario, we have 10 requests being handled by all available threads in a thread pool, they are long processing tasks,
	// thus more requests come in but can't be processed, then what? How to design an architecture that can prioritize the requests
	// so that clients can have reasonable responsiveness.

	//BNP Paribas onsite, Jersey City, GWT UI programmer position, 10/14/2014
	//TODO 12. Difference b/w callable and runnable


}
