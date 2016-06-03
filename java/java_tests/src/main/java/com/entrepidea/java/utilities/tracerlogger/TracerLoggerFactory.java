package com.entrepidea.java.utilities.tracerlogger;

import java.util.HashMap;


public class TracerLoggerFactory 
{
    private static HashMap<String,ITracerLogger> loggers = new HashMap<String,ITracerLogger>();
    private static TracerLoggerConfig logConfig = null;

	public static ITracerLogger getPackageTracerLogger(Object o) {
		String packageName = o.getClass().getName();
		return getPackageTracerLogger(packageName);
	}

	public static ITracerLogger getPackageTracerLogger(Class<?> c) {
		String packageName = c.getName();
		return getPackageTracerLogger(packageName);
	}

    public static ITracerLogger getPackageTracerLogger(String packageName)
    {
	if(logConfig == null)
	    logConfig = new TracerLoggerConfig();

		ITracerLogger logger = (ITracerLogger) loggers.get(packageName);
		if(logger == null) {
	    	System.out.println("LoggerFactory.getPackageTracerLogger()"
			       + " creating logger for " + packageName);
	    	logger = new Log4JTracerLogger(packageName);
	    	loggers.put(packageName , logger);
		}
		return logger;
    }
    
    public static void main(String[] args){
    	ITracerLogger logger = TracerLoggerFactory.getPackageTracerLogger(new TracerLoggerFactory());
    	logger.info("testing...");
    	logger.error("error message");
    	logger.debug("debug info");
    	
    }
}