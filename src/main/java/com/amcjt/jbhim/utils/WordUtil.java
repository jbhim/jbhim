package com.amcjt.jbhim.utils;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class WordUtil {

    public static InputStream getInputStream(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }

    public void readWordDocX(InputStream inputStream) throws IOException {
        try {
            XWPFDocument docx = new XWPFDocument(inputStream);
            //获取所有段落
            List<XWPFParagraph> paragraphs = docx.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                dealParagraph(paragraph);
            }
            //table
            List<XWPFTable> tables = docx.getTables();
            for (XWPFTable table : tables) {
                List<XWPFTableRow> rows = table.getRows();
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> tableCells = row.getTableCells();
                    for (XWPFTableCell tableCell : tableCells) {
                        for (XWPFParagraph paragraph : tableCell.getParagraphs()) {
                            dealParagraph(paragraph);
                        }
                    }
                }
            }
            FileOutputStream fileOutputStream = new FileOutputStream("D:\\develop\\work\\jbhim\\public\\files\\test.docx");
            docx.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } finally {
            inputStream.close();

        }
    }

    private void dealParagraph(XWPFParagraph paragraph) {
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            String fontFamily = run.getFontFamily();
            int fontSize = run.getFontSize();
            String text = run.getText(run.getTextPosition());
            System.out.println(text + String.format(" 字体：%s , %d", fontFamily, fontSize));
            String replaceAll = text.replaceAll("@(.*?)@", "替换文本");
            run.setText(replaceAll, 0);
        }
    }

    public static void main(String[] args) throws IOException {
        WordUtil wordUtil = new WordUtil();
        File file = new File("D:\\develop\\work\\jbhim\\public\\files\\一鉴安心鉴定报告（车况较差版）1009t2.docx");
        wordUtil.readWordDocX(WordUtil.getInputStream(file));
    }

}
