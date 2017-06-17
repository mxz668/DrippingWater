package com.xz.dripping.controller.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 2015-4-29
 * DES:POI导出Excel
 * author:JiBaoLe
 */
public class ExportToExcelUtil<T> {
    //每次设置导出数量
    public static String title="12";

    /**
     * 导出Excel的方法
     * @throws Exception
     */
    public void exportExcel() throws Exception{
        OutputStream out = new FileOutputStream("C://ws2.zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(out);

        for (int j = 0; j < 2; j++) {
            // 声明一个工作薄
            Workbook workbook = new HSSFWorkbook();
            // 生成一个表格
            HSSFSheet sheet = (HSSFSheet) workbook.createSheet(title);
            // 设置表格默认列宽度为18个字节
            sheet.setDefaultColumnWidth((short)18);

            ZipEntry entry = new ZipEntry("name" + j + ".xls");
            zipOutputStream.putNextEntry(entry);
            workbook.write(zipOutputStream);
        }
        zipOutputStream.flush();
        zipOutputStream.close();
    }
    //获取文件名字
    public static String getFileName(){
        // 文件名获取
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String f = title + format.format(date);
        return f;
    }

    public static void main(String args[]){
        try{
            new ExportToExcelUtil().exportExcel();
        }catch (Exception e){

        }
    }
}