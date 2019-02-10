package com.agh.jbloom.components.mapping;

import java.sql.ResultSet;

public interface Mapper {

    Object loadObject(ResultSet set);
}
