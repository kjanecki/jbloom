package com.agh.jbloom.components.mapping.model;

import com.agh.jbloom.components.mapping.mappers.TableAccess;

public interface TableAccessBuilder {

    void clear();
    TableAccessBuilder withName(String tableName);
    TableAccessBuilder withSubjectClass(Class c);
    TableAccessBuilder withClass(Class c);
    TableAccessBuilder withPrimaryKey(Key key);
    TableAccessBuilder withForeignKey(Key key);
    TableAccessBuilder withColumn(String columnName,String type,boolean isNullable);
    TableAccess build();
}