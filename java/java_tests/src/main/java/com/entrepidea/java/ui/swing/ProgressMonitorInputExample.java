package com.entrepidea.java.ui.swing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;
import javax.swing.ProgressMonitorInputStream;

public class ProgressMonitorInputExample {

	public ProgressMonitorInputExample(String[] filenames) {
		ProgressMonitorInputStream pmis;
		ProgressMonitor monitor=null;
		
		for (String filename : filenames) {
			try {

				pmis = new ProgressMonitorInputStream(null, "Loading "
						+ filename, new FileInputStream(filename));
				monitor = pmis.getProgressMonitor();
				while (pmis.available() > 0) {
					byte[] data = new byte[38];
					pmis.read(data);
					System.out.write(data);
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Unable to find file: "
						+ filename, "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				;
			}
		}
	}

	public static void main(String args[]) {
		String[] fileNames = new String[]{"J2EE", "XML"};
		new ProgressMonitorInputExample(fileNames);
	}
}