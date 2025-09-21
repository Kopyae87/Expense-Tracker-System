package org.ace.java.web.expense;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.ace.accounting.expense.Entity.Expense;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExport {
	
	   public static void exportExpensesToExcel(List<Expense> expenses, String filename) throws IOException {
	        Calendar cal = Calendar.getInstance();
	        String str = cal.get(Calendar.DAY_OF_MONTH) +"-" + cal.get(Calendar.MONTH)+1 + "-" + cal.get(Calendar.HOUR_OF_DAY) +"-"+ cal.get(Calendar.MINUTE);
		   	FacesContext facesContext = FacesContext.getCurrentInstance();
	        HttpServletResponse response =
	                (HttpServletResponse) facesContext.getExternalContext().getResponse();

	        response.reset();
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

	        try (Workbook workbook = new XSSFWorkbook()) {
	            Sheet sheet = workbook.createSheet("Expenses");
	            
	            
	            // Header Row
	            Row headerRow = sheet.createRow(0);
	            headerRow.createCell(0).setCellValue("Date");
	            headerRow.createCell(1).setCellValue("Category");
	            headerRow.createCell(2).setCellValue("Amount");
	            headerRow.createCell(3).setCellValue("Payment Type");
	            headerRow.createCell(4).setCellValue("Description");

	            // Populate rows
	            int rowNum = 1;
	            for (Expense expense : expenses) {
	                Row row = sheet.createRow(rowNum++);
	                row.createCell(0).setCellValue(expense.getExpenseDate().toString());
	                row.createCell(1).setCellValue(expense.getCategory().getName());
	                row.createCell(2).setCellValue(expense.getAmount());
	                row.createCell(3).setCellValue(expense.getPaymentType());
	                row.createCell(4).setCellValue(expense.getDescription());
	            }

	            for(int i = 0; i< 4;i++) {
	            	sheet.autoSizeColumn(i);
	            }
	            workbook.write(response.getOutputStream());
	        }

	        facesContext.responseComplete();
	    }

//	    public static void exportReportsToExcel(List<Report> reports, String filename) throws IOException {
//	        FacesContext facesContext = FacesContext.getCurrentInstance();
//	        HttpServletResponse response =
//	                (HttpServletResponse) facesContext.getExternalContext().getResponse();
//
//	        response.reset();
//	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//	        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
//
//	        try (Workbook workbook = new XSSFWorkbook()) {
//	            Sheet sheet = workbook.createSheet("Reports");
//
//	            // Header Row
//	            Row headerRow = sheet.createRow(0);
//	            headerRow.createCell(0).setCellValue("Report Name");
//	            headerRow.createCell(1).setCellValue("Generated Date");
//	            headerRow.createCell(2).setCellValue("Total Expense");
//	            headerRow.createCell(3).setCellValue("Remarks");
//
//	            int rowNum = 1;
//	            for (Report report : reports) {
//	                Row row = sheet.createRow(rowNum++);
//	                row.createCell(0).setCellValue(report.getName());
//	                row.createCell(1).setCellValue(report.getGeneratedDate().toString());
//	                row.createCell(2).setCellValue(report.getTotalExpense());
//	                row.createCell(3).setCellValue(report.getRemarks());
//	            }
//
//	            workbook.write(response.getOutputStream());
//	        }
//
//	        facesContext.responseComplete();
//	    }
}
