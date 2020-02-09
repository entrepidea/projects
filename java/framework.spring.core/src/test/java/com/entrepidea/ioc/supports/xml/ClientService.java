package com.entrepidea.ioc.supports.xml;

public class ClientService {
	private static ClientService clientService = new ClientService();

	private String email;

	private ClientService() {
	}

	public static ClientService createInstance() {
		return clientService;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public String getServiceType() {
		return "Client";
	}
}
