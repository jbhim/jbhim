package com.amcjt.jbhim.utils.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfo {
    private String column;
    private String columnAlias;
    private String tableAlias;

    @Override
    public String toString() {
        if (StringUtils.isBlank(column)) {
            throw new IllegalArgumentException("column must not blank");
        }
        String columnStr = StringUtils.isBlank(column) ? "" : "`" + column + "`";
        String tableAliasStr = StringUtils.isBlank(tableAlias) ? " " : " `" + tableAlias + "`.";
        String columnAliasStr = StringUtils.isBlank(columnAlias) ? "" : " as '" + columnAlias + "'";
        return tableAliasStr.concat(columnStr).concat(columnAliasStr);
    }
}
