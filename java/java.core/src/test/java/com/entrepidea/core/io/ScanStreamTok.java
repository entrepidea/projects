package com.entrepidea.core.io;

import java.io.FileReader;
import java.io.StreamTokenizer;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ScanStreamTok {
	
	private StreamTokenizer tf = null;
	
	public static void main(String[] argv)throws IOException{
		if(argv.length == 0){
			new ScanStreamTok(new InputStreamReader(System.in)).proces();
		}
		else{
			for(int i=0;i<argv.length;i++){
				new ScanStreamTok(argv[i]).proces();
			}
		}
	}
	
	public ScanStreamTok(String fileName) throws IOException {
		tf = new StreamTokenizer(new FileReader(fileName));
	}
	
	public ScanStreamTok(Reader r) throws IOException {
		tf = new StreamTokenizer(r);
	}
	
	public void proces()throws IOException {
		int i;
		while((i=tf.nextToken())!= StreamTokenizer.TT_EOF){
			switch(i){
			case StreamTokenizer.TT_EOF:
       			System.out.println("End of file");
        		break;
      		case StreamTokenizer.TT_EOL:
        		System.out.println("End of line");
        		break;
      		case StreamTokenizer.TT_NUMBER:
        		System.out.println("Number " + tf.nval);
        		break;
      		case StreamTokenizer.TT_WORD:
        		System.out.println("Word, length " + tf.sval.length() + "->" + tf.sval);
        		break;
      		default:
        		System.out.println("What is it? i = " + i);
			}
		}
	}
}