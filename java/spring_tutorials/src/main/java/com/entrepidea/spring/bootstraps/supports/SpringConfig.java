package com.entrepidea.spring.bootstraps.supports;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ImportResource("classpath:config/META-INF/spring/spring-bootstrap.xml")
@PropertySource("classpath:/config/application.properties")
public class SpringConfig {

	@Bean
	Client newClient() {
		return new Client();
	}

	// this is required if you want to evaluate the @Value annotation in Client
	// class.
	// see:
	// http://stackoverflow.com/questions/15937592/spring-value-is-not-resolving-to-value-from-property-file
	// however, I recalled it worked in another occurence w/o the code below.
	// Might be Spring version issue? Have to check it out.
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}