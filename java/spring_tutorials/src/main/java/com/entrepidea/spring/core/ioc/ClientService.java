package com.entrepidea.spring.core.ioc;

public class ClientService {
	private static ClientService clientService = new ClientService();

	private ClientService() {
	}

	public static ClientService createInstance() {
		return clientService;
	}
	
	public String getServiceType() {
		return "Client";
	}
}
