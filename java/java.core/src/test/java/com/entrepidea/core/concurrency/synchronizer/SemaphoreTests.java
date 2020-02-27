package com.entrepidea.core.concurrency.synchronizer;

/**
 * @desc:
 * Counting Semaphores are used to control the number of the activities that can access certain resource or perform a given action at the same time.
 * A semaphore manages a set of permits, the method "acquire" blocks until there are available permit; and the method "release" adds a permit. If there is only one
 * permit, semaphore becomes a mutex with nonreentrant locking semantics; whoever holds the sole permit holds the mutex.
 * 
 *
 * @Date: Unknown, 02/27/20
 * 
 */
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTests {

	//The following demo one usage of semaphore: it's able to control the number of object in a set. One direct use might be found in creating a connection pool.
	//reference: Java Concurrency in Practice, Brian Goetz, p98
	class BoundedSet<T>{
		private Set<T> set;
		private Semaphore sem;
		public BoundedSet(int permits){
			sem = new Semaphore(permits);
			set = Collections.synchronizedSet(new HashSet<T>());
		}
		public boolean add(T o) throws InterruptedException {
			boolean wasAdded = false;
			try{
				sem.acquire();
				wasAdded = set.add(o);
			}
			finally{
				if(!wasAdded)
					sem.release();
			}
			return wasAdded;
		}
		
		public boolean remove(T o){
			boolean wasRemoved = set.remove(o);
			if(wasRemoved)
				sem.release();
			return wasRemoved;
		}
	}
	@Test
	public void test(){
		//TODO testing the class BoundedSet
	}


	//20 clients completing for a thread pool of 5. Implemented using semaphore.
	//reference: http://www.360doc.com/content/08/0113/11/15643_969120.shtml
	volatile static int count = 0;
	static int clientID=0;
	@Test
	public void test2() throws InterruptedException {
		final Semaphore sem = new Semaphore(5);

		ExecutorService exe = Executors.newCachedThreadPool();
		//simulate 20 clients to share a thread pool of 5
		for(int i=0;i<20;i++){
			Runnable run = () -> {
				try{
					sem.acquire();/*acquire a thread*/
					System.out.println("client "+(clientID++)+": acquire thread: "+count++);
					Thread.sleep((long)Math.random()*10000);
					sem.release();
					count--;
				}
				catch(InterruptedException ie){
					ie.printStackTrace();
				}
			};
			exe.execute(run);


		}

		exe.awaitTermination(5, TimeUnit.SECONDS);
	}
}
