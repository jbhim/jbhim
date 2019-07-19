package com.amcjt.jbhim.utils.enums;

import java.util.HashMap;
import java.util.Map;

public enum ColumnType {
    BIT, INT, LONG, DECIMAL, IDENTITY, VARCHAR, TEXT, DATETIME, DATE, TIME, ENUM, SET, HTML, TEXTAREA, FILE, NONE;

    private static Map<ColumnType, String> columnTypeMap = new HashMap<>();

    static {
        columnTypeMap.put(BIT, "bit");
        columnTypeMap.put(INT, "int");
        columnTypeMap.put(LONG, "bigint");
        columnTypeMap.put(DECIMAL, "decimal(18, 6)");
        columnTypeMap.put(IDENTITY, "char(36)");
        columnTypeMap.put(VARCHAR, "varchar(400)");
        columnTypeMap.put(TEXT, "text");
        columnTypeMap.put(DATETIME, "datetime");
        columnTypeMap.put(DATE, "date");
        columnTypeMap.put(TIME, "time");
        columnTypeMap.put(ENUM, "varchar(255)");
        columnTypeMap.put(SET, "set");
        columnTypeMap.put(HTML, "text");
        columnTypeMap.put(TEXTAREA, "text");
        columnTypeMap.put(FILE, "text");
        columnTypeMap.put(NONE, "text");
    }

    public String getSqlType() {
        return columnTypeMap.get(this);
    }

}
