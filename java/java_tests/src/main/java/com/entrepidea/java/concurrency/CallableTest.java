package com.entrepidea.java.concurrency;

/**
 * Callable is used in conjunction with Future. When a task is implementing a callable, it must specify a 
 * returned type. That's the type that Future needs.
 * These are the steps on how to use callable/future
 * 1. write a callable implementation, with a specified type - this type will be used as the return type of call()
 * 2. submit the callable implementation, and get a future reference;
 * 3. once securing a future reference, you can use future's apis such as isDone(), get() to work on the callables. 
 *
 * */
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

public class CallableTest {

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
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				service.shutdown();
			}
		}
	}
}
