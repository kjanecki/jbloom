package com.agh.jbloom.components.mapping;

import java.sql.ResultSet;

public interface MappingHandler {

    Object loadObject(ResultSet set);
}
