package com.lhh.z.poi.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	private XSSFWorkbook wb;

	/**
	 * 读取EXCEL的单个SHEEL数据
	 * @param path
	 * @param sheetName
	 * @return
	 */
	public List<Map<Integer,Object>> processExcel(String path, String sheetName,int firstRowNum) {
		try {
			wb = new XSSFWorkbook(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( wb == null ) return null;
		return process(sheetName,firstRowNum);
	}

	/**
	 * 读取总格EXCEL中所有的SHEEL数据.返回LinkedHashMap先进先出的map数据
	 * @return
	 */
	public Map<String,List<Map<Integer,Object>>> process(InputStream inputStream,int firstRowNum){
		try {
			wb = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(wb == null )return null;
		Map<String,List<Map<Integer,Object>>> map = new LinkedHashMap<String, List<Map<Integer,Object>>>();
		for (XSSFSheet sheet : wb) {
			if(sheet==null) continue;;
			List<Map<Integer,Object>> result = process(sheet.getSheetName(),firstRowNum);
			if( result == null )continue;
			map.put(sheet.getSheetName(), result);
		}
		return map;
	}
	
	/**
	 * 读取excel，且Excel必须大于1行数据
	 * @param sheetName
	 * @param firstRowNum:从第几行开始读取,下标从0开始
	 * @return
	 */
	private List<Map<Integer,Object>> process(String sheetName,int firstRowNum){
		if( wb == null ) return null;
		XSSFSheet sheet = wb.getSheet(sheetName);
		if(sheet==null) return null;
		int lastRowNum = sheet.getLastRowNum()+1;
		if(lastRowNum<=0) return null;
		List<Map<Integer,Object>> result = new ArrayList<Map<Integer,Object>>();
		for (int i = firstRowNum; i < lastRowNum; i++) {
			Map<Integer,Object> rowMap = new LinkedHashMap<Integer,Object>();
			result.add(rowMap);
			XSSFRow row = sheet.getRow(i);
			if( row == null)continue;
			short lastCellNum =row.getLastCellNum();
			if(lastCellNum<=0) continue;
			for (int j = 0; j < lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				Object value = null;
				if(cell!=null){
					switch(cell.getCellType()) {
					case XSSFCell.CELL_TYPE_BOOLEAN:
						value = cell.getBooleanCellValue();
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						short format = cell.getCellStyle().getDataFormat();  
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							Date d = cell.getDateCellValue();
							DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = formater.format(d);
						}else if(format == 14 || format == 31 || format == 57 || format == 58){  
					        //日期  
							DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");  
							Date date = DateUtil.getJavaDate(cell.getNumericCellValue());  
							value = formater.format(date);  
						}else if (format == 20 || format == 32) {  
					        //时间  
							DateFormat formater = new SimpleDateFormat("HH:mm");    
							Date date = DateUtil.getJavaDate(cell.getNumericCellValue());  
							value = formater.format(date);  
					    } else{
							value = cell.getNumericCellValue();
						}
						//System.out.println(value+":"+cell.getCellStyle().getDataFormat());
						break;
					case XSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					}
					
				}
				value = rowMap.put(j,value);
			}
		}
		return result;
	}
}
