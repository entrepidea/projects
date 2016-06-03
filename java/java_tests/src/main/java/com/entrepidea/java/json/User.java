package com.entrepidea.java.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5500436089236599772L;
	
	private int age = 29;
	private String name = "mkyong";
	private List<String> messages = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3018510853853630931L;
		
		{
			add("msg 1");
			add("msg 2");
			add("msg 3");
		}
	};

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}



	//getter and setter methods
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name = n;
	}
	

	@Override
	public String toString() {
		return "User [age=" + age + ", name=" + name + ", " +
				"messages=" + messages + "]";
	}
}
