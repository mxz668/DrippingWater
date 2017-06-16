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
    public static int  NUM=5000;
    public static String title="";

    /**
     * 导出Excel的方法
     * @throws Exception
     */
    public void exportExcel() throws Exception{
        OutputStream out = new FileOutputStream("C://ws2.zip");
        File zip = new File("C://ws.zip");// 压缩文件

        int n=2;

        List<String> fileNames = new ArrayList();// 用于存放生成的文件名称s
        //文件流用于转存文件

        for (int j = 0; j < n; j++) {
            // 声明一个工作薄
            Workbook workbook = new HSSFWorkbook();
            // 生成一个表格
            HSSFSheet sheet = (HSSFSheet) workbook.createSheet(title);
            // 设置表格默认列宽度为18个字节
            sheet.setDefaultColumnWidth((short)18);


            String file = "C://" +j+ ".xls";
            fileNames.add(file);

            FileOutputStream o = new FileOutputStream(file);

            workbook.write(o);
            File srcfile[] = new File[fileNames.size()];
            for (int i = 0, n1 = fileNames.size(); i < n1; i++) {
                srcfile[i] = new File(fileNames.get(i));
            }
            ZipFiles(srcfile, zip);
            FileInputStream inStream = new FileInputStream(zip);
            byte[] buf = new byte[4096];
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                out.write(buf, 0, readLength);
            }
            inStream.close();
        }
    }
    //获取文件名字
    public static String getFileName(){
        // 文件名获取
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String f = title + format.format(date);
        return f;
    }
    //压缩文件
    public static void ZipFiles(java.io.File[] srcfile, java.io.File zipfile) {
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                FileInputStream in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        try{
            new ExportToExcelUtil().exportExcel();
        }catch (Exception e){

        }
    }
}