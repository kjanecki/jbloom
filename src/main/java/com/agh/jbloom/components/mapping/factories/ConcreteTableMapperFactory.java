package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.mappers.ConcreteTableMapper;
import com.agh.jbloom.components.mapping.model.TableAccessBuilder;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;

public class ConcreteTableMapperFactory extends BaseInheritanceMapperFactory {

    public ConcreteTableMapperFactory(TableAccessBuilder handlerBuilder) {
        super(handlerBuilder);
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c, BaseInheritanceMapper parent) throws InvalidArgumentException {
        if(!c.getSuperclass().equals(parent.getSubject()))
            throw new InvalidArgumentException("Subject of BaseInheritanceMapper has to be a superclass of second argument");

        buildClassTableAccess(c);
        handlerBuilder.withPrimaryKey(parent.getTableAccess().getPrimaryKey());

        return new ConcreteTableMapper(c, handlerBuilder.build(), parent);
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c) throws InvalidArgumentException {
        if(!c.getSuperclass().equals(Object.class))
            throw new InvalidArgumentException("Subject of BaseInheritanceMapper has to be a superclass of second argument");
        buildClassTableAccess(c);
        return new ConcreteTableMapper(c, handlerBuilder.build());
    }
}
