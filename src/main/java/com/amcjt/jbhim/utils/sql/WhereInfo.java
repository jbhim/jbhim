package com.amcjt.jbhim.utils.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhereInfo {
    private String condition;
    private String table;
    private String column;
    private String operator;
    private Object value;

    @Override
    public String toString() {
        return " " + condition + " `" + table + "`.`" + column + "` " + operator +" "+ value.toString();
    }
}
