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

    /**
     * 替换word指定参数
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
    }

    public static void main(String args[]){
        try{
            new PoiTest().createWord();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
