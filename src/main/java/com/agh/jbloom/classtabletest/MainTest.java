package com.agh.jbloom.classtabletest;

import com.agh.jbloom.components.PersistenceApi;
import com.agh.jbloom.components.dataaccess.IdentityField;
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
        IdentityField id = api.getKey(SimpleEntityImpl2.class);
        SimpleEntityImpl2 obj = new SimpleEntityImpl2((int)id.getId(), "Dziewiec", "Dwa", 2.0, "cztery");
        api.insert(obj);


//        IdentityField id = new IdentityField(SimpleEntityImpl2.class,5);
//        SimpleEntityImpl2 obj = (SimpleEntityImpl2) api.get(id, SimpleEntityImpl2.class);
//        System.out.println(obj);
    }
}
