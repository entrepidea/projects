package com.entrepidea.java.utilities;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLEncoderTest {

	public static void main(String[] args){
		try{
		String testStr = "<html>test, 1<2 <b>\\<cool!</b> </html>";
		String result = URLEncoder.encode(testStr,"UTF-8");
		System.out.println(result);
		result = URLDecoder.decode(result,"UTF-8");
		System.out.println(result);
		}
		catch(Exception e){}
	}
}
