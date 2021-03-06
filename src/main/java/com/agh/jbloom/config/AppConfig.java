package com.agh.jbloom.config;

import com.agh.jbloom.components.PersistenceApi;
import com.agh.jbloom.components.dataaccess.ConnectionObserver;
import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.DataSource;
import com.agh.jbloom.components.mapping.CohesionAnalyzer;
import com.agh.jbloom.components.mapping.DatabaseScheme;
import com.agh.jbloom.components.mapping.MappingDirector;
import com.agh.jbloom.model.EntityExample;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@PropertySource("application.properties")
@ComponentScan("com.agh.jbloom.components")
public class AppConfig {

    @Bean
    public EntityExample entityExample(){
        EntityExample entityExample = new EntityExample();
        entityExample.setId(4);
        entityExample.setName("Dupa");
        entityExample.setValue(5);
        entityExample.setDescription("Dupa");

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

    @Bean
    public ConnectionPool connectionPool(){
        return new ConnectionPool(dataSource());
    }

    @Bean
    public DatabaseScheme databaseScheme() {
        return new DatabaseScheme();
    }

    @Bean
    public CohesionAnalyzer cohesionAnalyzer(){
        return new CohesionAnalyzer(connectionPool());
    }

    @Bean
    public MappingDirector mappingDirector(){
        return new MappingDirector(cohesionAnalyzer(), databaseScheme());
    }

    @Bean
    public ConnectionObserver connectionObserver(){
        return new ConnectionObserver(connectionPool());
    }

    @Bean
    public PersistenceApi persistenceApi(){
        return new PersistenceApi(connectionObserver(),connectionPool(),mappingDirector(),databaseScheme());
    }
}
