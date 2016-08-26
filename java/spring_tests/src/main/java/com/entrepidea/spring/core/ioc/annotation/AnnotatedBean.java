package com.entrepidea.spring.core.ioc.annotation;

import org.springframework.stereotype.Component;

@Component
public class AnnotatedBean {

	public String getMessage(){
		return "I am an annotated bean.";
	}
}
