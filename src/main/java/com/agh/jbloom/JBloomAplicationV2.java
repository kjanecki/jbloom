package com.agh.jbloom;


import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.mapping.CohesionAnalyzer;
import com.agh.jbloom.config.AppConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class JBloomAplicationV2 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);


        CohesionAnalyzer cohesionAnalyzer = new CohesionAnalyzer(ctx.getBean(ConnectionPool.class));

    }
}
