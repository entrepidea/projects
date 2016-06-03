package com.entrepidea.java.io;

import java.io.*;
import java.util.*;

import com.entrepidea.java.utilities.tracerlogger.ITracerLogger;
import com.entrepidea.java.utilities.tracerlogger.TracerLoggerFactory;


public class DelConcident {
	protected ITracerLogger logger = TracerLoggerFactory
            .getPackageTracerLogger(this);
        
    public DelConcident(){
		String in1 = System.getProperty("NUMBERS");
		String in2 = System.getProperty("FILE");
		Vector<String> v1 = new Vector<String>();
		Vector<String> v2 = new Vector<String>();
		//Vector<String> v3 = new Vector<String>();
		
		LineNumberReader lineCounter1 = null;
		LineNumberReader lineCounter2 = null;
		
		try{
		 	lineCounter1 = new LineNumberReader(new FileReader(in1));
		 	lineCounter2 = new LineNumberReader(new FileReader(in2));
		 	
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}
		
		String nextLine = null;
		
		try {
     		while ((nextLine = lineCounter1.readLine())!=null) {
        		v1.add(nextLine);
      		}//numbers in the buffer
      		while ((nextLine = lineCounter2.readLine())!=null){
      			v2.add(nextLine);
      		}//content in the buffer      		
	  	} catch (Exception done) {
      		done.printStackTrace();
    	}
    	
    	Iterator numIter = v1.iterator();
    	
    	while(numIter.hasNext()){
    		String num = (String)numIter.next();
    		Iterator fileIter = v2.iterator();
    		
    		while(fileIter.hasNext()){
    			String line = (String)fileIter.next();
    			if(line.startsWith(num)){
    				v2.remove(line);
    			}
    		}
    	}
    	//v2.remove(v3);
    	Iterator fileIter = v2.iterator();
    	while(fileIter!=null && fileIter.hasNext()){
    		System.out.println(fileIter.next());
    	}    	
    }
            
	public static void main(String[] argv){
		new DelConcident();
	}
}