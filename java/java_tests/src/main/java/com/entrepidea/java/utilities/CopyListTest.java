package com.entrepidea.java.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopyListTest {

	
	
	public class DTO{
		private String var1;
		private int var2;
		
		public DTO(String v1, int v2){
			var1 = v1;
			var2 = v2;
		}
		
		public String getVar1() {
			return var1;
		}
		public void setVar1(String var1) {
			this.var1 = var1;
		}
		public int getVar2() {
			return var2;
		}
		public void setVar2(int var2) {
			this.var2 = var2;
		}
	}
	
	private List<DTO> list;
	CopyListTest(){
		ArrayList<DTO> l = new ArrayList<DTO>();
		l.add(new DTO("test1", 1));
		l.add(new DTO("test2",2));
		
		DTO[] array = new DTO[l.size()];		
		System.arraycopy(l.toArray(), 0, array, 0, l.size());
		System.out.println(l);
		
		list = Arrays.asList(array);
		
		System.out.println(list);
	}
	
	public void print(){
		List<DTO> l = list;
		for(int i=0;i<l.size();i++){
			DTO d = l.get(i);
			System.out.println(d.getVar1()+" "+d.getVar2());
		}
	}
	
	public static void main(String[] argc){
		new CopyListTest().print();
	}
}
