package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.mappers.ClassTableMapper;
import com.agh.jbloom.components.mapping.model.Key;
import com.agh.jbloom.components.mapping.model.TableAccessBuilder;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.mappers.TableAccess;

public class ClassTableMapperFactory implements MapperFactory {

    TableAccessBuilder handlerBuilder;

    public ClassTableMapperFactory(TableAccessBuilder handlerBuilder) {
        this.handlerBuilder = handlerBuilder;
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c, BaseInheritanceMapper parent) throws IllegalAccessException {
        if(!c.getSuperclass().equals(parent.getSubject()))
            throw new IllegalAccessException("Subject of BaseInheritanceMapper has to be a superclass of second argument");

        String tableName = getTableName(c);
        TableAccess parentMapper = parent.getTableAccess();
        Key parentPrimaryKey = parentMapper.getPrimaryKey();
        String references = parentMapper.getTableScheme().getName()+"("+parentPrimaryKey.getColumnScheme().getName()+")";
        Key childPrimaryKey = new Key(parentPrimaryKey.getColumnScheme(), parentPrimaryKey.getFieldAccess(), references);

        handlerBuilder
                .withName(getTableName(c))
                .withSubjectClass(c)
                .withClass(c)
                .withPrimaryKey(childPrimaryKey)
                .withForeignKey(childPrimaryKey);

        return new ClassTableMapper(c, handlerBuilder.build(), parent);
    }


    @Override
    public BaseInheritanceMapper createMapping(Class c) throws IllegalAccessException {
        if(!c.getSuperclass().equals(Object.class))
            throw new IllegalAccessException("Subject of BaseInheritanceMapper has to be a superclass of second argument");

        String tableName = getTableName(c);
        handlerBuilder
                .withName(getTableName(c))
                .withSubjectClass(c)
                .withClass(c);

        return new ClassTableMapper(c, handlerBuilder.build());
    }
}
