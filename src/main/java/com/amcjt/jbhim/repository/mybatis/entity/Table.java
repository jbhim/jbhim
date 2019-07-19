package com.amcjt.jbhim.repository.mybatis.entity;

import com.amcjt.jbhim.repository.Entity;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Table implements Entity, Serializable {
    private String id;
    private String name;
    private String tableName;
    private Boolean isDelete = false;
    private List<Column> columns = new ArrayList<>();
}
