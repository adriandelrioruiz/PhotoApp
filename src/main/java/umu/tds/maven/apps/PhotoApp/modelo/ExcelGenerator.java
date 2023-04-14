package umu.tds.maven.apps.PhotoApp.modelo;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class ExcelGenerator {
	
	private User user;
	
	public ExcelGenerator(User user) {
		this.user = user;
	}
	
	public boolean generateExcel(String path) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("lista-seguidores");

		// Contador de filas
		int i = 0;
		// Creamos una fila para cada seguidor
		for (User user : user.getFollowers()) {
			Row row = sheet.createRow(i);

			Cell cellName = row.createCell(0, CellType.STRING);
			Cell cellEmail = row.createCell(1, CellType.STRING);
			Cell cellBio = row.createCell(2, CellType.STRING);

			cellName.setCellValue(user.getUserName());
			cellEmail.setCellValue(user.getEmail());
			cellBio.setCellValue(user.getBio());

			i++;
		}

		FileOutputStream out;
		try {
			out = new FileOutputStream(path + "lista-seguidores.xlsx");
			workbook.write(out);
			out.close();
			workbook.close();
		} catch (IOException e) {
			return false;
		}

		return true;

	}
}
