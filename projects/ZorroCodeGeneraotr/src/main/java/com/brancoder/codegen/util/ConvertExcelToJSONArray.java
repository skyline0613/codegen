package com.brancoder.codegen.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONObject;
 
public class ConvertExcelToJSONArray extends ConvertExcelToJsonBase {
 
    public JSONArray convertExceltoJSONArray_withObject(final String excelFileName, int sheetIndex) {
        JSONArray jsonArray = new JSONArray();
        try {
            initialize(excelFileName, sheetIndex);
 
            String[] headers = new String[sheet.getRow(1).getLastCellNum()];
 
            Iterator<Row> iterator = sheet.iterator();
 
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 1) {
                    for (int i = 0; i < currentRow.getLastCellNum(); i++) {
                        headers[i] = CommonUtil.toCamelCase(currentRow.getCell(i).getStringCellValue());
                    }
                } else if(currentRow.getRowNum() > 1){
                    JSONObject obj = new JSONObject();
                    for (int i = 0; i < currentRow.getLastCellNum(); i++) {
                        Cell currentCell = currentRow.getCell(i);
                        if(currentCell == null)
                        	continue;
                        if (CellType.NUMERIC == currentCell.getCellType()) {
                            obj.put(headers[i], currentCell.getNumericCellValue());
                        }else if (currentCell.getCellType() == CellType.STRING) {
                            obj.put(headers[i], currentCell.getStringCellValue());
                        }else {
                        	obj.put(headers[i], "");
                        }
                        	
                    }
                    jsonArray.put(obj);
                }
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource();
 
        }
 
        return jsonArray;
    }
 
    public JSONArray convertExceltoJSONArray_withValueOnly(final String excelFileName, final int sheetIndex) {
        JSONArray jsonArray = new JSONArray();
        try {
            initialize(excelFileName, sheetIndex);
 
            Row headerRow = sheet.getRow(0);
            List<String> headers = new ArrayList<String>();
            for (Cell cell : headerRow) {
                headers.add(cell.getStringCellValue());
            }
            jsonArray.put(headers);
 
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                List<String> rowData = new ArrayList<String>();
                for (Cell cell : row) {
                    rowData.add(cell.toString());
                }
                jsonArray.put(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResource();
        }
 
        return jsonArray;
    }
 
}