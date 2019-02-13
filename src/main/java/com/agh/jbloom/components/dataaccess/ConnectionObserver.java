package com.agh.jbloom.components.dataaccess;

import org.springframework.stereotype.Component;

import java.util.Observable;
import java.util.Observer;

@Component
public class ConnectionObserver implements Observer {

    private ConnectionPool connectionPool;

    public ConnectionObserver(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
