package com.entrepidea.java.collection.tests;
/**
 * @author Hai YI
 *
 * @description this is to demo the usage of the Interface BlockingQueue, using the P-C model. The BlockingQueue
 * stops receiving more elements when it's blocked. Familiar yourselves with its implementations:ArrayBlockingQueue, 
 * DelayQueue, LinkedBlockingQueue, PriorityBlockingQueue, SynchronousQueue
 * 
 * @see also an old fasion implementation of P-C model at com.entrepidea.java.concurrent.P_C.PCTest;
 */

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

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
     * TODO
     *
     * (markit) To design a cache framework, in such a scenario, you have already 50 objects in the map, that's its capacity. Now 51st object comes in, you have to remove the first object in the cache (this implies the map is sorted) to make room for the new comer. How do you go with that?
     answer: use LinkedBlockingQueue as the map's placeholder
     *
     * */
}
