package com.amcjt.jbhim.utils.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableInfo {
    private String table;
    private String tableAlias;
    private boolean isMainTable;

    @Override
    public String toString() {
        if (StringUtils.isBlank(table)) {
            throw new IllegalArgumentException("table must not blank");
        }
        String tableStr = StringUtils.isBlank(table) ? "" : "`" + table + "`";
        String tableAliasStr = StringUtils.isBlank(tableAlias) ? "" : " as '" + tableAlias + "'";
        return tableStr.concat(tableAliasStr);
    }
}
