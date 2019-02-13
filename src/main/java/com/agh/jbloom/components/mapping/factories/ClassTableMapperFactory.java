package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.mappers.ClassTableMapper;
import com.agh.jbloom.components.mapping.model.Key;
import com.agh.jbloom.components.mapping.model.TableAccessBuilder;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.mappers.TableAccess;

public class ClassTableMapperFactory extends BaseInheritanceMapperFactory {

    public ClassTableMapperFactory(TableAccessBuilder handlerBuilder) {
        super(handlerBuilder);
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c, BaseInheritanceMapper parent) throws InvalidArgumentException {
        if(!c.getSuperclass().equals(parent.getSubject()))
            throw new InvalidArgumentException("Provided mapper class has to contain superclass of a class to map.");

        String tableName = getTableName(c);
        TableAccess parentMapper = parent.getTableAccess();
        Key parentPrimaryKey = parentMapper.getPrimaryKey();
        String references = parentMapper.getTableScheme().getName()+"("+parentPrimaryKey.getColumnScheme().getName()+")";
        Key childPrimaryKey = new Key(parentPrimaryKey.getColumnScheme(), parentPrimaryKey.getFieldAccess(), references);

        buildClassTableAccess(c);
        handlerBuilder.withPrimaryKey(childPrimaryKey)
                .withForeignKey(childPrimaryKey);

        return new ClassTableMapper(c, handlerBuilder.build(), parent);
    }


    @Override
    public BaseInheritanceMapper createMapping(Class c) throws InvalidArgumentException {
        if(!c.getSuperclass().equals(Object.class))
            throw new InvalidArgumentException("Subject of BaseInheritanceMapper has to be a superclass of second argument");
        buildClassTableAccess(c);
        return new ClassTableMapper(c, handlerBuilder.build());
    }
}
