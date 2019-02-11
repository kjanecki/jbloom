package com.agh.jbloom;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.mapping.*;
import com.agh.jbloom.components.mapping.factories.ClassTableMapperFactory;
import com.agh.jbloom.components.mapping.factories.MapperFactory;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.model.SimpleTableAccessBuilder;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import com.agh.jbloom.components.query.Transaction;
import com.agh.jbloom.components.query.concretequeryfactory.InsertQueryFactory;
import com.agh.jbloom.config.AppConfig;
import com.agh.jbloom.model.SimpleEntityImpl2;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class JBloomApplication {

    public static void main(String[] args) throws SQLException{

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        CohesionAnalyzer analyzer = ctx.getBean(CohesionAnalyzer.class);

        MapperFactory mapperFactory = new ClassTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));

        BaseInheritanceMapper handler = mapperFactory.createMapping(SimpleEntityImpl2.class);

        SimpleEntityImpl2 obj = new SimpleEntityImpl2(5, "asdasd", "asdasdasdas", 2.0, "afgdfgd");

        Transaction transaction = new Transaction(ctx.getBean(ConnectionPool.class));
        handler.buildTransaction(transaction, obj, new InsertQueryFactory());
        transaction.commit();

//        Stack<TableAccess> mappers = new Stack<>();
//
//        mappers.push(handler.getTableAccess());
//        while((handler = handler.getParent())!=null)
//            mappers.push(handler.getTableAccess());
//
//        while (!mappers.empty()){
////            analyzer.createTable(mappers.pop());
////            analyzer.dropTable(mappers.pop());
//        }
//

    }

}
