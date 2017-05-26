package com.Lemon.mysqlcontrast;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Configuration
public class GlobalDataConfiguration {
	@Bean(name="primaryDataSource")  
	@Qualifier("primaryDataSource")
	@Primary
    @ConfigurationProperties(prefix="spring.datasource.primary")  
    public DataSource primaryDataSource() {
        System.out.println("-------------------- primaryDataSource init ---------------------");  
        return DataSourceBuilder.create().build();  
    }  
      
    @Bean(name="secondaryDataSource")  
    @Qualifier("secondaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource.secondary")  
    public DataSource secondaryDataSource() {  
        System.out.println("-------------------- secondaryDataSource init ---------------------");  
        return DataSourceBuilder.create().build();  
    }
}
