package com.xz.dripping.controller.itext;

import com.aliyun.mns.common.utils.DateUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.xz.dripping.common.utils.DateUtils;
import com.xz.dripping.common.utils.VelocityFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by MABIAO on 2017/6/8 0008.
 */
@RestController
@RequestMapping(value = "createPdfService")
public class CreatePdfController {

    @Autowired
    private VelocityFactory velocityFactory;

    @RequestMapping(value = "createPdf", method = RequestMethod.GET)
    public void createPdf(HttpServletRequest request,HttpServletResponse response) {
        Document document = new Document();
        try {
            OutputStream os = response.getOutputStream();
            response.setContentType("application/pdf");
            PdfWriter writer = PdfWriter.getInstance(document, os);

            document.open();
            Image tImgCover = Image.getInstance("D:\\Workspaces\\Main\\DrippingWater\\dripping-web\\src\\test\\java\\com\\xz\\dripping\\test\\tt.jpg");
            /* 设置图片的位置 */
            tImgCover.setAbsolutePosition(0, 400);
            /* 设置图片的大小 */
            tImgCover.scaleAbsolute(600, 130);
            document.add(tImgCover);
            String str = "<html>\n" +
                    "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head>\n" +
                    "<body background-img=\"http://bpic.588ku.com/back_pic/00/02/34/8356167c25bc7e6.jpg\">\n" +
                    "<p>\n" +
                    "    <span style=\"font-family: MS Mincho; font-size: 20px; background-color: rgb(255, 255, 255);\">姓名：${name}</span>\n" +
                    "</p>\n" +
                    "<p>\n" +
                    "    <span style=\"font-family: 宋体; font-size: 14px; lightyellow;\">年龄：${age}</span>\n" +
                    "</p>\n" +
                    "<p>\n" +
                    "    <span style=\"font-family: MS Mincho; font-size: 14px;background-color: lightblue;\">也有另一种方法就是直接在</span>\n" +
                    "</p>\n" +
                    "<p style=\"text-align:right;\">\n" +
                    "    <span style=\"font-family: 微软雅黑; font-size: 14px; background-color: lightgreen;\">日期：${now}</span>\n" +
                    "</p>\n" +
                    "</body>\n" +
                    "</html>";
            VelocityContext context = velocityFactory.getContext();

            context.put("name", "张三");
            context.put("age", "26");
            context.put("now", DateUtils.format(new Date(), "yyyy-mm-dd"));

            StringWriter stringWriter = new StringWriter();
            Velocity.evaluate(context, stringWriter, "test", str);
//            document.add(new Paragraph(stringWriter.toString(), font));
            String toPdf = stringWriter.toString();
            InputStream is = new ByteArrayInputStream(toPdf.getBytes("UTF-8"));
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            worker.parseXHtml(writer, document, is, Charset.forName("UTF-8"));
            document.close();
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // step 5

        }
    }

}
