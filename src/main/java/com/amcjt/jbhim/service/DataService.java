package com.amcjt.jbhim.service;

import com.amcjt.jbhim.repository.mybatis.entity.Column;
import com.amcjt.jbhim.repository.mybatis.entity.Table;
import com.amcjt.jbhim.repository.mybatis.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataService {
    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private ExcelService excelService;

    public ByteArrayOutputStream getTableData(String tableId) throws IOException {
        Table table = tableMapper.findTableById(tableId);
        if (table != null) {
            List<Column> columnById = tableMapper.findColumnById(tableId);
            table.getColumns().addAll(columnById);
        }
        List<Map<String, String>> titleList = new ArrayList<>();
        buildTitle(titleList);
        List<Map<String, Object>> content = new ArrayList<>();
        assert table != null;
        buildContent(content, table);
        return excelService.createExcel(table.getName(), titleList, content);
    }

    private void buildContent(List<Map<String, Object>> content, Table table) {
        table.getColumns().forEach(column -> {
            Map<String, Object> map = new HashMap<>();
            map.put("tableId", table.getId());
            map.put("name", table.getName());
            map.put("tableName", table.getTableName());
            map.put("columnId", column.getId());
            map.put("columnName", column.getName());
            map.put("columnRealName", column.getColumnName());
            map.put("columnType", column.getColumnType().getSqlType());
            content.add(map);
        });

    }

    private void buildTitle(List<Map<String, String>> titleList) {
        titleList.add(putMap("tableId", "表id"));
        titleList.add(putMap("name", "数据库名"));
        titleList.add(putMap("tableName", "表名"));
        titleList.add(putMap("columnId", "列id"));
        titleList.add(putMap("columnName", "列名"));
        titleList.add(putMap("columnRealName", "列字段名"));
        titleList.add(putMap("columnType", "字段类型"));
    }

    private Map<String, String> putMap(String key, String value) {
        Map<String, String> map = new HashMap<>();
        map.put("field", key);
        map.put("title", value);
        return map;
    }

}
