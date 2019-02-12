package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.model.TableAccessBuilder;
import com.agh.jbloom.components.mapping.mappers.SingleTableMapper;
public class SingleTableMapperFactory implements MapperFactory {
    private TableAccessBuilder handlerBuilder;

    @Override
    public BaseInheritanceMapper createMapping(Class c, BaseInheritanceMapper parent) throws IllegalAccessException {
        //TODO maybe check if paren is really parrent
        handlerBuilder.clear();
        handlerBuilder.withName(c.getName()).withSubjectClass(c).withClass(c);
        //TODO maybe add field specyfing if class extedns or no
        parent.getTableAccess().union(handlerBuilder.build());
        return parent;
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c) {
        String tableName=c.getName();
        handlerBuilder.clear();
        handlerBuilder.withClass(c).withName(tableName).withClass(c);

        return new SingleTableMapper(c,handlerBuilder.build());
    }
}
