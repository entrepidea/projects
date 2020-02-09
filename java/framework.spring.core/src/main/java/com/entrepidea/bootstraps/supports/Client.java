package com.entrepidea.spring.bootstraps.supports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class Client {
	@Autowired
	private Environment env;
	@Value("${bean.name}")
	private String beanName;
	@Autowired
	SimpleDriverDataSource dataSource;

	public String getDasourceName() {
		return dataSource.getUsername();
	}

	public Environment getEnv() {
		return env;
	}

	public String getName() {
		return beanName;
	}

}
