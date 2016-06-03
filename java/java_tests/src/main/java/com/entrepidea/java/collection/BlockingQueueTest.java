package com.entrepidea.java.collection;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

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

	private static Logger log = LoggerFactory.getLogger(Producer.class);
	
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
	private static Logger log = LoggerFactory.getLogger(Consumer.class);
	
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
public class BlockingQueueTest extends TestCase {
	
	@org.junit.Test
	public void testBlockingQueue(){
		BlockingQueue<Product> queue = new LinkedBlockingQueue<Product>();
		ExecutorService service = Executors.newCachedThreadPool();
		service.execute(new Producer(queue));
		service.execute(new Consumer(queue));
	}
}
