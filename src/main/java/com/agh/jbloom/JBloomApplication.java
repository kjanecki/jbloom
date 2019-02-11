package com.agh.jbloom;

import com.agh.jbloom.components.mapping.*;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import com.agh.jbloom.config.AppConfig;
import com.agh.jbloom.model.EntityExample;
import com.agh.jbloom.model.SimpleEntityImpl;
import com.agh.jbloom.model.SimpleEntityImpl2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@SpringBootApplication
public class JBloomApplication {

    public static void main(String[] args) throws SQLException{

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        CohesionAnalyzer analyzer = ctx.getBean(CohesionAnalyzer.class);

        MappingService mappingService = new ClassTableMappingService(new SimpleMapperBuilder(new BaseSqlTypeConverter()));

        BaseMapperHandler handler = mappingService.createMapping(SimpleEntityImpl2.class);

        Stack<InheritanceMapper> mappers = new Stack<>();

        mappers.push(handler.getMapper());
        while((handler = handler.getParent())!=null)
            mappers.push(handler.getMapper());

        while (!mappers.empty()){
            analyzer.createTable(mappers.pop());
//            analyzer.dropTable(mappers.pop());
        }
    }

}
