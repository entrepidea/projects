package com.entrepidea.core.design_pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * 
 * @author Hai Yi
 * @description this is an old way to demo a producer-consumer algorithm. The
 *              components are: - ProductList: a singleton class maintaining a
 *              product list - Product: the product class - Producer: the
 *              producer class which consistently adds new product to the
 *              product list 'till it's full. The list is locked during the
 *              operation, to prevent it from being intermingled from other
 *              thread (Consumer in this case), and it's unlocked when the
 *              operation is completed by invoking 'notify'. - Consumer: the
 *              consumer class which consistently takes product out from the
 *              product list 'till it's empty.
 * 
 * @see also there is P-C model implementation using BlockingQueue in the
 *      package com.entrepidea.core.generic
 * 
 * @reference: http://www.blogjava.net/fidodido/archive/2005/10/11/15269.html
 */
public class ProdConsTests extends TestCase {

	static class Product {
		private int id;

		public Product(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	public static class ProductList {
		private static ProductList instance = new ProductList();
		private final static int SIZE = 10;
		private List<Product> products;

		private ProductList() {
			products = new ArrayList<Product>(SIZE);
		}

		public static ProductList newInstance() {
			return instance;
		}

		public boolean isFull() {
			return products.size() == SIZE;
		}

		public void put(Product product) {
			products.add(product);
		}

		public Product take() {
			return products.remove(0);
		}

		public boolean isEmpty() {
			return products.isEmpty();
		}
	}

	static class Producer extends Thread {

		private ProductList products = null;
		private Random rn = new Random();

		public Producer(ProductList l) {
			products = l;
		}

		public void run() {

			synchronized (products) {
				while(products.isFull()){
					try {
						products.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				while (!products.isFull()) {
					Product product = new Product(rn.nextInt() % 100);
					products.put(product);
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					System.out.println("Produced "+ product.getId());
					products.notifyAll();// release the lock
					
				}// ~ while ~
				
				
			}

		}
	}

	static class Consumer extends Thread {

		private ProductList products = null;

		public Consumer(ProductList l) {
			products = l;
		}

		public void run() {
			synchronized (products) {
				while(true){
				while (products.isEmpty()) {
					try {
						products.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Product product = products.take();
				System.out.println("Consumed " + product.getId());
				products.notifyAll();
				}
			}
		}
	}

	@Test
	public void test() {
		final ProductList products = ProductList.newInstance();
		new Producer(products).start();
		new Consumer(products).start();

	}

	//10/01/14, 5:30PM, BofA phone interview with Wilson
	//TODO 9. How do you implement Producer-consumer pattern?

}
