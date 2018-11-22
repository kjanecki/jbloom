package com.agh.jbloom.config;

import com.agh.jbloom.components.MappingService;
import com.agh.jbloom.model.EntityExample;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class AppConfig {

    @Bean
    public EntityExample entityExample(){
        EntityExample entityExample = new EntityExample();
        entityExample.setId(1);
        entityExample.setName("MyEntity");
        entityExample.setValue(5);
        entityExample.setDescription("This is test entity example.");

        return entityExample;
    }

    @Bean
    public Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/kjanecki?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                "kjanecki", "pp4iDZD5n0pd3Ltk");
    }

    @Bean
    public MappingService mappingService(){
        return new MappingService();
    }
}
