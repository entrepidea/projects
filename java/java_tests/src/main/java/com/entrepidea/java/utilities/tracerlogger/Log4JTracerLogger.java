package com.entrepidea.java.utilities.tracerlogger;

/*
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
*/
import org.apache.log4j.Category;

import com.entrepidea.java.utilities.tracerlogger.ITracerLogger;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;

public class Log4JTracerLogger implements ITracerLogger
{
	Category cat = null;
	File  logFile = new File("error.log");  
	public Log4JTracerLogger(String catname) {
		cat = Category.getInstance(catname);
	}

	public Category getCategory() {
		return this.cat;
	}

	public void debug(Object message) {
		//System.out.println("Log4JTracerLogger.debug() called: " + message);
		cat.debug(message);
	}

	public void debug(Object message, Throwable t) {
		cat.debug(message, t);
	}

	public void debug(Object message, Object source, Throwable t) {
		cat.debug("Log4JTracerLogger.debug() not implemented");
	}

	public void error(Object message) {
		
		cat.error(message);
	}

	public void error(Object message, Throwable t) {
		
		cat.error(message, t);
	}

	public void error(Object message, Object source, Throwable t) {
		cat.error("message not implementd");
	}

	public void info(Object message) {
		cat.info(message);
	}

	public void info(Object message, Throwable t) {
		cat.info(message, t);
	}

	public void info(Object message, Object source, Throwable t) {
		cat.info("method not implementd");
	}

	public void warn(Object message) {
		cat.warn(message);
	}

	public void warn(Object message, Throwable t) {
		cat.warn(message, t);
	}

	public void warn(Object message, Object source, Throwable t) {
		cat.warn(message, t);
	}

	public void fatal(Object message) {
		
		cat.fatal(message);
	}

	public void fatal(Object message, Throwable t) {
		
		cat.fatal(message, t);
	}

	public void fatal(Object message, Object source, Throwable t) {
		cat.fatal("method not implementd ");
	}

	//do we really need this method in this interface ???
	public void setLogLevel(int logLevel) {
		//to be implemented
	}
	
	public void readLog(){
		System.out.println("READY TO READ LOG FILE...");
	}
	public void writeLog(Exception e){
		System.out.println("READY TO WRITE LOG FILE...");
		try{
			//PrintWriter pw = new PrintWriter(logFile);
			StackTraceElement[] ste = e.getStackTrace();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(logFile));
			bw.write(new Date().toString());
			for(int i=0;i<ste.length;i++){
				//System.out.println(ste[i].getLineNumber()+":"+ste[i].toString());
				bw.write(ste[i].toString());
				bw.newLine();
				
			}
			bw.flush();
			bw.close();
		}
		catch(Exception e2){};
	}
}