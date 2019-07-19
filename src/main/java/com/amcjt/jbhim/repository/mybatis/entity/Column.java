package com.amcjt.jbhim.repository.mybatis.entity;

import com.amcjt.jbhim.repository.Entity;
import com.amcjt.jbhim.utils.enums.ColumnType;
import lombok.Data;

import java.io.Serializable;

@Data
public class Column implements Entity, Serializable {

    private String id;
    private String tableId;
    private String name;
    private String columnName;
    private ColumnType columnType = ColumnType.VARCHAR;
    private Boolean isDelete = false;
    private Boolean isPrimaryKey = false;
    private Boolean isRequired = false;
    private Boolean isUnicode = false;
    private Boolean isProtected = false;
    private Boolean isHidden = false;
    private String defaultValue;

}
