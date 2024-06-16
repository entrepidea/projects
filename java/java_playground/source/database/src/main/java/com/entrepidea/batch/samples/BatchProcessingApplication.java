package com.entrepidea.batch.samples;
/*
* This is sample code from Spring batch official site.
* This app worked, but the one com.entrepidea.com.batch.main.BatchApplication didn't,
* even though the code are almost identical, don't know why.
*
* 06/09/24
*
* */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchProcessingApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, args)));
	}
}
