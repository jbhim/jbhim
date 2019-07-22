package com.amcjt.jbhim.service;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {
    /**
     * 创建 excel
     *
     * @param sheetName   表格名
     * @param titleList   表头集合
     * @param contentList 数据集合
     */
    public ByteArrayOutputStream createExcel(String sheetName,
                                             List<Map<String, String>> titleList,
                                             List<Map<String, Object>> contentList) throws IOException {
        // 创建一个webbook，对应一个Excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // 在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = hssfWorkbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(20);
        // 创建单元格，并设置值表头 设置表头居中
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = getHssfCellStyle(hssfWorkbook);
        List<String> fieldList = new ArrayList<>();
        //生成格式化标题
        writeTitles(titleList, row, style, fieldList);
        // 表格的行号偏移量 因为可能多插入几行
        int Offset = 0;
        // 生成内容
        for (int i = 0; i < contentList.size(); i++) {
            row = sheet.createRow(i + 1 + Offset);
            Map<String, Object> map = contentList.get(i);
            writeRow(row, style, fieldList, map);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        hssfWorkbook.write(baos);
        return baos;
    }

    private HSSFCellStyle getHssfCellStyle(HSSFWorkbook hssfWorkbook) {
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置字体
        HSSFFont font = hssfWorkbook.createFont();
        font.setBold(false);
        style.setFont(font);
        return style;
    }

    private void writeTitles(List<Map<String, String>> titleList, HSSFRow row, HSSFCellStyle style, List<String> fieldList) {
        for (int i = 0; i < titleList.size(); i++) {
            String title = titleList.get(i).get("title");
            fieldList.add(titleList.get(i).get("field"));
            HSSFCell cell = row.createCell((short) i);
            cell.setCellValue(title);
            cell.setCellStyle(style);
        }
    }

    private void initField(List<Map<String, String>> titleList, List<String> fieldList) {
        for (int i = 0; i < titleList.size(); i++) {
            String title = titleList.get(i).get("title");
            // 将 field 放入有顺序的 List 中
            fieldList.add(titleList.get(i).get("field"));
        }
    }

    private void writeRow(HSSFRow row, HSSFCellStyle style, List<String> fieldList, Map<String, Object> map) {
        for (int j = 0; j < fieldList.size(); j++) {
            Object val = map.get(fieldList.get(j));
            if (val != null) {
                HSSFCell cell = row.createCell((short) j);
                cell.setCellValue(val.toString());
                cell.setCellStyle(style);
            }
        }
    }
}
