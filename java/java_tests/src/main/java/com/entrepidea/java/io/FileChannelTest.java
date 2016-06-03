package com.entrepidea.java.io;

import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;
import java.io.IOException;

public class FileChannelTest
{
	public static void main(String[] args){
		try{
			FileInputStream fis = new FileInputStream(args[0]);
			FileChannel fc = fis.getChannel();
			long fSize = fc.size();
			MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY,0,fSize);
			for(long i=0;i<fSize;i++){
				System.out.println((char)mbb.get());
			}
			fc.close();
			fis.close();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
}