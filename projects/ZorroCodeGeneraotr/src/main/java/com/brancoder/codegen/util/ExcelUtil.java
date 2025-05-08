package com.brancoder.codegen.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import com.brancoder.codegen.bean.ZorroCtrlBean;

public class ExcelUtil {
	
	public static Workbook getWorkbook(String path) {
		Workbook wb = null;
		if (path == null)
			return null;
		String extString = path.substring(path.lastIndexOf(".")).toUpperCase();
		InputStream is;
		try {
			is = new FileInputStream(path);
			if (".XLS".equals(extString)) {
				wb = new HSSFWorkbook(is);
			} else if (".XLSX".equals(extString)) {
				wb = new XSSFWorkbook(is);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	public static Sheet readSheet(File selectedFile, int sheetIndex) {
		try {


			// 使用WorkbookFactory來創建一個工作簿
			Workbook workbook = getWorkbook(selectedFile.getAbsolutePath());

			// 從工作簿中獲取第一個工作表
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			workbook.close();
			return sheet;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	

}
