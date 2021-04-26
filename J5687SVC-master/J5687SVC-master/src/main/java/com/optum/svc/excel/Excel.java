package com.optum.svc.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.optum.svc.bean.SvcBean;
import com.optum.svc.mail.SendMail;


public class Excel {

	private static final String FILE_NAME="SVC_REPORT.xlsx";
	private static InputStream fileInput;
	private static FileOutputStream fileOut;
   
	
	public static void createExcelSheet(String sheetName)
	{
		String header[]={"PROCESS DATE","LOADED TO PRADJD CLAIM REPOSITORY TABLES","LOADED TO PRADJD SERVICE CHANNEL TABLES","PENDING PRADJD CLAIMS","PROCESS DATE","LOADED TO ADJD CLAIM REPOSITORY TABLES","LOADED TO ADJD SERVICE CHANNEL TABLES","PENDING ADJD CLAIMS"};
		XSSFWorkbook workbook= new XSSFWorkbook(); 
		try
		{	
			//workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet(sheetName);
			for(int i=0;i<header.length;i++)
			sheet.setColumnWidth(i, 5000);
			Font font = workbook.createFont();
		    XSSFRow row;
		    Cell cell; 
		    row= sheet.createRow(0);
			XSSFCellStyle cellStyle=(XSSFCellStyle) row.getSheet().getWorkbook().createCellStyle();
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			//XSSFColor myColor = new XSSFColor(new java.awt.Color(155,199,249));
			/*XSSFColor myColor = new XSSFColor(new java.awt.Color(198,211,224));
			cellStyle.setFillForegroundColor(myColor);*/
			cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		    cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		    cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		    cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		    cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		    cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		    cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		    cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		    cellStyle.setWrapText(true);
		    cellStyle.setFont(font);
			for(int i=0;i<header.length;i++)
				 {
					 cell=row.createCell(i);
					 sheet.autoSizeColumn(i);
					 cell.setCellValue(header[i]); 
					 cell.setCellStyle(cellStyle);
			     }
		    fileOut = new FileOutputStream(new File(FILE_NAME)); 
		    workbook.write(fileOut);   
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			safeClose(fileOut);
			safeClose(workbook);
		}
	}
	public static void main(String args[])
	{
		createExcelSheet("RECON_REPORT");

	}
	public static CellStyle setStyle(Row row)
	{
		CellStyle cellStyle=row.getSheet().getWorkbook().createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
	    cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
	    cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyle.setBorderRight(CellStyle.BORDER_THIN);
	    cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
	    cellStyle.setBorderTop(CellStyle.BORDER_THIN);
	    cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
	    return cellStyle;
	}
	public static void  write(SvcBean arrayBean[])
	{
		createExcelSheet("SVC_REPORT");
			
		try{
		    fileInput = new FileInputStream(FILE_NAME); 
		    Workbook wb = WorkbookFactory.create(fileInput);
		    Sheet sheet=  wb.getSheet("SVC_REPORT"); 
		    Row row; Cell cell;
			for(int i=0;i<arrayBean.length;i++)
			{
				row = sheet.createRow(i+1);
				CellStyle cellStyle = setStyle(row);
				String data[]=arrayBean[i].getDataFromObj().split(" ");
				for(int j=0;j<data.length;j++)
				{
					 cell=row.createCell(j);
					 cell.setCellValue(data[j]);
					 cell.setCellStyle(cellStyle);
				}
			}
			fileOut = new FileOutputStream(FILE_NAME); 
		    wb.write(fileOut);
			System.out.println("*        FILE CREATED SUCCESSFULLY        *");
			new SendMail().htmlMail(FILE_NAME);
			System.out.println("*         FILE SENT SUCCESSFULLY          *");
			File file = new File(FILE_NAME); 
	        if(file.delete()) 
	        { 
	            System.out.println("*       FILE DELETED SUCCESSFULLY         *"); 
	        } 
	        else
	        { 
	            System.out.println("FIALED TO DELETE FILE                    "); 
	        } 
		    
		    
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			safeClose(fileInput);
			safeClose(fileOut);
		}
     } 
	public static void safeClose(FileOutputStream fileOutputStream)
	{
		try {
			fileOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("FAILED TO CLOSE THE OUT FILE");
		}
	}
	public static void safeClose(InputStream inputStream)
	{
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("FAILED TO CLOSE THE IN FILE");
		}
	}
	public static void safeClose(Workbook workbook)
	{
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("FAILED TO CLOSE THE WORKBOOK");
		}
	}
}


