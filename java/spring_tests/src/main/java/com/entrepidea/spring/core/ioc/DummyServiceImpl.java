package com.entrepidea.spring.core.ioc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Use annotated-config file - totally XML-free configuration, supported by Spring 3.0
public class DummyServiceImpl implements DummyService {

	Logger log = LoggerFactory.getLogger(DummyServiceImpl.class);
	@Override
	public void doStuff() {
		log.info("I am a DUMMY!!!!!!!!!!");;
	}

}
