package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.annotations.Table;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.mapping.model.TableAccessBuilder;
import com.agh.jbloom.components.mapping.mappers.SingleTableMapper;
public class SingleTableMapperFactory extends BaseInheritanceMapperFactory {

    public SingleTableMapperFactory(TableAccessBuilder handlerBuilder) {
        super(handlerBuilder);
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c, BaseInheritanceMapper parent) throws InvalidArgumentException {

        handlerBuilder.clear();
        handlerBuilder.withName(c.getName()).withSubjectClass(c).withClass(c);

        //Get parent DBAcces and TableAccess
        TableAccess parentDBTableAcces=((SingleTableMapper)parent).getDBTableAccess();
        TableAccess parentTableAcces=parent.getTableAccess();

        //Create class own talbeAccess
        TableAccess classPrivateTableAccess=handlerBuilder.build();


        //Update DBTableAcces with current classtable
        parentDBTableAcces.union(classPrivateTableAccess);

        //Create concrete class table access
        TableAccess readyTableAcces=parentTableAcces.getIndependentCopy();
        readyTableAcces.union(classPrivateTableAccess);
        return new SingleTableMapper(c,readyTableAcces,parentDBTableAcces);
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c) {
        String tableName=c.getName();
        handlerBuilder.clear();
        handlerBuilder.withColumn("ClassName","String",false);
        handlerBuilder.withClass(c).withName(tableName).withClass(c);

        return new SingleTableMapper(c,handlerBuilder.build());
    }
}
