package umu.tds.maven.apps.PhotoApp.modelo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

	private User user;

	public PdfGenerator(User user) {
		this.user = user;
	}

	public boolean generatePdf(String path) {

		try {
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(path + "lista-seguidores.pdf"));
			document.open();
			PdfPTable table = new PdfPTable(3); // 3 columnas
			PdfPCell cell1 = new PdfPCell(new Phrase("Nombre"));// nombre usuario
			PdfPCell cell2 = new PdfPCell(new Phrase("Email"));// email usuario
			PdfPCell cell3 = new PdfPCell(new Phrase("Descripción"));// descripción usuario
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			for (User user : user.getFollowers()) {
				PdfPCell cell4 = new PdfPCell(new Phrase(user.getUserName()));// nombre usuario
				PdfPCell cell5 = new PdfPCell(new Phrase(user.getEmail()));// email usuario
				PdfPCell cell6 = new PdfPCell(new Phrase(user.getBio()));// descripción usuario
				table.addCell(cell4);
				table.addCell(cell5);
				table.addCell(cell6);
			}
			document.add(table);
			document.close();
		} catch (DocumentException | FileNotFoundException e) {
			return false;
		}
		return true;
	}
}
