package com.agh.jbloom.singletabletest;

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
        IdentityField id = api.getKey(SimpleEntityImpl.class);
        SimpleEntityImpl obj = new SimpleEntityImpl((int)id.getId(), "Dziewiec", "Dwa", 2.0);
        api.insert(obj);

        SimpleEntityImpl obj2 = new SimpleEntityImpl(8, "Dziewiec", "Dwa", 2.0);

        api.delete(obj2);


//        IdentityField id = new IdentityField(SimpleEntityImpl2.class,5);
//        SimpleEntityImpl2 obj = (SimpleEntityImpl2) api.get(id, SimpleEntityImpl2.class);
//        System.out.println(obj);
    }
}
