package com.agh.jbloom.components.query.concretequery;

import com.agh.jbloom.components.mapping.mappers.TableAccess;
import com.agh.jbloom.components.query.SqlQuery;

import java.util.List;

public class SelectQuery extends SqlQuery {

    private List<String> columns;
    private List<String> tables;
    private List<String> conditions;

    public SelectQuery(List<String> columns, List<String> tables, List<String> conditions) {
        this.columns = columns;
        this.tables = tables;
        this.conditions = conditions;
    }

    public SelectQuery(TableAccess tableAccess, Object object) {
        super(tableAccess, object);
    }


    @Override
    public String toString() {
//        StringBuilder queryString = new StringBuilder("SELECT * FROM ");
//
//        queryString.append(getTableAccess().getTableScheme().getName());
//
//        queryString.append(" WHERE ");
//
//        for(var field: getObject().getClass().getDeclaredFields()){
//
//            //TODO figure out how it should works, if there was some PK it would be great
//            //TODO but if no we have to lop through all teh filed and compere them??
//            String value = "test_value";
//
//            queryString.append(field.getName());
//            queryString.append('=');
//            queryString.append(value);
//            queryString.append(", ");
//
//        }
//
//        queryString.delete(queryString.length()-2, queryString.length());
//
//        queryString.append(';');
//
//        return queryString.toString();

        StringBuilder query = new StringBuilder("select ");
        query.append(this.generateColumnsClause())
                .append(" from ")
                .append(this.generateFromClause())
                .append(" where ")
                .append(this.generateWhereClause())
                .append(";");
        return query.toString();
    }

    private String generateColumnsClause() {
        return generateClause(columns, ", ");
    }

    private String generateFromClause() {
        return generateClause(tables, " natural join ");
    }

    private String generateWhereClause() {
        return generateClause(conditions, " and ");
    }

    private String generateClause(List<String> list, String separator){

        StringBuilder buff = new StringBuilder();
        boolean first = true;
        for(var str : list){
            if(first)
                first = false;
            else
                buff.append(separator);
            buff.append(str);
        }
        return buff.toString();

    }

}
