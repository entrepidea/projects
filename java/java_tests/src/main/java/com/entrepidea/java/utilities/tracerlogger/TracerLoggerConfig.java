package com.entrepidea.java.utilities.tracerlogger;

import org.apache.log4j.*;
import org.apache.log4j.xml.*;
import java.io.*;

public class TracerLoggerConfig 
{

	public TracerLoggerConfig() {
		init();
	}

	public void init() {
		// Load Log4J config file here.
    //reserved for now
		String fileName = "logcfg.xml";
		File f = new File(fileName);
		DOMConfigurator.configure(f.toString());
		DOMConfigurator.configureAndWatch(fileName, 10000);
	}
}