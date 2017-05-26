package com.Lemon.mysqlcontrast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class ExcelUtil {
	/**
	 * 创建sheet的title
	 * @param sheet
	 * @param titleNameList
	 * @return
	 */
	public XSSFSheet createSheetTitleRow(XSSFSheet sheet,List<String> titleNameList){
		XSSFRow row = sheet.createRow(0);
		for(int i=0;i<titleNameList.size();i++){
			row.createCell(i).setCellValue(titleNameList.get(i));
		}
		return sheet;
	}
	
	/**
	 * 根据测试库和正式库的数据写入excel
	 * @param sheet
	 * @param info1
	 * @param info2
	 * @return
	 */
	public XSSFSheet createSheetDataRows(XSSFSheet sheet, Map<String, String> info1,Map<String, String> info2){
		int i = 0;
		for (Map.Entry<String, String> entry : info1.entrySet()) {
			XSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(entry.getKey());
			row.createCell(1).setCellValue(entry.getValue());
			if(info2.get(entry.getKey())!=null){
				row.createCell(2).setCellValue(info2.get(entry.getKey()));
			}
			XSSFCell cell3 = row.createCell(3);
			cell3.setCellFormula("IF(INDIRECT(ADDRESS(ROW(),COLUMN()-1))=INDIRECT(ADDRESS(ROW(),COLUMN()-2)),\"无\",\"有\")");
			cell3.setCellType(Cell.CELL_TYPE_FORMULA);
			i++;
		}
		return sheet;
	}
	
	/**
	 * 把excel写入路径下
	 * @param wb
	 * @throws IOException
	 */
	public void writeExcel(XSSFWorkbook wb) throws IOException{
		File file=new File("C:\\Users\\liu.mr\\Downloads\\测试库正式库对比.xlsx");
		if(!file.exists()){
			file.createNewFile();
		}else{
			file.delete();
			file.createNewFile();
		}
		FileOutputStream os =new FileOutputStream(file);
		wb.write(os);
		os.close();
	}
}
