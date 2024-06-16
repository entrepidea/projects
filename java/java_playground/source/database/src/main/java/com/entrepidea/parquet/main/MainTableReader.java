package com.entrepidea.parquet.main;

import com.entrepidea.parquet.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.entrepidea.parquet")
public class MainTableReader implements ApplicationRunner {
    @Autowired
    private DatabaseService databaseService;

    public static void main(String[] args) {
        SpringApplication.run(MainTableReader.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String sql = "select * from employees";
        databaseService.fetchDataInBatch(sql);
    }
}
