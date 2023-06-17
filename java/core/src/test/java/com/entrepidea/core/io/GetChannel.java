package com.entrepidea.core.io;

import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.ByteBuffer;
import java.io.IOException;

public class GetChannel{
	private static int BUFF_SIZE = 1024;
	public static void main(String[] args) throws IOException {
		String fName = args[0];
		FileChannel fc = new FileOutputStream(fName).getChannel();
		fc.write(ByteBuffer.wrap("some text".getBytes()));
		fc.close();
	}
}
