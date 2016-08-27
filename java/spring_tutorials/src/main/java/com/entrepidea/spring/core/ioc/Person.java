package com.entrepidea.spring.core.ioc;


//<!--  example of using p-namespace. It needs to be declared but not need to specify the xsd; -->
//<!-- look at the special ref: spouse-ref, it's the property + ref -->
public class Person {
	private String name;
	private Person spouse;
	public Person getSpouse() {
		return spouse;
	}
	public void setSpouse(Person spouse) {
		this.spouse = spouse;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
