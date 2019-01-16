package com.agh.jbloom.components.mapping;

import com.agh.jbloom.components.dataaccess.DataBaseConnector;

public class CohesionAnalyzer {

    private DataBaseConnector dataBaseConnector;

    public CohesionAnalyzer(DataBaseConnector dataBaseConnector) {
        this.dataBaseConnector = dataBaseConnector;
    }

    public boolean checkCohesion(TableScheme tableScheme){
        return false;
    }
}
