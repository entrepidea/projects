package com.entrepidea.java.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PreferenceExample {

  public static void main(String args[]) {
    Preferences prefsRoot = Preferences.userRoot();
    Preferences myPrefs = prefsRoot.node("PreferenceExample");
    
    //demo how to populate some proerties and create a xml file
    try {
    	myPrefs.put("fruit", "apple");
    	myPrefs.put("Items", "oil");
    	FileOutputStream fos = new FileOutputStream("conf/preference_test.xml");

    	myPrefs.exportSubtree(fos);    	
        fos.close();
        
      } catch (IOException ioe) {
        System.out.println("IOException in exportToFile\n" + ioe);
        ioe.printStackTrace();
    } catch (BackingStoreException bse) {
      System.out.println("Problem with accessing the backing store\n"
          + bse);
      bse.printStackTrace();
    } 
    
    //demo how to read a xml file created earlier
    try{
    	Preferences.importPreferences(new FileInputStream(new File("conf/preference_test.xml")));
    	System.out.println(myPrefs.get("fruit",null));
    }
    catch(Exception e){
    	e.printStackTrace();
    }
  }
}

           