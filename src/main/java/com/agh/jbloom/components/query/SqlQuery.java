package com.agh.jbloom.components.query;

import com.agh.jbloom.components.mapping.mappers.TableAccess;

public abstract class SqlQuery {

    private TableAccess tableAccess;
    private Object object;

    public SqlQuery(TableAccess tableAccess, Object object) {
        this.tableAccess = tableAccess;
        this.object = object;
    }

    public SqlQuery() {
    }

    public TableAccess getTableAccess() {
        return tableAccess;
    }

    public void setTableAccess(TableAccess tableAccess) {
        this.tableAccess = tableAccess;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    //void add(Object obj);
    public abstract String toString();
}
