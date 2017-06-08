package com.xz.dripping.controller.itext;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringReader;

/**
 * Created by MABIAO on 2017/6/8 0008.
 */
@RestController
@RequestMapping(value = "createPdfService")
public class CreatePdfController {

    @RequestMapping(value = "createPdf",method = RequestMethod.GET)
    public void createPdf(String fileName){
        // step 1
        Document document = new Document(PageSize.A4);
        // step 2
        try {
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.addTitle("ID.NET");
            document.addAuthor("dotuian");
            document.addSubject("This is the subject of the PDF file.");
            document.addKeywords("This is the keyword of the PDF file.");

            // step 3
            document.open();
            // step 4
            document.add(new Paragraph("Hello World!"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            // step 5
            document.close();
        }
    }
}
