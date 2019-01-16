package com.agh.jbloom.components.query;

import com.agh.jbloom.components.mapping.TableScheme;

public class QueryFactory {

    private QueryBuilder queryBuilder;

    public QueryFactory(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public SqlQuery createUpdateQuery(TableScheme tableScheme, Object obj){ return null;}
    public SqlQuery createDeleteQuery(TableScheme tableScheme, Object obj){ return null;}
    public SqlQuery createInsertQuery(TableScheme tableScheme, Object obj){ return null;}
    public SqlQuery createSelectQuery(TableScheme tableScheme, Object obj){ return null;}

}
