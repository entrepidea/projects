package com.entrepidea.batch;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        String jdbcProto = "jdbc:oracle:thin:@(DESCRIPTION=(CONNECT_DATA=(SERVICE_NAME=ORCLPDB1))(ADDRESS=(PROTOCOL=tcp)(HOST=192.168.86.41)(PORT=1521)))";
        hikariConfig.setJdbcUrl(jdbcProto);
        hikariConfig.setUsername("hr");
        hikariConfig.setPassword("f0rever");
        hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");
        return new HikariDataSource(hikariConfig);
    }
    @Bean
    public JdbcCursorItemReader<Employee> reader(DataSource dataSource) {
        log.info("Reader entered.");
        JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID FROM EMPLOYEES");
        reader.setRowMapper(new EmployeeRowMapper());
        return reader;
    }

    @Bean
    public ItemProcessor<Employee, Employee> processor() {
        log.info("Processor entered.");
        return item -> {
            // Process item (if needed)
            long id = item.getEmployeeId();
            log.info("{}",id);
            return item;
        };
    }

    @Bean
    public ItemWriter<Employee> writer() {
        log.info("Writer entered.");
        return items -> {
            for (Employee item : items) {
                // Write item to the destination
                long id = item.getEmployeeId();
                log.info("{}",id);
            }
        };
    }


/*    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        //return new ResourcelessTransactionManager();
        return new DataSourceTransactionManager(dataSource);

    }*/

    @Bean
    public Step step1(JobRepository jobRepository, ItemProcessor<Employee, Employee> processor, JdbcCursorItemReader<Employee> reader, ItemWriter<Employee> writer,
                      DataSourceTransactionManager transactionManager)
    {
        return new StepBuilder("step1", jobRepository)
                .<Employee, Employee> chunk(10,transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
/*
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
*/
    }
}