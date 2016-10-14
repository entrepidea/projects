package com.entrepidea.spring.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class RestClient {

	private static final Logger log = LoggerFactory.getLogger(RestClient.class);
	public static void main(String[] args) {
		RestTemplate rt = new RestTemplate();
		IdiomModel idiomModel = rt.getForObject("http://entrepidea.com:8080/ws/Vivid.Necessity/allIdioms", IdiomModel.class);
		List<Idiom> idioms = idiomModel.getIdioms();
		int count=0;
		for(Idiom idiom : idioms){
			log.info("{})===> {}",(count++),idiom.getDefinition());
		}
	}
}
