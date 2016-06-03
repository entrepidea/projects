 package com.entrepidea.java.basic;

import java.awt.Font;
import java.lang.Character.UnicodeBlock;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UnicodeBlockTest {

	public static UnicodeBlock[] characterRanges(Font font){
		Set<UnicodeBlock> temp = new HashSet<UnicodeBlock>();
		int count = Math.max(0, font.getNumGlyphs()); // don't trust source
		for (int index = 0; index < count; index ++){
			char ch = (char)index;
			Character.UnicodeBlock block =  Character.UnicodeBlock.of(ch);
			temp.add(block);
		}
		Iterator<UnicodeBlock> iter = temp.iterator();
		UnicodeBlock[] ranges = new UnicodeBlock[temp.size()];
		int i=0;
		while(iter!=null && iter.hasNext()){
			ranges[i++] = iter.next();
		}
		
		return ranges;
	}
	
	public static void print(Object[] objects){
		System.out.println("how many blocks: "+objects.length);
		for(Object obj: objects){
			System.out.println(obj);
		}
	}
	
	public static void main(String[] args) {
		char ch = (char)1456;//'\u05B0';
		Character.UnicodeBlock block = Character.UnicodeBlock.of(ch);
		System.out.println(block);
		
		Font dftFont = new Font("Arial Unicode MS", Font.PLAIN,  30);
		print(characterRanges(dftFont));
	
	}

}
