package com.agh.jbloom;

import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.mapping.*;
import com.agh.jbloom.components.mapping.factories.ClassTableMapperFactory;
import com.agh.jbloom.components.mapping.factories.ConcreteTableMapperFactory;
import com.agh.jbloom.components.mapping.factories.MapperFactory;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.model.SimpleTableAccessBuilder;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import com.agh.jbloom.components.query.Transaction;
import com.agh.jbloom.components.query.concretequeryfactory.InsertQueryFactory;
import com.agh.jbloom.config.AppConfig;
import com.agh.jbloom.model.SimpleEntity;
import com.agh.jbloom.model.SimpleEntityImpl;
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

        MapperFactory mapperFactory = new ConcreteTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));

        var handler1 = mapperFactory.createMapping(SimpleEntity.class);
        var handler2 = mapperFactory.createMapping(SimpleEntityImpl.class,handler1);
        BaseInheritanceMapper handler = mapperFactory.createMapping(SimpleEntityImpl2.class, handler2);

        SimpleEntityImpl2 obj = new SimpleEntityImpl2(5, "asdasd", "asdasdasdas", 2.0, "afgdfgd");
        IdentityField id = new IdentityField(SimpleEntityImpl2.class, 5);

        analyzer.createTable(handler1.getTableAccess());
        analyzer.createTable(handler2.getTableAccess());
        analyzer.createTable(handler.getTableAccess());

        Transaction transaction = new Transaction(ctx.getBean(ConnectionPool.class));
        handler.buildTransaction(transaction, obj, new InsertQueryFactory());
        transaction.commit();

        try {
            var o = handler.find(id, ctx.getBean(ConnectionPool.class),new InsertQueryFactory());
            System.out.println(o);

        } catch (Exception e) {
            e.printStackTrace();
        }

        analyzer.dropTable(handler.getTableAccess());
        analyzer.dropTable(handler2.getTableAccess());
        analyzer.dropTable(handler1.getTableAccess());
    }

}
