package com.entrepidea.core.collection.tests;
/**
 * @author J.E.Y
 *
 * @description this is to demo the usage of the Interface BlockingQueue, using the P-C model. The BlockingQueue
 * stops receiving more elements when it's full. Familiarize yourselves with its implementations:ArrayBlockingQueue,
 * DelayQueue, LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue
 *
 */

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QueueTests {

	private static Logger log = LoggerFactory.getLogger(QueueTests.class);

	class Product {
		private int id;

		public Product(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	class Producer implements Runnable{



		private final BlockingQueue<Product> queue;
		private final Random rn = new Random();

		Producer(BlockingQueue<Product> q){
			queue = q;
		}

		@Override
		public void run() {
			try{
				//int i=0;
				while(true){
					Product p = new Product(rn.nextInt()%100);
					log.info("Puting product into Queue: "+p.getId());
					queue.put(p);
				}
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}

	class Consumer implements Runnable{

		private BlockingQueue<Product> queue;
		public Consumer(BlockingQueue<Product> q){
			queue = q;
		}
		@Override public void run(){
			try{
				while(true){
					Product p = queue.take();
					log.info("Taking product out from Queue: "+p.getId());
				}
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
	}

	@Test
	public void testBlockingQueue(){
		BlockingQueue<Product> queue = new LinkedBlockingQueue<Product>();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Producer(queue));
		service.execute(new Consumer(queue));
	}


	/**
	 * Simple Queue tests
	 * Create a simple implementation of a queue class.
	 * and add blocking feature
	 * */
	interface Queue<T>{
		 T take();
		 void put(T t);
	}

	//below is an implementation of a simple queue structure
	//the code is from here: https://codereview.stackexchange.com/questions/64258/array-implementation-of-queue
	//refer to this link: http://stackoverflow.com/questions/2536692/a-simple-scenario-using-wait-and-notify-in-java
	// to learn how to add blocking feature to the queue so that it can be safely used by multiple threads
	class SimpleQueue<T> implements Queue<T>{

		private int front;
		private int rear;
		int size;
		T[] queue;

		public SimpleQueue(int inSize) {
			size = inSize;
			queue = (T[]) new Object[size];
			front = -1;
			rear = -1;
		}

		public boolean isempty() {
			return (front == -1 && rear == -1);
		}

		@Override
		public synchronized void  put(T value) {
			while ((rear+1)%size==front) { //the queue is full
				//throw new IllegalStateException("Queue is full");
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (isempty()) {
				front++;
				rear++;
				queue[rear] = value;

			} else {
				rear=(rear+1)%size;
				queue[rear] = value;

			}

			notifyAll();

		}

		@Override
		public synchronized T take() {
			T value = null;
			while (isempty()) {
				//throw new IllegalStateException("Queue is empty, cant dequeue");
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (front == rear) {
				value = queue[front];
				front = -1;
				rear = -1;

			} else {
				value = queue[front];
				front=(front+1)%size;

			}

			notifyAll();
			return value;

		}

		@Override
		public String toString() {
			return "Queue [front=" + front + ", rear=" + rear + ", size=" + size
					+ ", queue=" + Arrays.toString(queue) + "]";
		}
	}

	class PrimitiveProducer implements Runnable{

		Queue<Integer> queue;
		PrimitiveProducer(Queue<Integer> q){
			queue = q;
		}
		@Override
		public void run() {
			Random rn = new Random();
			while(true){
				int i = rn.nextInt()%100;
				System.out.println("putting "+i);
				queue.put(i);
				//wait for 5 sec
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class PrimitiveConsumer implements Runnable{

		Queue<Integer> queue;

		PrimitiveConsumer(Queue<Integer> q){
			queue = q;
		}
		@Override
		public void run() {
			while(true){
				int i = queue.take();
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("taking "+i);
			}
		}
	}

	@Test
	public void testSimpleQueue(){
		Queue<Integer> newQueue = new SimpleQueue<>(5);
		PrimitiveProducer p = new PrimitiveProducer(newQueue);
		PrimitiveConsumer c = new PrimitiveConsumer(newQueue);
		ExecutorService exe = Executors.newCachedThreadPool();
		exe.submit(p);
		exe.submit(c);

		while(true){}

	}





    /**
     * TODO
     *
     * (markit) To design a cache framework, in such a scenario, you have already 50 objects in the map, that's its capacity. Now 51st object comes in, you have to remove the first object in the cache (this implies the map is sorted) to make room for the new comer. How do you go with that?
     answer: use LinkedBlockingQueue as the map's placeholder
     *
     * */

    //TODO Write a Queue implementation. And then make the Queue a blocking queue. (10/15/14, Markit on site)
}
