package com.quinbay.finance.service;

import com.quinbay.finance.api.BillService;
import com.quinbay.finance.model.EmailDetails;
import com.quinbay.finance.model.Transaction;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.text.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class BillServiceImpl implements BillService{
    static double disc;
    Document document = new Document();
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    @Autowired
    private Emailservice emailService;

    @Override
    public String generatePdf(Transaction t){
        String file_name = "/Users/harish/Desktop/Team task/" + t.getTxncode()+ ".pdf";
        try {

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file_name));
            document.open();
            addMetaData(document);
            addContent(document, t);
            System.out.println("Your bill no. is " + t.getTxncode());
            System.out.println("Bill invoice can be found at " + file_name);
            document.close();
            EmailDetails ed = new EmailDetails();
            ed.setRecipient("vasunthrat@gmail.com");
            ed.setSubject("intern task");
            ed.setMsgBody("Hello \n\n welocme to gmail!!!");
            ed.setAttachment(file_name);

            emailService.sendMailWithAttachment(ed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "PDF generated";
    }

    private static void addMetaData(Document document) {
        document.addTitle("Invoice");
        document.addSubject("for product management");
        document.addKeywords("Price, Stock, Product");
        document.addAuthor("Harish, Vasunthra, Archana");
        document.addCreator("Harish, Vasunthra, Archan");
    }

    private static void addContent(Document document, Transaction t) throws DocumentException {

        Chapter catPart = new Chapter(new Paragraph(t.getBuyertype()+ " Invoice"), 1);

        Paragraph p = new Paragraph();
        Date date = new Date();
        p.add(new Paragraph("Invoice date: " + date, smallBold));
        p.setAlignment(Element.ALIGN_RIGHT);
        catPart.addAll(p);

        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 2);
        catPart.addAll(paragraph);

        Paragraph bill_to = new Paragraph("Bill to: " + t.getBuyercode() + "\n" + "Transaction code: " + t.getTxncode());

        catPart.addAll(bill_to);

        if(t.getProductqty() < 10) disc = 0;
        else if(t.getProductqty() < 50) disc=0.1;
        else if(t.getProductqty() < 100) disc=0.2;
        else disc=0.3;

        createTable(catPart, t);

        Paragraph gap = new Paragraph();
        addEmptyLine(gap, 3);
        catPart.addAll(gap);

        Paragraph thanks = new Paragraph("------------------------------------------------Thank you for purchasing!------------------------------------------------");
        thanks.setAlignment(Element.ALIGN_RIGHT);
        catPart.addAll(thanks);

        document.add(catPart);
    }

    private static void createTable(Section subCatPart, Transaction t) throws BadElementException {

        double sub_total = 0;
        double total = 0;

        PdfPTable table = new PdfPTable(5);

        PdfPCell c1 = new PdfPCell(new Phrase("S. No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Product Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Amount"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        int s_no = 1;
        table.addCell(Integer.toString(s_no++));
        table.addCell(t.getProductcode());
        table.addCell(Integer.toString(t.getProductqty()));
        table.addCell(Float.toString(t.getProductprice()));
        table.addCell(Float.toString(t.getProductprice() * t.getProductqty()));
        sub_total += t.getProductprice() * t.getProductqty();

        double gst = 18*sub_total/100;

        table.addCell("-");
        table.addCell("-");
        table.addCell("-");
        table.addCell("-");
        table.addCell("-");

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("Sub Total");
        table.addCell(Double.toString(sub_total));

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("Discount @ " + disc*100 + "%");
        table.addCell(Double.toString(sub_total*disc));

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("GST @ " + 18 + "%");
        String gst_val = String.format("%.02f", gst);
        table.addCell(gst_val);

        table.addCell("-");
        table.addCell("-");
        table.addCell("-");
        table.addCell("-");
        table.addCell("-");

        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("TOTAL");
        total = sub_total + gst - sub_total*disc;
        String total_val = String.format("%.02f", total);
        table.addCell(total_val);

        subCatPart.add(table);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}
