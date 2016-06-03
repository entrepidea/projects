package com.entrepidea.java.misc;

public class RatioTest{

	private static boolean single(int s[], int x){
		int temp;
			temp = x/100;
			if(s[temp]==0){
				s[temp]=1;
				temp = (x%100)/10;
				if(temp==0){return false;}				
				if(s[temp]==0){
					s[temp]=1;
					temp = x%10;
					if(temp==0){return false;}
					if(s[temp]==0){
						s[temp]=1;
						return true;
					}
					else{
						return false;
					}
				}
				else{
					return false;
				}
			}
			else{
				return false;
			}
	}
	
	public static void main(String[] args){
		for(int i=123;i<329;i++){
			int x = i; 
			int y = 2*i;
			int z = 3*i;
			int[] s = new int[10];

			if(single(s, x) &&	single(s, y) && single(s, z)){
				System.out.println(x+":"+y+":"+z);
			}
			else{
				continue;
			}	
		}
	}
}