package com.entrepidea.batch.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
* this is meant to test the Spring batch API but didn't work as expected, see the comment
* in samples/batchprocessing.BatchProcessingApplication.
*
* 06/09/24
*
* */
@SpringBootApplication
@ComponentScan(basePackages = "com.entrepidea.batch")
public class BatchApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchApplication.class, args)));
    }
}

