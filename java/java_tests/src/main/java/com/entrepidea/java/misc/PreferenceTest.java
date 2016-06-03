package com.entrepidea.java.misc;

import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class PreferenceTest {

	private final static Logger logger = Logger.getLogger("fitch.j2ee.core.util");
	
	PreferenceTest() {
		try {
      		// looking up application preferences file
      		String prefFile = System.getProperty("prefs.file");
      		Preferences prefs;
      		
      		try {
      			System.out.println("pref File = "+prefFile);
        		FileInputStream is = new FileInputStream(prefFile);
        		Preferences.importPreferences(is);
        		prefs = Preferences.userNodeForPackage(this.getClass());
      		}
      		catch (Exception _ex) {
        		throw new Exception ("Unable to load application configuration file", _ex);
      		}
      		// initializes logging properties from the logging property file
      		String logConfigFile = prefs.get("log.config.file",null);
      		System.out.println("logConfigFile = "+logConfigFile );
      		
      		FileInputStream logIs = new FileInputStream(logConfigFile);
      		LogManager logMgr = LogManager.getLogManager();
		    logMgr.readConfiguration(logIs);
      		logger.log(Level.INFO,"Application configurations loaded");
    	}
    	catch (Exception _ex) {
      		logger.log(Level.SEVERE,"Failed to load application configurations",_ex);
    	}		
	}
	
	public static void main(String[] args){
		new PreferenceTest();
	}
}


