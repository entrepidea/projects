package com.entrepidea.parquet.main;

import com.entrepidea.parquet.service.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
/*
* This experiment shows how we read dataset from a DB table and write the data into a parquet file locally.
*
* 06/22/24
*
* */
@SpringBootApplication
@ComponentScan(basePackages = "com.entrepidea.parquet")
public class Table2ParquetWriter implements ApplicationRunner {
    @Autowired
    private DatabaseService databaseService;

    public static void main(String[] args) {
        SpringApplication.run(Table2ParquetWriter.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String sql = "select * from employees";
        databaseService.fetchDataInBatch(sql);
    }
}
