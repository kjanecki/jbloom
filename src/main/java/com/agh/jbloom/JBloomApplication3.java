package com.agh.jbloom;

import com.agh.jbloom.classtabletest.SimpleEntity;
import com.agh.jbloom.classtabletest.SimpleEntityImpl;
import com.agh.jbloom.classtabletest.SimpleEntityImpl2;
import com.agh.jbloom.components.dataaccess.ConnectionPool;
import com.agh.jbloom.components.dataaccess.IdentityField;
import com.agh.jbloom.components.mapping.*;
import com.agh.jbloom.components.mapping.factories.ClassTableMapperFactory;
import com.agh.jbloom.components.mapping.factories.MapperFactory;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.model.SimpleTableAccessBuilder;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;
import com.agh.jbloom.components.query.Transaction;
import com.agh.jbloom.components.query.concretequeryfactory.DeleteQueryFactory;
import com.agh.jbloom.components.query.concretequeryfactory.UpdateQueryFactory;
import com.agh.jbloom.config.AppConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

@SpringBootApplication
public class JBloomApplication3 {

    public static void main(String[] args) throws SQLException{

        try {
            System.out.println(Class.forName("components.mapping.mappers.ClassTableMapper"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        CohesionAnalyzer analyzer = ctx.getBean(CohesionAnalyzer.class);

        MapperFactory mapperFactory = new ClassTableMapperFactory(new SimpleTableAccessBuilder(new BaseSqlTypeConverter()));

        var handler1 = mapperFactory.createMapping(SimpleEntity.class);
        var handler2 = mapperFactory.createMapping(SimpleEntityImpl.class,handler1);
        BaseInheritanceMapper handler = mapperFactory.createMapping(SimpleEntityImpl2.class, handler2);

        SimpleEntityImpl2 obj = new SimpleEntityImpl2(5, "Osiem", "Dwa", 2.0, "Trzy");
        SimpleEntityImpl obj2 = new SimpleEntityImpl(23,"Bobos","Janecki To Cebulka",2.0);
        IdentityField id = new IdentityField(SimpleEntityImpl2.class, 5);

        System.out.println();
        System.out.println("-------------------");
        System.out.println("Creating tables");
        System.out.println("-------------------");

//        analyzer.createTable(handler1.getTableAccess());
//        analyzer.createTable(handler2.getTableAccess());
//        analyzer.createTable(handler.getTableAccess());

        Transaction transaction = new Transaction(ctx.getBean(ConnectionPool.class));
        handler.buildTransaction(transaction, obj, new UpdateQueryFactory());
        handler1.buildTransaction(transaction,obj2,new DeleteQueryFactory());
        transaction.commit();
        System.out.println();
        System.out.println("-------------------");
        System.out.println("INSERTED IN TO DB");
        System.out.println("-------------------");



//        try {
//            var o = handler.find(id, ctx.getBean(ConnectionPool.class),new InsertQueryFactory());
//            System.out.println(o);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        analyzer.dropTable(handler.getTableAccess());
//        analyzer.dropTable(handler2.getTableAccess());
//        analyzer.dropTable(handler1.getTableAccess());
//
//
//        var foreignKeyHandler1 = mapperFactory.createMapping(ForeignKeyEntity.class);
//        for(var key : foreignKeyHandler1.getRelatedClasses().keySet()) {
//            try {
//                var foreignKeyHandler2 = mapperFactory.createMapping(Class.forName(key));
//                foreignKeyHandler1.addMapper(foreignKeyHandler2);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

//        System.out.println(foreignKeyHandler1);
//        System.out.println();
//        List<String> list = new ArrayList<>();
//        list.add("Str");
//        System.out.println(list);
//        System.out.println(list.getClass());
//        try {
//            get(list.getClass());
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
    }

}
