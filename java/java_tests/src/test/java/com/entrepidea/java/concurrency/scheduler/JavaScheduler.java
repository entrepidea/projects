package com.entrepidea.java.concurrency.scheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JavaScheduler {
	
	class StreamGobbler extends Thread
	{
	    InputStream is;
	    String type;
	    
	    StreamGobbler(InputStream is, String type)
	    {
	        this.is = is;
	        this.type = type;
	    }
	    
	    public void run()
	    {
	        try
	        {
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String line=null;
	            while ( (line = br.readLine()) != null)
	                System.out.println(type + ">" + line);    
	            } catch (IOException ioe)
	              {
	                ioe.printStackTrace();  
	              }
	    }
	}
	
	class Job implements Runnable {
		 
	    private String[] command = { "sleep.exe","5" };
	    private int jobId;
	    private long lastRunTime = -1;
	    Runtime r = Runtime.getRuntime();
	    boolean isRunning = false;
	 
	    public Job(int id) {
	        jobId = id;
	    }
	 
	    public void run() {
	        System.out.println("runJob started" + jobId);
	        isRunning = true;
	        lastRunTime = System.currentTimeMillis();
	 
	        try {
	            Process p = r.exec(command);
	            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
	            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
	            errorGobbler.start();
	            outputGobbler.start();
	            int exitVal = p.waitFor();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        } catch (InterruptedException ex) {
	            ex.printStackTrace();
	        }
	        System.out.println("runJob returned" + jobId);
	        isRunning = false;
	 
	    }
	 
	    public long getLastRunTime() {
	        return lastRunTime;
	    }
	 
	    public boolean isRunning() {
	        return isRunning;
	    }
	}
	
	private ArrayList<Job> allJobs = new ArrayList();
	public static JavaScheduler instance;

	public static void main(String[] args) {
		instance = new JavaScheduler();
		instance.loop();
	}

	public JavaScheduler() {
		// Create lots of jobs
		for (int i = 0; i < 100; i++) {
			Job j = new Job(i);
			allJobs.add(j);
		}
		System.out.println("Created 100 jobs");
	}

	private boolean loop() {
		// Main loop
		int i = 0;
		int x = 0;
		while (true) {
			x++;
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
			}
			i++;
			if (i >= 10) {
				Runtime.getRuntime().gc();
				i = 0;
			}
			for (Job j : allJobs) {
				long now = System.currentTimeMillis();
				// Jobs run every 15 secs
				if (now >= j.getLastRunTime() + 15000 && !j.isRunning()) {
					new Thread(j).start();
				}
			}
		}
	}

}
