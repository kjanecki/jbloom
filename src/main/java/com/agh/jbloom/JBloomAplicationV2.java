package com.agh.jbloom;


import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.mapping.CohesionAnalyzer;
import com.agh.jbloom.components.mapping.DatabaseScheme;
import com.agh.jbloom.components.mapping.factories.ConcreteTableMapperFactory;
import com.agh.jbloom.components.mapping.factories.MapperFactory;
import com.agh.jbloom.components.mapping.model.SimpleTableAccessBuilder;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import com.agh.jbloom.config.AppConfig;
import com.agh.jbloom.model.SimpleEntity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class JBloomAplicationV2 {
    public static void main(String[] args) throws SQLException {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);


        CohesionAnalyzer cohesionAnalyzer = new CohesionAnalyzer(ctx.getBean(ConnectionPool.class));

        MapperFactory mapperFactory = new ConcreteTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));

        var handler1 = mapperFactory.createMapping(SimpleEntity.class);


        cohesionAnalyzer.checkCohesion(handler1.getTableAccess());

    }
}
