package com.entrepidea.core.concurrency.synchronizer;

public class SharedMemory {
	String[] B;
	String[][] M;
	boolean isBPresent[], isMPresent; // 1-->B and 2-->M

	public SharedMemory(int n) {
		B = new String[n];
		M = new String[7][n / 2];
		isBPresent = new boolean[7];
		isMPresent = false;
		for (int i = 0; i < 7; i++)
			isBPresent[i] = false;

	}

	public synchronized void putMi(String Mi[], int i) {
		M[i - 1] = Mi;
		isBPresent[i - 1] = false;
		notifyAll();
	}

	public synchronized void putB(String[] b) {
		B = b;
		for (int i = 0; i < 7; i++)
			isBPresent[i] = true;
		notifyAll();
	}

	public synchronized void waitForMs() {
		int i = 0;
		// BorM = 2;
		System.out.println("Coordinating agent : Waiting for Ms");
		while (i != 7) {
			try {
				wait();
				i++;
			} catch (InterruptedException ie) {
				// i++;
				System.out.println("Coordinating Agent interrupted : " + i);
			}
		}

		i = 0;

		// BorM = 1;
	}

	public synchronized void waitForB(int i) {
		while (!isBPresent[i - 1]) {
			try {
				wait();
			} catch (InterruptedException ie) {

			}

		}
	}

}
