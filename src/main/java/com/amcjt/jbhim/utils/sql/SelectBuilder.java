package com.amcjt.jbhim.utils.sql;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SelectBuilder extends SqlBuilder {

    private String sql;
    private String selectItem = "select ";
    private String from = " from ";
    private String where = "";
    private String limit;
    private List<ColumnInfo> columnInfos = new ArrayList<>();
    private List<TableInfo> mainTables = new ArrayList<>();
    private List<WhereInfo> whereList = new ArrayList<>();

    public static SelectBuilder create() {
        return new SelectBuilder();
    }


    public SelectBuilder select(List<ColumnInfo> columnInfoList) {
        columnInfos.addAll(columnInfoList);
        return this;
    }

    public SelectBuilder select(@NotNull String column, String columnAlias, String tableAlias) {
        ColumnInfo columnInfo = new ColumnInfo(column, columnAlias, tableAlias);
        columnInfos.add(columnInfo);
        return this;
    }

    public SelectBuilder select(String column, String columnAlias) {
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setColumn(column);
        columnInfo.setColumn(columnAlias);
        columnInfos.add(columnInfo);
        return this;
    }

    public SelectBuilder select(String column) {
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setColumn(column);
        columnInfos.add(columnInfo);
        return this;
    }

    public SelectBuilder from(String table) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTable(table);
        this.mainTables.add(tableInfo);
        return this;
    }

    public SelectBuilder from(String table, String tableAlias) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTable(table);
        tableInfo.setTableAlias(tableAlias);
        this.mainTables.add(tableInfo);
        return this;
    }

    public SelectBuilder from(TableInfo tableInfo) {
        this.mainTables.add(tableInfo);
        return this;
    }

    public SelectBuilder from(List<TableInfo> tableInfoList) {
        this.mainTables = tableInfoList;
        return this;
    }

    public SelectBuilder limit(long start, long size) {
        this.limit = String.format(" limit %s, %s", start, size);
        return this;
    }

    @Override
    public String build() {
        verification();
        buildSelect();
        buildFrom();
        buildWhere();
        buildSql();
        return sql;
    }

    private void buildWhere() {
        this.where = String.join(" ", this.whereList.stream().map(WhereInfo::toString).collect(Collectors.toList()));
    }

    private void buildSql() {
        this.sql = selectItem.concat(from);
        if (StringUtils.isNotBlank(where)) {
            this.sql = this.sql.concat(where);
        }
        if (StringUtils.isNotBlank(limit)) {
            this.sql = this.sql.concat(limit);
        }
    }

    private void buildFrom() {
        this.from = this.from
                + String.join(", \n", this.mainTables.stream().map(TableInfo::toString).collect(Collectors.toList()));
    }

    private void buildSelect() {
        if (columnInfos.size() == 0) {
            this.selectItem = this.selectItem + "* ";
        }
        ;
        this.selectItem = this.selectItem
                + String.join(", \n", this.columnInfos.stream().map(ColumnInfo::toString).collect(Collectors.toList()));
    }

    private void verification() {

    }

    private SelectBuilder where(String table, String column, String operator, Object value) {
        WhereInfo whereInfo = new WhereInfo("where", table, column, operator, value);
        this.whereList.add(whereInfo);
        return this;
    }

    private SelectBuilder and(String table, String column, String operator, Object value) {
        WhereInfo whereInfo = new WhereInfo("and", table, column, operator, value);
        this.whereList.add(whereInfo);
        return this;
    }

    private SelectBuilder or(String table, String column, String operator, Object value) {
        WhereInfo whereInfo = new WhereInfo("or", table, column, operator, value);
        this.whereList.add(whereInfo);
        return this;
    }


    public static void main(String[] args) {
        String build = create()
                .select("id", "id", "con")
                .select("id1", "id", "con")
                .select("id2", "id", "con")
                .select("id3", "id", "con")
                .select("id4", "id", "con")
                .select("id5", "id", "con")
                .select("id6", "id", "con")
                .select("id7", "id7", "con")
                .from("table", "con")
                .where("con", "id7", "=", "4")
                .and("con", "id1", "=", "3")
                .or("con", "id2", "=", "4")
                .limit(0, 10).build();
        System.out.println(build);
    }
}
