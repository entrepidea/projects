package com.entrepidea.java.misc;

class Stu{
	private String name;
	private int age;
	private double score;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	Stu(String n, int a, double s){
		name = n; age = a; score = s;
	}
	
	//copy constructor
	Stu(Stu s){
		name = s.name;
		age = s.age;
		score = s.score;
	}
}
public class CopyCntrTest {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stu s = new Stu("Scott", 34, 67.5);
		Stu[] stus = new Stu[10];
		for(int i=0;i<stus.length;i++){
			stus[i] = new Stu(s);
				System.out.println(stus[i] + ": name: "+stus[i].getName());
			}
		}		
}
