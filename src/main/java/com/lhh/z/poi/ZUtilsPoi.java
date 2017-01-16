package com.lhh.z.poi;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ZUtilsPoi {

	/**
	 * 导出文件
	 * @param res
	 * @param wb
	 * @param filename
	 */
	public static void writer(HttpServletResponse res,XSSFWorkbook wb,String filename){
		try {
			res.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GBK"), "ISO-8859-1"));
			OutputStream out = res.getOutputStream();
			wb.write(out);
	        out.flush();
	        out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
