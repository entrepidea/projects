package com.entrepidea.bootstraps;

import com.entrepidea.bootstraps.supports.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

/**
 * The code tests the command line arguments. To test it: <program> --lo=xxx --od=xxx --td=xxx
 * http://entrepidea.com/blogs/tech/index.php/2016/09/30/various-ways-to-bootstrap-a-spring-container/
 * */

@SpringBootApplication
public class SpringBootStrapper4 implements CommandLineRunner {

	Logger log = LoggerFactory.getLogger(SpringBootStrapper4.class);

	//Field injection is not recommended
	//@Autowired
	private Client client;

	//Constructor injection - the Autowired annotation can be omitted since Spring 4.x
	public SpringBootStrapper4(Client client){
		this.client = client;
	}
	
	@Override
	public void run(String... args) throws Exception {
    	CommandLinePropertySource<?> commandLinePropertySource = new SimpleCommandLinePropertySource(args);
		String listOnly = commandLinePropertySource.getProperty("lo");
		String originDir = commandLinePropertySource.getProperty("od");
		String targetDir =  commandLinePropertySource.getProperty("td");
		
		log.info("listOnly={}, origin dir={}, target dir={}",listOnly, originDir, targetDir);
		
		log.info("name={}",client.getName());
		
	}
	
	public static void main(String... args){
		SpringApplication.run(SpringBootStrapper4.class, args);
	}
}
