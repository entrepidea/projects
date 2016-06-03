package com.entrepidea.java.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "com.entrepidea.java.ws.HelloWorld")
public class HelloWorldImpl implements HelloWorld{

	@Override
	@WebMethod
	public String getHelloAsString(String name) {
		// TODO Auto-generated method stub
		return "hello from JAX-WS " + name;
	}
}
