package com.agh.jbloom;

import com.agh.jbloom.components.PersistenceApi;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.config.AppConfig;
import com.agh.jbloom.model.SimpleEntityImpl2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.sql.SQLException;

@SpringBootApplication
public class MainTest {

    public static void main(String[] args) throws SQLException{

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        PersistenceApi api = ctx.getBean(PersistenceApi.class);

//        SimpleEntityImpl2 obj = new SimpleEntityImpl2(5, "Dziewiec", "Dwa", 2.0, "cztery");

        IdentityField id = new IdentityField(SimpleEntityImpl2.class,5);
        SimpleEntityImpl2 obj = (SimpleEntityImpl2) api.get(id, SimpleEntityImpl2.class);
        System.out.println(obj);
    }
}
