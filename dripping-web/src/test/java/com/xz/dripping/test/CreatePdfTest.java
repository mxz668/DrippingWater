package com.xz.dripping.test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CSS;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.xz.dripping.common.utils.DateUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by MABIAO on 2017/6/8 0008.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RestController
@RequestMapping("createPdfTest")
public class CreatePdfTest {

    @RequestMapping(value = "createPdf",method = RequestMethod.GET)
    public void stringToPdf(){
        Document document = new Document(PageSize.LETTER);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("c://stringToPdf.pdf"));

            BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);

            Font font = new Font(baseFont);
            // step 4
            document.open();
            String str = "Spring Cloud主要有以下特点：\n" +
                    "1、 是一套完整的分布式系统解决方案，它的子项目涵盖了所有实现布式系统所需要的基础软件设施\n" +
                    "2.、基于Spring Boot, 高度集成，使得开发部署极其简单(加依赖，加注解，就能运行了)\n";
            document.add(new Paragraph(str, font));

        }catch (Exception e){
            e.printStackTrace();
        }
        document.close();
    }

    public void html2Pdf() throws IOException, DocumentException {
        String filePath = "c://testpdf2.pdf";
        // step 1
        Document document = new Document(PageSize.LETTER);
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream("c://test.html"), Charset.forName("UTF-8"));
        // step 5
        document.close();
    }

    public void parseHtml5() throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c://testpdf2.pdf"));
        // step 3
        document.open();
        // step 4
        String str = "<html>\n" +
                "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head>\n" +
                "<body>\n" +
                "<p>\n" +
                "    <span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\">也有另一种方法就是直接在</span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\">也有另一种方法就是直接在</span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\">也有另一种方法就是直接在</span></span></span>\n" +
                "</p>\n" +
                "<p>\n" +
                "    <span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\"><span style=\"font-family: MS Mincho; font-size: 14px; background-color: rgb(255, 255, 255);\">也有另一种方法就是直接在</span></span></span></span>\n" +
                "</p>\n" +
                "</body>\n" +
                "</html>";

        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        InputStream is = new ByteArrayInputStream(str.getBytes("UTF-8"));
        worker.parseXHtml(writer, document, is, Charset.forName("UTF-8"));

//        String CSS = "tr { text-align: center; } th { background-color: lightgreen; padding: 3px; } " +
//                "td {background-color: lightblue;  padding: 3px; }";
//        CSSResolver cssResolver = new StyleAttrCSSResolver();
//        CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(CSS.getBytes()));
//        cssResolver.addCss(cssFile);
//
//        // HTML
//        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
//        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
//
//        // Pipelines
//        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
//        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
//        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
//
//        // XML Worker
//        XMLWorker worker = new XMLWorker(css, true);
//        XMLParser p = new XMLParser(worker);
//        p.parse(new ByteArrayInputStream(str.getBytes()));
        // step 5
        document.close();
    }

    public void createTablePdf() throws IOException, DocumentException {
        String CSS = "tr { text-align: center; } th { background-color: lightgreen; padding: 3px; } " +
                "td {background-color: lightblue;  padding: 3px; }";
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c://testpdf2.pdf"));
        // step 3
        document.open();
        // step 4
        StringBuilder sb = new StringBuilder();
        sb.append("<table border=\"2\">");
        sb.append("<tr>");
        sb.append("<th>Sr. No.</th>");
        sb.append("<th>Text Data</th>");
        sb.append("<th>Number Data</th>");
        sb.append("</tr>");
        for (int i = 0; i < 10; ) {
            i++;
            sb.append("<tr>");
            sb.append("<td>");
            sb.append(i);
            sb.append("</td>");
            sb.append("<td>This is text data ");
            sb.append(i);
            sb.append("</td>");
            sb.append("<td>");
            sb.append(i);
            sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");

        CSSResolver cssResolver = new StyleAttrCSSResolver();
//        CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(CSS.getBytes()));
//        cssResolver.addCss(cssFile);

        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(sb.toString().getBytes()));

        // step 5
        document.close();
    }

    public  void htmlToPdf (){
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("c://htmlToPdf.pdf"));

//            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//
//            Font font = new Font(baseFont);
            // step 4
            document.open();
            Image tImgCover = Image.getInstance("D:\\Workspaces\\Main\\DrippingWater\\dripping-web\\src\\test\\java\\com\\xz\\dripping\\test\\tt.jpg");
            /* 设置图片的位置 */
            tImgCover.setAbsolutePosition(0, 500);
            /* 设置图片的大小 */
            tImgCover.scaleAbsolute(600, 130);
            document.add(tImgCover);
            String str = "<html>\n" +
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
            Velocity.init();
            VelocityContext context = new VelocityContext();

            context.put("name", "李赵四");
            context.put("age", "26");
            context.put("now", DateUtils.format(new Date(), "yyyy-mm-dd"));

            StringWriter stringWriter = new StringWriter();
            Velocity.evaluate(context, stringWriter, "test", str);
            String toPdf = stringWriter.toString();
            InputStream is = new ByteArrayInputStream(toPdf.getBytes("UTF-8"));
//            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
//            worker.parseXHtml(writer, document, is, Charset.forName("UTF-8"),new MyFont());//中文显示，部分字体不支持

//            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
//            worker.parseXHtml(writer, document, is, Charset.forName("UTF-8"));//部分中文不支持
            String FONT = "D:\\Workspaces\\Main\\DrippingWater\\dripping-web\\src\\test\\resources\\fonts\\FreeSans.ttf";
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register(FONT);
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    is, null, Charset.forName("UTF-8"), fontImp);
            document.close();
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

    public static void main (String args[]){
        try {
//            File file = new File("c://testpdf2.pdf");
//            file.getParentFile().mkdirs();
//            new CreatePdfTest().stringToPdf();
            new CreatePdfTest().htmlToPdf();
        }catch (Exception e){

        }
    }

    class MyFont extends FontFactoryImp {
        com.itextpdf.text.pdf.BaseFont baseFont = null;

        public MyFont() {
            try {
                baseFont = com.itextpdf.text.pdf.BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", com.itextpdf.text.pdf.BaseFont.NOT_EMBEDDED);
            } catch (com.itextpdf.text.DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public com.itextpdf.text.Font getFont(String fontName, String encoding, boolean embedded, float size, int style, BaseColor color, boolean cached) {
            return new com.itextpdf.text.Font(baseFont, 9, com.itextpdf.text.Font.NORMAL);
        }
    }
}
