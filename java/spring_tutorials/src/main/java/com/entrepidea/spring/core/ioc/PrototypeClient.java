package com.entrepidea.spring.core.ioc;

//<!--  example of bean with prototype scope -->
public class PrototypeClient {
	private PrototypeBean bean1;
	private PrototypeBean bean2;
	private PrototypeBean bean3;
	
	public PrototypeBean getBean1() {
		return bean1;
	}
	public void setBean1(PrototypeBean bean1) {
		this.bean1 = bean1;
	}
	public PrototypeBean getBean2() {
		return bean2;
	}
	public void setBean2(PrototypeBean bean2) {
		this.bean2 = bean2;
	}
	public PrototypeBean getBean3() {
		return bean3;
	}
	public void setBean3(PrototypeBean bean3) {
		this.bean3 = bean3;
	}
}
