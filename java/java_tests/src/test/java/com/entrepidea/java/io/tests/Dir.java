package com.entrepidea.java.io.tests;

import java.io.File;
import java.io.IOException;

public class Dir {
	private static String DIR = "/home/john/projects/java_workspace/java_tests/src/main/resources/data";
	
	public static void main(String[] argv)throws IOException {
		showDir(1, new File(DIR));
	}
	public static void showDir(int indent, File dir) throws IOException {
		for(int i=0;i<indent;i++){
			System.out.print('-');
		}
		System.out.println(dir.getName());
		if(dir.isDirectory()){
			File[] files = dir.listFiles();
			for(int i=0;i<files.length;i++){
				showDir(indent+4, files[i]);
			}
		}
	}
}