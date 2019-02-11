package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.model.TableAccessBuilder;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;

public class ConcreteTableMapperFactory implements MapperFactory {

    private TableAccessBuilder handlerBuilder;

    public ConcreteTableMapperFactory(TableAccessBuilder handlerBuilder) {
        this.handlerBuilder = handlerBuilder;
    }

    @Override
    public BaseInheritanceMapper createMapping(BaseInheritanceMapper handler, Class c) {
        BaseInheritanceMapper newHandler = createMapping(c, handler.getSubject());
        newHandler.getTableAccess().union(handler.getTableAccess());
        return newHandler;
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c, Class stop) {

        String tableName = getTableName(c);
        handlerBuilder.withSubjectClass(c).withName(tableName);

        Class current = c;
        do{
            handlerBuilder.withClass(current);
        }while ((current = current.getSuperclass()) != stop);
        return new BaseInheritanceMapper(c, handlerBuilder.build());
    }
}
