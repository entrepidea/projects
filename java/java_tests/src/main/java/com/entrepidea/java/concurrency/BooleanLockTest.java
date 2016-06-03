package com.entrepidea.java.concurrency;

/**
 * what does this test do?
 * 
 * @author John
 * @date 
 * */

public class BooleanLockTest {

	static class BooleanLock {
		  private boolean value;

		  public BooleanLock(boolean initialValue) {
		    value = initialValue;
		  }

		  public BooleanLock() {
		    this(false);
		  }

		  public synchronized void setValue(boolean newValue) {
		    if (newValue != value) {
		      value = newValue;
		      notifyAll();
		    }
		  }

		  public synchronized boolean waitToSetTrue(long msTimeout) throws InterruptedException {
		    boolean success = waitUntilFalse(msTimeout);
		    if (success) {
		      setValue(true);
		    }

		    return success;
		  }

		  public synchronized boolean waitToSetFalse(long msTimeout) throws InterruptedException {
		    boolean success = waitUntilTrue(msTimeout);
		    if (success) {
		      setValue(false);
		    }

		    return success;
		  }

		  public synchronized boolean isTrue() {
		    return value;
		  }

		  public synchronized boolean isFalse() {
		    return !value;
		  }

		  public synchronized boolean waitUntilTrue(long msTimeout) throws InterruptedException {
		    return waitUntilStateIs(true, msTimeout);
		  }

		  public synchronized boolean waitUntilFalse(long msTimeout) throws InterruptedException {
		    return waitUntilStateIs(false, msTimeout);
		  }

		  public synchronized boolean waitUntilStateIs(boolean state, long msTimeout) throws InterruptedException {
		    if (msTimeout == 0L) {
		      while (value != state) {
		        wait();
		      }
		      return true;
		    }

		    // wait for the specified amount of time
		    long endTime = System.currentTimeMillis() + msTimeout;
		    long msRemaining = msTimeout;

		    while ((value != state) && (msRemaining > 0L)) {
		      wait(msRemaining);
		      msRemaining = endTime - System.currentTimeMillis();
		    }
		    
		    return (value == state);
		  }
		}
	
	private BooleanLock readyLock;

	public BooleanLockTest(BooleanLock lock){
		readyLock = lock;
		Runnable r = new Runnable(){
			@Override
			public void run(){
				try{
					runWork();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		
		new Thread(r).start();
		
	}
	
	private void runWork() throws InterruptedException {
		System.out.println("wait until true");
		readyLock.waitToSetTrue(0);
		System.out.println("now the value is true");
	}
	
	public static void main(String[] args) {
		try{
			BooleanLock lock = new BooleanLock(true);
			 new BooleanLockTest(lock);
			System.out.println("about to sleep for 3 seconds");
			Thread.sleep(3000);
			lock.setValue(false);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
