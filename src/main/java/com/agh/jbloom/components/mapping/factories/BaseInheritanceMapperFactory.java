package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.model.TableAccessBuilder;

public abstract class BaseInheritanceMapperFactory implements MapperFactory{

    protected TableAccessBuilder handlerBuilder;

    public BaseInheritanceMapperFactory(TableAccessBuilder handlerBuilder) {
        this.handlerBuilder = handlerBuilder;
    }

    protected void buildClassTableAccess(Class c)  {
        handlerBuilder.withSubjectClass(c)
                .withName(getTableName(c))
                .withClass(c);
    }
}

class InvalidArgumentException extends RuntimeException{

    public InvalidArgumentException(String message) {
        super(message);
    }
}