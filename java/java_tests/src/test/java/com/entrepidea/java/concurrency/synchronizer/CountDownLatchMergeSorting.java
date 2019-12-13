package com.entrepidea.java.concurrency.synchronizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class CountDownLatchMergeSorting {

	private static int ARRAY_LENGTH = 80;
	
	private int[] createArray(){
		int[] arr = new int[ARRAY_LENGTH];
		Random rand = new Random();
		for(int i=0;i<ARRAY_LENGTH;i++){
			int val = rand.nextInt(1000);
			arr[i] = val;
		}
		return arr;
	}
	
	private int[][] breakArray(int[] arr, int n){
		int[][] ret = new int[n][];
		int step = arr.length / n;
		for(int i=0;i<n;i++){
			ret[i] = new int[step]; 
			System.arraycopy(arr, i*step, ret[i], 0, step);
		}
		return ret;
	}
	
	
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//find out available cpu cores
		int n = Runtime.getRuntime().availableProcessors();
		//break 10^6 long array into n segments
		CountDownLatchMergeSorting cds = new CountDownLatchMergeSorting();
		int[][] ret = cds.breakArray(cds.createArray(), n);
		cds.print(ret);
		//prepare startSignal and completeSignal - a count down latch object
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch done = new CountDownLatch(n);
		//prepare n workers
		ExecutorService exec = Executors.newFixedThreadPool(n);
		List<Future<int[]>> results = new ArrayList<Future<int[]>>();
		for(int i=0;i<n;i++){
			SortingTask t = new SortingTask(ret[i],start,done);
			Future<int[]> f = exec.submit(t);
			results.add(f);
		}
		exec.shutdown();
		//kick off the workers
		start.countDown();
		//set a barrier
		System.out.println("Set the barrier...");
		done.await();//wait until all threads are done
		//print all arrays
		System.out.println("Print the sorted result...");
		int[][] temp = new int[results.size()][];
		int count=0;
		for(int i=0;i<results.size();i++){
			Future<int[]> f=  results.get(i);
			int[] a = f.get();
			temp[count++] = a;
		}
		
		cds.print(cds.mergeAll(temp));
		
	}
	
	private int[] merge(int[] a, int[] b){
		int[] ret = new int[a.length+b.length];
		int i=0,j=0,k=0;
		while(i<a.length && j<b.length){
			if(a[i]<=b[j]){
				ret[k++] = a[i++];
			}
			else{
				ret[k++] = b[j++];
			}
		}
		if(i<a.length){ //that means b finished first
			while(i<a.length){
				ret[k++] = a[i++];
			}
		}
		else{
			while(j<b.length){
				ret[k++] = b[j++];
			}
		}
		return ret;
	}
	
	private int[] mergeAll(int[][] m){
		int[] ret=m[0];
		for(int i=1;i<m.length;i++){
			ret = merge(ret, m[i]);
		}
		return ret;
	}
	private void print(int[] a){
		for(int j=0;j<a.length;j++){
			System.out.print(a[j]+"\t");
		}
		System.out.println();
	}
	
	private void print(int[][] a){
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a[0].length;j++){
				System.out.print(a[i][j]+"\t");
			}
			System.out.println();
		}
	}
	static class SortingTask implements Callable<int[]> {
		
		private int[] arr;
		private CountDownLatch s,d;
		public SortingTask(final int[] arr, CountDownLatch startC, CountDownLatch doneC){
			this.arr = arr;
			s = startC;
			d = doneC;
		}
		
		public int[] quickSort(int[] arr){
			int[] ret = null;
			if(arr.length<=1){
				ret = arr;
				return ret;
			}
			else{
				int[] a = new int[arr.length];
				int[] b = new int[arr.length];
				int p = arr[arr.length/2];
				int k1=0,k2=0;
				for(int i=0;i<arr.length;i++){
					if(i==arr.length/2) continue;
					if(arr[i]<=p){
						a[k1++] = arr[i];
					}
					else{
						b[k2++] = arr[i];
					}
				}
				int[] a1=new int[k1];
				int[] b1=new int[k2];
				
				System.arraycopy(a, 0, a1, 0, k1);
				System.arraycopy(b, 0, b1, 0, k2);
				
				ret = cat(quickSort(a1),quickSort(b1),p);
				
				return ret;
			}
		}
		
		
		
		private int[] cat(int[] a, int[] b, int p){
			int[] ret = new int[a.length+b.length+1];
			int k=0;
			for(int i=0;i<a.length;i++){
				ret[k++] = a[i]; 
			}
			ret[k++] = p;
			for(int i=0;i<b.length;i++){
				ret[k++] = b[i]; 
			}
			return ret;
		}
		
		@Override
		public int[] call() {
			//hold for the start signal
			int[] ret = null;
			try {
				s.await();
				ret = quickSort(arr);
				//and done
				d.countDown();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return ret;
		}
		
	}

}
