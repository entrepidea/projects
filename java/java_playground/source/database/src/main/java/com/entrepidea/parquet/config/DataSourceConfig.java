package com.entrepidea.parquet.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
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
}
