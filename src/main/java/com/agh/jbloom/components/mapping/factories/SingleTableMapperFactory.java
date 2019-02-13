package com.agh.jbloom.components.mapping.factories;

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
        buildClassTableAccess(c);
        //Get parent DBAccess and TableAccess
        TableAccess parentDBTableAccess=parent.getTableAccess();
        TableAccess parentLocalTableAccess=((SingleTableMapper) parent).getLocalTableAccess();

        //Create class own tableAccess
        TableAccess thisLocalTableAccess=handlerBuilder.build();


        //Update DBTableAccess with current castable
        parentDBTableAccess.union(thisLocalTableAccess);

        //Create concrete class table access
        TableAccess thisReadyLocalTableAccess=parentLocalTableAccess.getIndependentCopy();
        thisReadyLocalTableAccess.union(thisLocalTableAccess);
        return new SingleTableMapper(c,parentDBTableAccess,thisReadyLocalTableAccess);
    }

    @Override
    public BaseInheritanceMapper createMapping(Class c) {
        handlerBuilder.clear();
        handlerBuilder.withColumn("ClassName","Varchar(30)",false);
        buildClassTableAccess(c);

        return new SingleTableMapper(c,handlerBuilder.build());
    }
}
