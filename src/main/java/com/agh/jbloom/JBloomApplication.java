package com.agh.jbloom;

import com.agh.jbloom.components.mapping.MappingService;
import com.agh.jbloom.config.AppConfig;
import com.agh.jbloom.model.EntityExample;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.sql.SQLException;

@SpringBootApplication
public class JBloomApplication {

    public static void main(String[] args) throws SQLException{

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

    }
}
