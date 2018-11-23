package com.agh.jbloom.config;

import com.agh.jbloom.components.DataSource;
import com.agh.jbloom.components.MappingService;
import com.agh.jbloom.model.EntityExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("application.properties")
@ComponentScan("com.agh.jbloom.components")
public class AppConfig {

    @Bean
    public EntityExample entityExample(){
        EntityExample entityExample = new EntityExample();
        entityExample.setId(3);
        entityExample.setName("MyEntity");
        entityExample.setValue(5);
        entityExample.setDescription("This is test entity example.");

        return entityExample;
    }

    @Bean
    public DataSource dataSource(){
        return new DataSource();
    }

    @Bean
    public Connection connection() throws SQLException {
        return dataSource().getConnection();
    }
}
