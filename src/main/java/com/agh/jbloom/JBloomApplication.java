package com.agh.jbloom;

import com.agh.jbloom.components.MappingService;
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
        MappingService mappingService = ctx.getBean(MappingService.class);
        mappingService.registerEntity(EntityExample.class);
        try {
            mappingService.insert(ctx.getBean(EntityExample.class));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
