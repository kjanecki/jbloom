package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.DatabaseScheme;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.mappers.Mapper;
import com.agh.jbloom.components.mapping.model.ColumnScheme;
import com.agh.jbloom.components.mapping.model.Key;
import com.agh.jbloom.components.mapping.model.SimpleTableAccessBuilder;
import com.agh.jbloom.components.mapping.model.TableAccessBuilder;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;

public class ForeignKeyMapperFactory {

    public Mapper createMapper(BaseInheritanceMapper relatedMapper, String relatedClass, DatabaseScheme databaseScheme, Key foreignKey){
        SimpleTableAccessBuilder builder = new SimpleTableAccessBuilder(new BaseSqlTypeConverter());
        builder.withColumn("id", "varchar(30)", true);
        builder.withColumn("type", "varchar(100)", true);
        builder.withName(relatedMapper.getSubject().toString()+"-"+relatedClass);
        return null;
    }
}
