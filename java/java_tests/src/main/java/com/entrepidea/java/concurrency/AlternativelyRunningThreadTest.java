package com.entrepidea.java.concurrency;

public class AlternativelyRunningThreadTest {

	public static class Task1 extends Thread {
		private int[] numbers = { 1, 3, 5, 7, 9 };

		private int count = 0;
		private BoolWrapper isOdd;
		private Object lock;
		public Task1(Object lock, BoolWrapper isOdd) {
			this.lock = lock;
			this.isOdd = isOdd;
		}

		@Override
		public void run() {
			while (count<numbers.length) {
				synchronized (lock) {
					if(isOdd.isValue() == false){
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.print(numbers[count++]+" ");
					isOdd.setValue(false);
					lock.notify();
				}
			}
		}
	}

	public static class Task2 extends Thread {
		private int[] numbers = { 2, 4, 6, 8, 10 };

		private int count = 0;

		private BoolWrapper isOdd;
		private Object lock;

		public Task2(Object lock, BoolWrapper isOdd) {
			this.lock = lock;
			this.isOdd = isOdd;
		}

		@Override
		public void run() {
			synchronized (lock) {
				while (count<numbers.length) {
					if(isOdd.isValue() == true){
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.print(numbers[count++]+"  ");
					isOdd.setValue(true);
					lock.notify();
				}
			}
		}
	}

	static class BoolWrapper{
		private boolean value;

		public BoolWrapper(boolean v){
			value = v;
		}
		public boolean isValue() {
			return value;
		}

		public void setValue(boolean value) {
			this.value = value;
		}
		
	}
	static volatile BoolWrapper isOdd = new BoolWrapper(true);
	public static void main(String[] args) {
		Object lock = new Object();
		Task1 t1 = new Task1(lock, isOdd);
		Task2 t2 = new Task2(lock, isOdd);

		t1.start();
		t2.start();
	}
}
