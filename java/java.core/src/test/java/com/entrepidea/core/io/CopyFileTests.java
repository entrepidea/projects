package com.entrepidea.core.io;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.MappedByteBuffer;

/**
 * @Desc:
 * NIO package in Java is used for data storage and network transfer.
 * N stand for Non-blocking, implying techniques used in this APIs.
 * For now I am still learning about it, start from the sources below, update
 * the corresponding part of my wiki (http://entrepidea.com/wiki/index.php/NIO) while I am at it.
 *
 * @Source:
 * https://www.zhihu.com/question/29005375/answer/667616386?utm_source=com.ideashower.readitlater.pro&utm_medium=social&utm_oi=809364293245075456
 *
 * @Date: 01/20/2020
 *
 * */
public class CopyFileTests {

	//作者：Java3y
	//链接：https://www.zhihu.com/question/29005375/answer/667616386
	//来源：知乎
	//著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
	private long transferFile(File source, File des) throws IOException {
		long startTime = System.currentTimeMillis();

		if (!des.exists())
			des.createNewFile();

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));

		//将数据源读到的内容写入目的地--使用数组
		byte[] bytes = new byte[1024 * 1024];
		int len;
		while ((len = bis.read(bytes)) != -1) {
			bos.write(bytes, 0, len);
		}

		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	private long transferFileWithNIO(File source, File des) throws IOException {
		long startTime = System.currentTimeMillis();

		if (!des.exists())
			des.createNewFile();

		RandomAccessFile read = new RandomAccessFile(source, "r");
		RandomAccessFile write = new RandomAccessFile(des, "rw");

		FileChannel readChannel = read.getChannel();
		FileChannel writeChannel = write.getChannel();


		ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 1024);//1M缓冲区

		while (readChannel.read(byteBuffer) > 0) {
			byteBuffer.flip();
			writeChannel.write(byteBuffer);
			byteBuffer.clear();
		}

		writeChannel.close();
		readChannel.close();
		long endTime = System.currentTimeMillis();
		return endTime - startTime;
	}

	@Test
	public void test(){
		File source = new File("/run/user/1000/gvfs/afp-volume:host=WDMyCloudEX2.local,volume=jon/videos/world_war_z/World.War.Z.UNRATED.CUT.2013.BluRay.720p.x264.AC3-WOFEI.mkv");
		File dest = new File("/home/aaron/Downloads/world_war_Z.mkv");
		try {
			long dur = transferFile(source, dest);
			System.out.println(dur/(1000*60)+" minutes");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testNIO(){
		File source = new File("/run/user/1000/gvfs/afp-volume:host=WDMyCloudEX2.local,volume=jon/videos/world_war_z/World.War.Z.UNRATED.CUT.2013.BluRay.720p.x264.AC3-WOFEI.mkv");
		File dest = new File("/home/aaron/Downloads/world_war_Z_2.mkv");
		try {
			long dur = transferFileWithNIO(source, dest);
			System.out.println(dur/(1000*60)+" minutes");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}