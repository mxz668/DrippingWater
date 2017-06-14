package com.xz.dripping.controller.poi;

import com.xz.dripping.common.utils.DateUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MABIAO on 2017/6/13 0013.
 */
@RestController
@RequestMapping(value = "poiService")
public class PoiController {

    @RequestMapping(value = "createWord", method = RequestMethod.GET)
    public void createWord(HttpServletRequest request,HttpServletResponse response) {
        try{
            XwpfUtil xwpfTUtil = new XwpfUtil();

            XWPFDocument doc;
            InputStream is = getClass().getClassLoader().getResourceAsStream("temps\\test.docx");
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
            OutputStream os = response.getOutputStream();

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition","attachment;filename="+"123"+".docx");

            doc.write(os);
            is.close();

            os.flush();
            os.close();
        }catch (Exception e){

        }
    }
}
