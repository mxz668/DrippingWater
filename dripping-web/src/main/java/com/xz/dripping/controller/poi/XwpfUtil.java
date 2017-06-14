package com.xz.dripping.controller.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * POI 模板参数转换
 * Created by MABIAO on 2017/6/13 0013.
 */
public class XwpfUtil {

    /**
     * 替换段落里面的变量
     * @param doc    要替换的文档
     * @param params 参数
     */
    public static void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            if (matcher(para.getParagraphText())) {
                List<XWPFRun> runs = para.getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    String paraString = runs.get(i).getText(runs.get(i).getTextPosition());
                    for (String key : params.keySet()) {
                        paraString = paraString.replace(key, params.get(key).toString());
                    }
                    runs.get(i).setText(paraString, 0);
                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     * @param doc    要替换的文档
     * @param params 参数
     */
    public static void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        while (iterator.hasNext()) {
            XWPFTable table = iterator.next();
            int rcount = table.getNumberOfRows();
            for (int i = 0; i < rcount; i++) {
                XWPFTableRow row = table.getRow(i);
                List<XWPFTableCell> cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    String cellTextString = cell.getText();
                    if (matcher(cellTextString)){
                        for (String key : params.keySet()) {
                            if (cellTextString.contains(key))
                                cellTextString = cellTextString.replace(key, params.get(key).toString());
                        }
                        cell.removeParagraph(0);
                        cell.setText(cellTextString);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private static boolean matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

}
