package com.entrepidea.java.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PlanetPrefs {
	  public static void main(String args[]) {
	    String names[] = { "Mercury", "Venus", "Earth", "Mars", "Jupiter",
	        "Saturn", "Uranus", "Neptune", "Pluto" };
	    int moons[] = { 0, 0, 1, 2, 16, 18, 21, 8, 1 };

	    Preferences prefs = Preferences.userRoot()
	        .node("/MasteringJava/Chap17");

	    for (int i = 0, n = names.length; i < n; i++) {
	      prefs.putInt(names[i], moons[i]);
	    }

	    try {
	      String keys[] = prefs.keys();
	      for (int i = 0, n = keys.length; i < n; i++) {
	        System.out.println(keys[i] + ": " + prefs.getInt(keys[i], 0));
	      }
	      FileOutputStream os = new FileOutputStream(new File("test.xml"));
	      prefs.exportSubtree(os);
	      
	    } 
	    catch(FileNotFoundException fe){
	    	fe.printStackTrace();
	    }
	    catch (BackingStoreException e) {
	      System.err.println("Unable to read backing store: " + e);
	    }
	    catch(IOException ioe){
	    	ioe.printStackTrace();
	    }
	  }
	}