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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.team.models.FacultyNotes;

import java.io.*;
import java.net.MalformedURLException;
import java.util.List;
import java.util.StringTokenizer;

public class PdfUtil {
    private static Logger logger = LoggerFactory.getLogger(PdfUtil.class);

    public static ByteArrayInputStream employeePDFReport(List<FacultyNotes> employees) {
        //Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            System.out.println("Inside try block of PdfUtil !");
            FacultyNotes FacultyNotes = employees.get(0);
            String fileName = (FacultyNotes.getstudent().getFirstName() + "_" + FacultyNotes.getstudent().getLastName()) + ".pdf";
            System.out.println("Report FileName : " + fileName);
            String dest = "D:\\PDFs\\" + fileName;
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdfDoc = new PdfDocument(writer);
            pdfDoc.setDefaultPageSize(PageSize.A4);
            pdfDoc.addNewPage();
            Document document = new Document(pdfDoc);
            System.out.print("Inside PDF Util !");
            float col = 280f;
            float col_width[] = {col, col, col};

            Table table = new Table(col_width);
            table.setBackgroundColor(new DeviceRgb(63, 169, 219));
            table.setFontColor(Color.WHITE);
            String imageFile = "/home/yash30147101/Documents/SENG696/OnlineAppointments2/src/main/resources/static/images/efka-logotypo.png";
            ImageData data = ImageDataFactory.create(imageFile);
            Im img = new Image(data);
            table.addCell(img.setAutoScale(true).setWidth(100f).setHeight(120f));
            table.addCell(new Cell().add("FACULTY REPORT").setBold().setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE).setMarginTop(30f).setMarginBottom(30f)
                    .setBorder(Border.NO_BORDER).setFontSize(35f));
            table.addCell(new Cell().add("Yash Trada\nSENG 696\nAgent-Based Software Engineering\nUniversity of Calgary")
                    .setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE)
                    .setMarginTop(30f).setMarginBottom(30f).setMarginRight(10f)
                    .setBorder(Border.NO_BORDER));
            document.add(table);


            Paragraph name = new Paragraph("\n" + "Student's Name : " + FacultyNotes.getstudent().getFirstName() + " " + FacultyNotes.getstudent().getLastName());
            document.add(name);
            Paragraph studentID = new Paragraph("StudentID : " + FacultyNotes.getstudent().getId());
            document.add(studentID);
            Paragraph emailID = new Paragraph("EmailID : " + FacultyNotes.getstudent().getEmail());
            document.add(emailID);
            Paragraph contact = new Paragraph("Contact : " + FacultyNotes.getstudent().getPhone());
            document.add(contact);
            Paragraph facultyName = new Paragraph("Faculty Name : " + FacultyNotes.getfaculty().getFirstName() + " " + FacultyNotes.getfaculty().getLastName());
            document.add(facultyName);

            System.out.print("Document creation in progress !!");

            // Map the response of faculty against FacultyNotes field.
            // String FacultyNotes1 = "The student is not able to breathe properly. It has a lung infection. Please administer Medicine : Synthroid.";
            StringTokenizer strtok = new StringTokenizer(FacultyNotes.getFacultyNotes(), ".");
            String updatedFacultyNotes = new String();
            while (strtok.hasMoreTokens()) {
                String temp = (Character.toString('\u2022') + " " + strtok.nextToken().trim() + "\n");
                updatedFacultyNotes = updatedFacultyNotes + (temp);
            }
            Paragraph response = new Paragraph("\n" + "Faculty's Note : " + "\n" + updatedFacultyNotes);
            document.add(response);


            String imageStamp = "/home/yash30147101/Documents/SENG696/OnlineAppointments2/src/main/resources/static/images/efka-logotypo2.png";
            ImageData authorizedStamp = ImageDataFactory.create(imageStamp);
            Im imgSign = new Image(authorizedStamp);
            imgSign.setFixedPosition(440, 100);
            document.add(imgSign);
            document.close();
            pdfDoc.close();
            System.out.println("PDF Created !");

            File f = new File(dest);
            byte[] buf = new byte[8192];
            FileInputStream fis = new FileInputStream(f);
            int c = 0;
            while ((c = fis.read(buf, 0, buf.length)) > 0) {
                out.write(buf, 0, c);
                out.flush();
            }

            out.close();
            System.out.println("stop");
            fis.close();


//            PdfWriter.getInstance(document, out);
//            document.open();
//
//            // Add Text to PDF file ->
//            Font font = FontFactory.getFont(FontFactory.COURIER, 14,
//                    BaseColor.BLACK);
//            Paragraph para = new Paragraph("FacultyNotes", font);
//            para.setAlignment(Element.ALIGN_CENTER);
//            document.add(para);
//            document.add(Chunk.NEWLINE);
//            PdfPTable table = new PdfPTable(5);
//
//            System.out.println("Inside PdfUtil !");
//            Im img = Image.getInstance("src/main/resources/static/images/efka-logotypo.png");
//
//            img.setAbsolutePosition(500f, 120f);
//
//            document.add(img);
//
//
//            FacultyNotes FacultyNotes = employees.get(0);
//
//            Paragraph name = new Paragraph("\n" + "student Name : " + FacultyNotes.getstudent().getFirstName());
//            document.add((Element) name);
//            Paragraph studentID = new Paragraph("studentID : " + FacultyNotes.getstudent().getId());
//            document.add((Element) studentID);
//            Paragraph emailID = new Paragraph("EmailID : " + FacultyNotes.getstudent().getEmail());
//            document.add((Element) emailID);
//            Paragraph contact = new Paragraph("Contact : " + FacultyNotes.getstudent().getPhone());
//            document.add((Element) contact);
//            Paragraph facultyName = new Paragraph("Faculty Name : " + FacultyNotes.getfaculty().getFirstName());
//            document.add((Element) facultyName);
//            Paragraph FacultyNotes1 = new Paragraph("FacultyNotes : " + FacultyNotes.getFacultyNotes());
//            document.add((Element) FacultyNotes1);
////                PdfPCell idCell = new PdfPCell(new Phrase(FacultyNotes.getId().
////                        toString()));
////                idCell.setPaddingLeft(4);
////                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
////                table.addCell(idCell);
////
////                PdfPCell firstNameCell = new PdfPCell(new Phrase
////                        (FacultyNotes.getfaculty().getFirstName()));
////                firstNameCell.setPaddingLeft(4);
////                firstNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////                firstNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
////                table.addCell(firstNameCell);
////
////                PdfPCell emailCell = new PdfPCell(new Phrase
////                        (String.valueOf(FacultyNotes.getfaculty().getEmail())));
////                emailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////                emailCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
////                emailCell.setPaddingRight(4);
////                table.addCell(emailCell);
////
////                PdfPCell studentNameCell = new PdfPCell(new Phrase
////                        (FacultyNotes.getstudent().getFirstName()));
////                studentNameCell.setPaddingLeft(4);
////                studentNameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////                studentNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
////                table.addCell(studentNameCell);
////
////                PdfPCell studentEmailCell = new PdfPCell(new Phrase
////                        (String.valueOf(FacultyNotes.getstudent().getEmail())));
////                studentEmailCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////                studentEmailCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
////                studentEmailCell.setPaddingRight(4);
////                table.addCell(studentEmailCell);
////
////
////                PdfPCell FacultyNotesCell = new PdfPCell(new Phrase
////                        (String.valueOf(FacultyNotes.getFacultyNotes())));
////                FacultyNotesCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
////                FacultyNotesCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
////                FacultyNotesCell.setPaddingRight(4);
////                table.addCell(FacultyNotesCell);
//
//            document.add(table);
//
//
//            document.close();
        } catch (FileNotFoundException | MalformedURLException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
