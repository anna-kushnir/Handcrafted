package com.annak.handcrafted.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {
    private static final String DATASOURCE_PREFIX = "spring.datasource.";

    @Bean
    @Primary
    public DataSource dataSource(Environment environment) {
        return DataSourceBuilder.create()
                .url(environment.getProperty(DATASOURCE_PREFIX + "url"))
                .driverClassName(environment.getProperty(DATASOURCE_PREFIX + "driver-class-name"))
                .username(environment.getProperty(DATASOURCE_PREFIX + "username"))
                .password(environment.getProperty(DATASOURCE_PREFIX + "password"))
                .build();
    }
}