package com.agh.jbloom.components.data_base_connector;

import com.agh.jbloom.annotations.Id;
import com.agh.jbloom.components.DataSource;
import com.agh.jbloom.components.Table;
import com.agh.jbloom.model.SqlParamAdapter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


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



    private void createTable(Table table) {
    }


    public void insert(String query){
    }


    class TableNotFoundException extends SQLException{
    }
}



