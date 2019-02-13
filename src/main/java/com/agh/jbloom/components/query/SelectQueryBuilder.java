package com.agh.jbloom.components.query;

import com.agh.jbloom.components.mapping.model.ColumnScheme;
import com.agh.jbloom.components.query.concretequery.SelectQuery;

import java.util.ArrayList;
import java.util.List;

public class SelectQueryBuilder {

    private List<String> columns;
    private List<String> tables;
    private List<String> conditions;

    public SelectQueryBuilder() {
        this.clear();
    }

    public void clear() {
        columns = new ArrayList<>();
        tables = new ArrayList<>();
        conditions = new ArrayList<>();
    }

    public SelectQueryBuilder withColumn(ColumnScheme column){
        columns.add(column.getName());
        return this;
    }

    public SelectQueryBuilder withTable(String tablename){
        tables.add(tablename);
        return this;
    }

    public SelectQueryBuilder withCondition(ColumnScheme c, String relation, Object value){
        conditions.add(c.getName() + " " + relation + " " + value.toString());
        return this;
    }


    public SqlQuery build(){
        SqlQuery query = new SelectQuery(columns,tables,conditions);
        this.clear();
        return query;
    }

}
