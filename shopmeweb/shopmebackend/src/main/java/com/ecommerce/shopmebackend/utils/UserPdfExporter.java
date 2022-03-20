package com.ecommerce.shopmebackend.utils;

import com.ecommerce.shopmecommon.entity.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserPdfExporter extends AbstractExporter {

    public void export(List<User> userList, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");
        String[] pdfHeader = {"ID", "Email", "First Name", "Last Name", "Roles", "Enabled"};

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);

        Paragraph paragraph = new Paragraph("List of Users", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);

        writeTableHeader(table, pdfHeader);
        writeTableData(table, userList);

        document.add(table);

        document.close();
    }

    private void writeTableHeader(PdfPTable table, String[] pdfHeader) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(12);

        for (String header: pdfHeader) {
            cell.setPhrase(new Phrase(header, font));
            table.addCell(cell);
        }
    }

    private void writeTableData(PdfPTable table, List<User> userList) {
        for (User user: userList) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getEmail());
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getRoles().toString());
            table.addCell(String.valueOf(user.isEnabled()));
        }
    }
}
