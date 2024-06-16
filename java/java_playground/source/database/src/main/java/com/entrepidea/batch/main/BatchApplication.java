package com.entrepidea.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
* this is meant to test the Spring batch API but didn't work as expected, see the comment
* in batchprocessing.BatchProcessingApplication.
*
* 06/09/24
*
* */
@SpringBootApplication
public class BatchApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(BatchApplication.class, args)));
    }
}

