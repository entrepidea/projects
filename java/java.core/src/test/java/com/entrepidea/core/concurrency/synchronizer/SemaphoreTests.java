package com.entrepidea.core.concurrency.synchronizer;

/**
 * @desc:
 * Counting Semaphores are used to control the number of the activities that can access certain resource or perform a given action at the same time.
 * A semaphore manages a set of permits, the method "acquire" blocks until there are available permit; and the method "release" adds a permit. If there is only one
 * permit, semaphore becomes a mutex with nonreentrant locking semantics; whoever holds the sole permit holds the mutex.
 *
 * Semaphore is used mostly to create a resource pool, controlling the availability of the resource in the pool. Like Connection pool, for instance.
 * Another use case is to set the "bound" for a collection. An example can be found in CJP and showed below.
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
	//reference: Java Concurrency in Practice, Brian Goetz, 5.5.3 "Semaphore", p98
	class BoundedSet<T>{
		private Set<T> set;
		private Semaphore sem;
		public BoundedSet(int permits){
			sem = new Semaphore(permits);
			set = Collections.synchronizedSet(new HashSet<>());
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
	public void test() throws InterruptedException {
		BoundedSet<String> bs = new BoundedSet<>(5);
		final int poolSize = 10;
		ExecutorService es = Executors.newFixedThreadPool(poolSize);
		for(int i=0;i<poolSize;i++){
			es.execute(()->{
				try {
					String str = Thread.currentThread().getName();
					while (!bs.add(str)) ;
					System.out.println(str+ " added a string,");
					Thread.sleep(1000);
					System.out.println(str + " waited for 1 sec and release the permit");
					bs.remove(str);
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
			});
		}
		es.awaitTermination(10, TimeUnit.SECONDS);
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


	/**
	 * BNP Paribas onsite 02/18/20
	 * Synchronizer. semaphore ? How does it work?
	 *
	 * */
	//Semaphore is a construct that hold a set of "permit" that can be allocated to threads for them to access shared resource.
	//A thread "acquire" a permit before using the resource and explicitly "release" the permit so the next waiting thread can acquire it. when permit is 1, the semaphore is degenerated to a mutex, acting like a lock. Only differ that semaphore needs to release the permit. A normal lock is released once the thread completes its running course.

	/**
	 * BNP Paribas onsite 02/18/20
	 * How does semaphore is different from Latch? CountdownLatcher? How is CountdownLatch different from CyclicBarrier
	 *
	 * */
	//Latch is created for threads collaboration. In the case of Latch (e.g CoutdownLatcher) the main thread is held up (blocked) until each participating thread complete its share of work, and then the main thread move on.
	//CyclicBarrier is useful in multi-phase task. On each phase, the main thread holds up until all participating threads complete their respective task; and holdup again at the next phase until the threads complete another set of tasks, respectively.

}
