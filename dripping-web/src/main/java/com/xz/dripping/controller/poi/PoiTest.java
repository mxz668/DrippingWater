package com.xz.dripping.controller.poi;

import com.xz.dripping.common.utils.DateUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * POI 操作office
 * Created by MABIAO on 2017/6/13 0013.
 */
public class PoiTest {

    public void createWord() throws Exception{
        XwpfTUtil xwpfTUtil = new XwpfTUtil();

        XWPFDocument doc;
        String fileResource = "D:\\Workspaces\\Main\\DrippingWater\\dripping-web\\src\\main\\resources\\temps\\test.docx";
        InputStream is;
        is = new FileInputStream(fileResource);
//        is = getClass().getClassLoader().getResourceAsStream(fileNameInResource);
        doc = new XWPFDocument(is);

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("${legalName}","贺超宇");
        params.put("${companyName}","云南汇百金经贸有限公司");
        params.put("${date}", DateUtils.format(new Date(), "yyyy年MM月dd日"));

        xwpfTUtil.replaceInPara(doc, params);
        //替换表格里面的变量
//        xwpfTUtil.replaceInTable(doc, params);
        OutputStream os = new FileOutputStream("C://poi.docx");
//        OutputStream os = response.getOutputStream();

//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-disposition","attachment;filename="+"123"+".docx");

        doc.write(os);

        xwpfTUtil.close(os);
        xwpfTUtil.close(is);

        os.flush();
        os.close();
    }

    public static void main(String args[]){
        try{
            new PoiTest().createWord();
        }catch (Exception e){

        }

    }
}
