package com.entrepidea.java.concurrency.tests.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	class GrabQuotes extends TimerTask {
		private String symbol = "SUNW";
		
		GrabQuotes(String s){
			symbol = s;
		}
		public void download() {
			StringBuffer urlStr = new StringBuffer();
			urlStr.append("http://table.finance.yahoo.com/table.csv").append("?s=");
			urlStr.append(symbol);			
			urlStr.append("&d=6&e=23&f=2006&g=d&a=2&b=11&c=2005&ignore=.csv");
			System.out.println(urlStr.toString());
			try {
				URL url = new URL(urlStr.toString());
				if (url != null) {
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setAllowUserInteraction(true);
					conn.setRequestMethod("GET");
					conn.setInstanceFollowRedirects(true);

					// setInputStream(conn.getInputStream());
					conn.connect();
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String line=null;
			        while ((line=in.readLine()) != null){
			          System.out.println(line);
			        }
			        
			        in.close();
			        conn.disconnect();
				}
			} catch (Exception e) {
			}

		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			download();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Timer timer =  new Timer();
		TimerTask task = new TimerTest().new GrabQuotes("SUNW");
		long delay = 10000;
		long period = 10000;		
		timer.schedule(task, delay, period);
		
	}

}
