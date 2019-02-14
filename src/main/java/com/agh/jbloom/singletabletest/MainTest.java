package com.agh.jbloom.singletabletest;

import com.agh.jbloom.components.PersistenceApi;
import com.agh.jbloom.components.mapping.CohesionAnalyzer;
import com.agh.jbloom.config.AppConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class MainTest {

    public static void main(String[] args) throws SQLException{

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        PersistenceApi api = ctx.getBean(PersistenceApi.class);

        SimpleEntityImpl obj = new SimpleEntityImpl(10, "Dziewiec", "Dwa", 2.0);
        api.insert(obj);

//        IdentityField id = new IdentityField(SimpleEntityImpl2.class,5);
//        SimpleEntityImpl2 obj = (SimpleEntityImpl2) api.get(id, SimpleEntityImpl2.class);
//        System.out.println(obj);
    }
}
