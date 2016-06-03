package com.entrepidea.java.io;

import java.io.StreamTokenizer;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class WordCounter {
	
	public static void main(String[] args) throws IOException {
		StreamTokenizer st = new StreamTokenizer(new BufferedReader(new FileReader(args[0])));
		int i;
		HashMap hm = new HashMap();

		while((i=st.nextToken())!=StreamTokenizer.TT_EOF){
			if(i == StreamTokenizer.TT_WORD){
				//System.out.println(st.sval);
				if(hm.get(st.sval)!=null){
					Integer count = (Integer)hm.get(st.sval);
					int c = count.intValue()+1;
					hm.put(st.sval,new Integer(c));
				}
				else{
					hm.put(st.sval,new Integer(1));
				}
			}
		}//end while ~
		
		Set s = hm.entrySet();
		for(Iterator iter = s.iterator();iter.hasNext();){
			Map.Entry me = (Map.Entry)iter.next();
			System.out.println(me.getKey()+":"+me.getValue());
		}		
	}	
}