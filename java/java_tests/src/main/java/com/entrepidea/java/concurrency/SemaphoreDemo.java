package com.entrepidea.java.concurrency;

/**
 * @author Hai Yi
 * @description Counting Semaphores are used to control the number of the activities that can access certain resource or perform a given action at the same time.
 * A semaphore manages a set of permits, the method "acquire" blocks until there are available permit; and the method "release" adds a permit. If there is only one
 * permit, semaphore becomes a mutex with nonreentrant locking semantics; whoever holds the sole permit holds the mutex.
 * 
 * The following demo one usage of semaphore: it's able to control the number of object in a set. One direct use might be found in creating a connection pool.
 * 
 * @reference: Java Concurrency in Practice, Brian Goetz, p98
 * 
 */
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
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
}
