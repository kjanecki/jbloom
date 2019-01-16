package com.agh.jbloom.components.dataaccess;

import com.agh.jbloom.components.mapping.TableScheme;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;


@Component
public class DataBaseConnector {
        private Connection connection;
        private DataSource dataSource;
        private DatabaseMetaData databaseMetaData;


    public DataBaseConnector(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        connection = dataSource.getConnection();
        databaseMetaData = connection.getMetaData();
    }



    private void createTable(TableScheme tableScheme) {
    }


    public void insert(String query){
    }


    class TableNotFoundException extends SQLException{
    }
}



