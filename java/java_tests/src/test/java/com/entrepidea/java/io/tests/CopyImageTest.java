package com.entrepidea.java.io.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyImageTest {

	static class CopyTask implements Runnable{

		@Override
		public void run() {
			try {
				long start = System.nanoTime();
				FileInputStream in = new FileInputStream("C:\\Users\\john\\Downloads\\ojdbc14.jar");
				FileOutputStream out = new FileOutputStream("C:\\Users\\john\\Downloads\\temp.jar");
				int c;
				while((c=in.read()) != -1){
					out.write(c);
				}
				in.close();
				out.close();
				
				long end = System.nanoTime();
				System.out.println("byte-by-byte copying: "+(end-start));
						
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class CopyTask2 implements Runnable{

		@Override
		public void run() {
			try {
				long start = System.nanoTime();
				FileInputStream in = new FileInputStream("C:\\Users\\john\\Downloads\\ojdbc14.jar");
				FileOutputStream out = new FileOutputStream("C:\\Users\\john\\Downloads\\temp2.jar");
				FileChannel inChannel = in.getChannel();
				FileChannel outChannel = out.getChannel();
				inChannel.transferTo(0, inChannel.size(), outChannel);
				in.close();out.close();
				long end = System.nanoTime();
				System.out.println("bulk copying: "+(end-start));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		
		//int nCore = Runtime.getRuntime().availableProcessors();
		ExecutorService exec = Executors.newFixedThreadPool(2);
		exec.submit(new CopyTask());
		exec.submit(new CopyTask2());
		
		exec.shutdown();
	}

}
