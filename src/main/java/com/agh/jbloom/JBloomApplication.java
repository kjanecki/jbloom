package com.agh.jbloom;

import com.agh.jbloom.components.MappingService;
import com.agh.jbloom.config.AppConfig;
import com.agh.jbloom.model.EntityExample;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class JBloomApplication {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();

        MappingService mappingService = (MappingService) ctx.getBean("mappingService");

        mappingService.registerEntity(EntityExample.class);

    }
}
