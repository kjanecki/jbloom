package com.agh.jbloom.components.mapping.factories;

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
    public BaseInheritanceMapper createMapping(BaseInheritanceMapper handler, Class c) {
        BaseInheritanceMapper newHandler = createMapping(c, handler.getSubject());
        newHandler.setParent(handler);
        return newHandler;
    }


    @Override
    public BaseInheritanceMapper createMapping(Class c, Class stop) {
        if (c.equals(stop)){
            return null;
        }else{
            BaseInheritanceMapper parent = createMapping(c.getSuperclass(), stop);

            String tableName = getTableName(c);
            handlerBuilder.withSubjectClass(c).withName(tableName);
            TableAccess mapper;

            handlerBuilder
                    .withName(getTableName(c))
                    .withSubjectClass(c)
                    .withClass(c);

            if (parent == null){
                return new BaseInheritanceMapper(c, handlerBuilder.build());
            }
            else {
                TableAccess parentMapper = parent.getTableAccess();
                Key parentPrimaryKey = parentMapper.getPrimaryKey();
                String references = parentMapper.getTableScheme().getName()+"("+parentPrimaryKey.getColumnScheme().getName()+")";
                Key childPrimaryKey = new Key(parentPrimaryKey.getColumnScheme(), parentPrimaryKey.getFieldAccess(), references);
                handlerBuilder.withPrimaryKey(childPrimaryKey);
                handlerBuilder.withForeignKey(childPrimaryKey);
                return new BaseInheritanceMapper(c, parent, handlerBuilder.build());
            }
        }
    }
}
