package com.entrepidea.java.utilities;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FileTimeStampTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File f = new File("c:\\temp\\KnowledgeBase.itw");
		System.out.println(f.lastModified());
		Timestamp toTSDate = new Timestamp (f.lastModified() );
		System.out.println(toTSDate);
	}

}
