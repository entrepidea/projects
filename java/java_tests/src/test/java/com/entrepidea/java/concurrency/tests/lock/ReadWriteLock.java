package com.entrepidea.java.concurrency.tests.lock;

public final class ReadWriteLock {
	private int readingReaders = 0; // number of the reading threads
	private int waitingWriters = 0; // the waiting writer threads 
	private int writingWriters = 0; // the writing threads
	private boolean preferWriter = true; // is writer preferred

	public synchronized void readLock() throws InterruptedException {
		while (writingWriters > 0 || (preferWriter && waitingWriters > 0)) {
			wait();
		}
		readingReaders++;
	}

	public synchronized void readUnlock() throws InterruptedException {
		readingReaders--;
		notifyAll();
	}

	public synchronized void writeLock() throws InterruptedException {
		waitingWriters++;
		try {
			while (readingReaders > 0 || writingWriters > 0) {
				wait();
			}
		} finally {
			waitingWriters--;
		}
		writingWriters++;
	}

	public synchronized void writeUnlock() throws InterruptedException {
		writingWriters--;
		notifyAll();
	}

}