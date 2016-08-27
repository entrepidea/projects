package com.entrepidea.spring.core.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//<!--  test how to use depend-on property -->
public class SimplestBean {
	Logger log = LoggerFactory.getLogger(SimplestBean.class);
	
	public SimplestBean(){
		log.info("Simplest bean's constructor.");
	}
	
	private String name;
	public void setName(String s){
		name = s;
	}
	
	public String getName(){
		return name;
	}
	public String myName(){ return "Lilly."; }
}
