package com.amcjt.jbhim.utils;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class WordUtil {

    public void readWord(InputStream inputStream) throws IOException {
        HWPFDocument document = new HWPFDocument(inputStream);
        XWPFDocument docx = new XWPFDocument();
        Iterator<XWPFParagraph> paragraphIterator = docx.getParagraphsIterator();
        while (paragraphIterator.hasNext()) {
            XWPFParagraph next = paragraphIterator.next();
            List<XWPFRun> runs = next.getRuns();
            XWPFRun xwpfRun = runs.get(0);
            int textPosition = xwpfRun.getTextPosition();
            for (int i = 0; i < textPosition; i++) {
                xwpfRun.getText(i);
            }
        }
    }

}
