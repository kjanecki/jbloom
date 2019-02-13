package com.agh.jbloom.components.mapping.factories;

import com.agh.jbloom.components.mapping.DatabaseScheme;
import com.agh.jbloom.components.mapping.mappers.BaseInheritanceMapper;
import com.agh.jbloom.components.mapping.mappers.Mapper;
import com.agh.jbloom.components.mapping.model.Key;
import com.agh.jbloom.components.mapping.model.SimpleTableAccessBuilder;
import com.agh.jbloom.components.mapping.model.TableAccessBuilder;
import com.agh.jbloom.components.query.BaseSqlTypeConverter;

public class ForeignKeyMapperFactory {

    public Mapper createMapper(BaseInheritanceMapper relatedMapper, DatabaseScheme databaseScheme, Key foreignKey){
        TableAccessBuilder builder = new SimpleTableAccessBuilder(new BaseSqlTypeConverter());
        return null;
    }
}
