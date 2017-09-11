package com.entrepidea.java.io.tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;
import java.io.IOException;


public class CopyFileTest{
	public static void main(String[] args){
		try{
			FileInputStream fin = new FileInputStream(args[0]);
			FileOutputStream fout = new FileOutputStream(args[1]);
			FileChannel fcin = fin.getChannel();
			FileChannel fcout = fout.getChannel();
			MappedByteBuffer mbb = fcin.map(FileChannel.MapMode.READ_ONLY, 0, fcin.size());
			fcout.write(mbb);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
			System.exit(1);
		}
		
	}
}