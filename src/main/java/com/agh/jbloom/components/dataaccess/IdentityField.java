package com.agh.jbloom.components.dataaccess;

public class IdentityField {
    private Class subject;
    private Object id;

    public IdentityField(Class subject, Object id) {
        this.subject = subject;
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}
