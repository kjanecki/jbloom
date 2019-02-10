package com.agh.jbloom.components.query;

import com.agh.jbloom.components.mapping.TableScheme;

public abstract class SqlQuery {

    private TableScheme tableScheme;
    private Object object;

    public SqlQuery(TableScheme tableScheme, Object object) {
        this.tableScheme = tableScheme;
        this.object = object;
    }

    public TableScheme getTableScheme() {
        return tableScheme;
    }

    public void setTableScheme(TableScheme tableScheme) {
        this.tableScheme = tableScheme;
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
