package com.entrepidea.java.io.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class DeleteFileWithExceptionCapture {
	public static void main(String[] argv) {
	    	//delete("C:\\Users\\John\\projects\\java_workspace\\DVB_FM\\My_Group\\test_dir\\teststt\\arialbi.TTF");
	    	File file = new File("C:\\Users\\John\\projects\\java_workspace\\DVB_FM\\My_Group\\test_dir\\teststt\\ANTQUAB.TTF");
	    	Path path = file.toPath();
	    	try {
	    		
	    	    Files.delete(path);
	    	} catch (NoSuchFileException x) {
	    	    System.err.format("%s: no such file or directory%n", path);
	    	} catch (DirectoryNotEmptyException x) {
	    	    System.err.format("%s not empty%n", path);
	    	} catch (IOException x) {
	    	    //File permission problems are caught here.
	    	    System.err.println(x);
	    	}

	  }
}
