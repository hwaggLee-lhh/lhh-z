package com.lhh.z.poi.excel;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * Excel单元格处理
 * @author hwaggLee
 * @createDate 2017年1月4日
 */
public class ZUtilsExcelCell {
	public static void fillCell(XSSFSheet sheet, int rownum, int cellnum, String value) {
		if(sheet==null || StringUtils.isBlank(value)) return;
		XSSFRow row = sheet.getRow(rownum);
		if(row==null) return;
		XSSFCell cell = row.getCell(cellnum);
		if(cell==null) {
			cell = row.createCell(cellnum);
		}
		cell.setCellValue(value);
	}
	public static void fillCell(XSSFSheet sheet, int rownum, int cellnum, BigDecimal value) {
		if(sheet==null || value==null) return;
		XSSFRow row = sheet.getRow(rownum);
		if(row==null) return;
		XSSFCell cell = row.getCell(cellnum);
		if(cell==null) return;
		cell.setCellValue(new Double(value+""));
	}
	public static void fillCell(XSSFSheet sheet, int rownum, int cellnum, Integer value) {
		if(sheet==null || value==null) return;
		XSSFRow row = sheet.getRow(rownum);
		if(row==null) return;
		XSSFCell cell = row.getCell(cellnum);
		if(cell==null) return;
		cell.setCellValue(value);
	}
	public static void fillCell(XSSFSheet sheet, int rownum, int cellnum, BigInteger value) {
		if(sheet==null || value==null) return;
		XSSFRow row = sheet.getRow(rownum);
		if(row==null) return;
		XSSFCell cell = row.getCell(cellnum);
		if(cell==null) return;
		cell.setCellValue(value.intValue());
	}

	public static void fillCellStyle(XSSFSheet sheet, int rownum, int cellnum, String value,XSSFCellStyle style) {
		if(sheet==null ) return;
		if( StringUtils.isBlank(value) ){
			value = "";
		}
		XSSFRow row = sheet.getRow(rownum);
		if(row==null) row = sheet.createRow(rownum);
		XSSFCell cell = row.getCell(cellnum);
		if(cell==null) {
			cell = row.createCell(cellnum);
		}
		cell.setCellValue(value);
		if( style != null ){
			cell.setCellStyle(style);
		}
	}

}
