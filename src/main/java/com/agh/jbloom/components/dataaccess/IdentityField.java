package com.agh.jbloom.components.dataaccess;

public class IdentityField {
    private Class subject;
    private Long id;

    public IdentityField(Class subject, Long id) {
        this.subject = subject;
        this.id = id;
    }
}
