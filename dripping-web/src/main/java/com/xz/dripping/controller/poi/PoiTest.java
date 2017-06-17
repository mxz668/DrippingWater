package com.xz.dripping.controller.poi;

import com.xz.dripping.common.utils.DateUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * POI 操作office
 * Created by MABIAO on 2017/6/13 0013.
 */
public class PoiTest {

    /**
     * 替换word指定参数并生成word文档
     * @throws Exception
     */
    public void createWord() throws Exception{

        XWPFDocument doc;
        String fileResource = "D:\\Workspaces\\Main\\DrippingWater\\dripping-web\\src\\main\\resources\\temps\\test.docx";
        InputStream is = new FileInputStream(fileResource);
        doc = new XWPFDocument(is);

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("${legalName}","贺超宇");
        params.put("${age}","25");
        params.put("${companyName}","云南汇百金经贸有限公司");
        params.put("${date}", DateUtils.format(new Date(), "yyyy年MM月dd日"));

        //替换段落里的参数
        XwpfUtil.replaceInPara(doc, params);
        //替换表格里的参数
        XwpfUtil.replaceInTable(doc, params);
        OutputStream os = new FileOutputStream("C://poi.docx");
        doc.write(os);
        is.close();

        os.flush();
        os.close();
        is.close();
    }

    /**
     * 替换word指定参数并压缩多个word文档成Zip
     * @throws Exception
     */
    public void createWord2Zip() throws Exception{

        OutputStream out = new FileOutputStream("C://words.zip");
        ZipOutputStream zipOut = new ZipOutputStream(out);

        for (int i=0;i < 3;i++){
            XWPFDocument doc;
            String fileResource = "D:\\Workspaces\\Main\\DrippingWater\\dripping-web\\src\\main\\resources\\temps\\test.docx";
            InputStream is = new FileInputStream(fileResource);
            doc = new XWPFDocument(is);

            Map<String,Object> params = new HashMap<String,Object>();
            params.put("${legalName}","贺超宇");
            params.put("${age}","25");
            params.put("${companyName}","云南汇百金经贸有限公司");
            params.put("${date}", DateUtils.format(new Date(), "yyyy年MM月dd日"));

            //替换段落里的参数
            XwpfUtil.replaceInPara(doc, params);
            //替换表格里的参数
            XwpfUtil.replaceInTable(doc, params);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            doc.write(outputStream);

            ZipEntry entry = new ZipEntry(getFileName()+ ".docx");
            zipOut.putNextEntry(entry);
            zipOut.write(outputStream.toByteArray());

            is.close();
        }
        zipOut.flush();
        zipOut.close();
    }

    //获取文件名字
    public static String getFileName(){
        // 文件名获取
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
        String f = format.format(date);
        return f;
    }

    public void html2Word(){
        String str = "<html>" +
                "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head>\n" +
                "<body>\n" +
                "<p>\n" +
                "    <span style=\"font-family: 微软雅黑; font-size: 20px; background-color: rgb(255, 255, 255);\">姓名：${name}</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family: 微软雅黑; font-size: 14px; lightyellow;color:red;\">年龄：${age}</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family: 微软雅黑; font-size: 14px;background-color: lightblue;\">也有另一种方法就是直接在</span>\n" +
                "</p>\n" +
                "<p style=\"text-align:right;margin-top:200px;\">\n" +
                "    <span style=\"font-family: 微软雅黑; font-size: 14px; background-color: lightgreen;\">日期：${now}</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family: 微软雅黑; margin-top:10000px;background-color: lightblue;\">也有另一种方法就是直接在</span>\n" +
                "</p>\n" +
                "</body>\n" +
                "</html>";

        byte b[] = str.getBytes();
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
            FileOutputStream ostream = new FileOutputStream("C://html2word.doc");
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
        }catch (Exception e){

        }

    }

    public static void main(String args[]){
        try{
            new PoiTest().createWord();
//            new PoiTest().html2Word();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
