package com.apptriggergenerator.listcontrollers.utils;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public void read() throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			for (int j = 0; j < sheet.getColumns(); j++) {
				for (int i = 0; i < sheet.getRows(); i++) {
					Cell cell = sheet.getCell(j, i);
					CellType type = cell.getType();
					if (type == CellType.LABEL) {
						System.out.println("I got a label " + cell.getContents());
					}
					if (type == CellType.NUMBER) {
						System.out.println("I got a number " + cell.getContents());
					}
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public void readForInsert(String tablename, String... fields) throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			String instructions = "INSERT INTO " + tablename + "(";
			for (int i = 0; i < fields.length; i++) {
				if (i != 0) {
					instructions += ", ";
				}
				instructions += fields[i];
			}
			instructions += ") VALUES (";
			String auxRecord;
			for (int i = 0; i < sheet.getRows(); i++) {
				auxRecord = instructions;
				for (int j = 0; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					if (j != 0) {
						auxRecord += ", ";
					}
					if (cell.getContents().equals("curdate()")) {
						auxRecord += cell.getContents();
					} else {
						auxRecord += "'" + cell.getContents() + "'";
					}
				}
				auxRecord += ");";
				System.out.println(auxRecord);
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public void getInsertEMPosibles() throws IOException {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
			for (int i = 0; i < sheet.getRows(); i++) {
				Cell cell = sheet.getCell(0, i);
				System.out.println(getInsertEMPosibles(cell.getContents()));
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}
	}

	public String getInsertEMPosibles(String lIAcademica_id) {
		return "INSERT INTO appModuloBasico.gatbl_EMPosibles \r\n" + "    (lEMPosible_id, \r\n" + "    lIAcademica_id, \r\n" + "    iPeriodo_id, \r\n" + "    IMateria_id, \r\n"
		        + "    iNroMateria, \r\n" + "    sCaso_fl, \r\n" + "    sSemestreCarr_fl, \r\n" + "    lVeces_qty, \r\n" + "    sPreinscripcion_fl, \r\n" + "    iEstado_fl, \r\n"
		        + "    iEliminado_fl, \r\n" + "    sCreated_by, \r\n" + "    iConcurrencia_id\r\n" + "    )\r\n" + "    VALUES\r\n" + "    (getNextTableId('gatbl_EMPosibles'), \r\n" + "    "
		        + lIAcademica_id + ", \r\n" + "    45, \r\n" + "    335, \r\n" + "    1, \r\n" + "    '01', \r\n" + "    '01', \r\n" + "    1, \r\n" + "    '01', \r\n" + "    '01', \r\n"
		        + "    '01', \r\n" + "    'psoliz', \r\n" + "    0\r\n" + "    );";
	}

	public static void main(String[] args) throws IOException {
		ReadExcel test = new ReadExcel();
		/*
		 * test.setInputFile("D:/Gatbl_IAcademicas.xls");
		 * test.readForInsert("gatbl_IAcademicas", "lIAcademica_id", "lEstudiante_id", "iPrograma_id", "IPensum_id", "iPeriodo_inicio", "sRegistro_desc", "dtAdmision_dt", "dtInicioCarr_dt",
		 * "sTipoIngreso_id", "lCredVencido", "sSemestreCarr_id", "iEstado_fl", "iEliminado_fl", "sCreated_by", "iConcurrencia_id");
		 */
		test.setInputFile("D:/acad.xls");
		test.getInsertEMPosibles();
	}
}