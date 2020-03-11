package com.entrepidea.core.io;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class LineReader{
	public static void main(String[] argv) throws IOException{
		BufferedReader br = null;
		if(argv.length!=0){
			br = new BufferedReader(new FileReader(argv[0]));
			String line = null;
			while((line = br.readLine())!=null){
				System.out.println(line);
			}
			br.close();
		}
	}
}

