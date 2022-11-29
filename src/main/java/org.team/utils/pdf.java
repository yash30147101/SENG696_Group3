pack  org.team.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import org.team.models.FacultyNotes;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class PDF {


    public static ByteArrayInputStream employeePDFReport
            (List<FacultyNotes> FacultyNotess) throws FileNotFoundException, MalformedURLException {

        FacultyNotes FacultyNotes = Collections.max(FacultyNotess, Comparator.comparing(FacultyNotes::getCreatedDate));
        String dest = "PDFs/sample.pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A4);
        pdfDoc.addNewPage();
        Document document = new Document(pdfDoc);

        float col = 280f;
        float col_width[] = {col, col, col};

        Table table = new Table(col_width);
        table.setBackgroundColor(new DeviceRgb(63, 169, 219));
        table.setFontColor(Color.WHITE);
        String imageFile = "src/main/resources/static/images/efka-logotypo.png";
        ImageData data = ImageDataFactory.create(imageFile);
        Im img = new Image(data);
        table.addCell(img.setAutoScale(true).setWidth(100f).setHeight(120f));
        table.addCell(new Cell().add("FACULTY REPORT").setBold().setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE).setMarginTop(30f).setMarginBottom(30f)
                .setBorder(Border.NO_BORDER).setFontSize(35f));
        table.addCell(new Cell().add("Group D\nSENL 696\nAgent-Based Software Engineering\nUniversity of Calbary")
                .setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setMarginTop(30f).setMarginBottom(30f).setMarginRight(10f)
                .setBorder(Border.NO_BORDER));
        document.add(table);


        Paragraph name = new Paragraph("\n" + "student Name : " + FacultyNotes.getstudent().getFirstName());
        document.add(name);
        Paragraph studentID = new Paragraph("studentID : " + FacultyNotes.getstudent().getId());
        document.add(studentID);
        Paragraph emailID = new Paragraph("EmailID : " + FacultyNotes.getstudent().getEmail());
        document.add(emailID);
        Paragraph contact = new Paragraph("Contact : " + FacultyNotes.getstudent().getPhone());
        document.add(contact);
        Paragraph facultyName = new Paragraph("Faculty Name : " + FacultyNotes.getfaculty().getFirstName());
        document.add(facultyName);


        StringTokenizer strtok = new StringTokenizer(FacultyNotes.getFacultyNotes(), ".");
        String updatedFacultyNotes = new String();
        while (strtok.hasMoreTokens()) {
            String temp = (Character.toString('\u2022') + " " + strtok.nextToken().trim() + "\n");
            updatedFacultyNotes = updatedFacultyNotes + (temp);
        }
        Paragraph response = new Paragraph("\n" + "Faculty's Note : " + "\n" + updatedFacultyNotes);
        document.add(response);


        String imageStamp = "static/images/authoried.jpg";
        ImageData authorizedStamp = ImageDataFactory.create(imageStamp);
        Im imgSign = new Image(authorizedStamp);
        imgSign.setFixedPosition(440, 100);
        document.add(imgSign);
        ;

        document.close();
        pdfDoc.close();
        System.out.println("PDF Created");
        return null;
    }

}
