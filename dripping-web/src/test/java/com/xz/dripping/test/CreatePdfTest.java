package com.xz.dripping.test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by MABIAO on 2017/6/8 0008.
 */
public class CreatePdfTest {

    public void createPdf(){
        Document document = new Document(PageSize.LETTER);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("c://testpdf2.pdf"));

            BaseFont baseFont = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);

            Font font = new Font(baseFont);
            // step 4
            document.open();
            FileInputStream fis = new FileInputStream("c://pdf.html");
            StringBuffer html = new StringBuffer();
            int len = 0;
            byte[] buf = new byte[4096];
            while((len=fis.read(buf)) != -1){
                html.append(new String(buf,0,len));
            }
            document.add(new Paragraph(html.toString(), font));

        }catch (Exception e){
            e.printStackTrace();;
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
        String str = "<html><head></head><body style=\"font-size:12.0pt; font-family:Times New Roman\">"+
                "<a href='http://www.rgagnon.com/howto.html'><b>Real's HowTo</b></a>" +
                "<h1>Show your support</h1>" +
                "<p>It DOES cost a lot to produce this site - in ISP storage and transfer fees</p>" +
                "<p>TEST POLSKICH ZNAKÓW: \u0104\u0105\u0106\u0107\u00d3\u00f3\u0141\u0142\u0179\u017a\u017b\u017c\u017d\u017e\u0118\u0119</p>" +
                "<hr/>" +
                "<p>the huge amounts of time it takes for one person to design and write the actual content.</p>" +
                "<p>If you feel that effort has been useful to you, perhaps you will consider giving something back?</p>" +
                "<p>Donate using PayPal\u017d</p>" +
                "<p>Contributions via PayPal are accepted in any amount</p>" +
                "<p><br/><table border='1'><tr><td>Java HowTo</td></tr><tr>" +
                "<td style='background-color:red;'>Javascript HowTo</td></tr>" +
                "<tr><td>Powerbuilder HowTo</td></tr></table></p>" +
                "</body></html>";

        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
        InputStream is = new ByteArrayInputStream(str.getBytes("UTF-8"));
        worker.parseXHtml(writer, document, is, Charset.forName("UTF-8"), new XMLWorkerFontProvider("resources/fonts/"));
        // step 5
        document.close();
    }

    public static void main (String args[]){
        try {
//            File file = new File("c://testpdf2.pdf");
//            file.getParentFile().mkdirs();
            new CreatePdfTest().parseHtml5();
        }catch (Exception e){

        }
    }
}
