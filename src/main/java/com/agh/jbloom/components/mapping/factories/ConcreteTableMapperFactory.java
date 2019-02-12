package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.mappers.ConcreteTableMapper;
import com.agh.jbloom.components.mapping.model.TableAccessBuilder;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;

public class ConcreteTableMapperFactory implements MapperFactory {

    private TableAccessBuilder handlerBuilder;

    public ConcreteTableMapperFactory(TableAccessBuilder handlerBuilder) {
        this.handlerBuilder = handlerBuilder;
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c, BaseInheritanceMapper parent) throws IllegalAccessException {
        if(!c.getSuperclass().equals(parent.getSubject()))
            throw new IllegalAccessException("Subject of BaseInheritanceMapper has to be a superclass of second argument");

        String tableName = getTableName(c);
        handlerBuilder.withSubjectClass(c)
                .withName(tableName)
                .withClass(c);

        return new ConcreteTableMapper(c, handlerBuilder.build(), parent);
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c) {

        String tableName = getTableName(c);
        handlerBuilder.withSubjectClass(c)
                .withName(tableName)
                .withClass(c);

        return new ConcreteTableMapper(c, handlerBuilder.build());

//        Class current = c;
//        do{
//            handlerBuilder.withClass(current);
//        }while ((current = current.getSuperclass()) != parent);
//        return new BaseInheritanceMapper(c, handlerBuilder.build());
    }
}
