package com.Lemon.mysqlcontrast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
@Component
public class TableCreateContrastService {
	@Resource(name="primaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate1;
	@Resource(name="secondaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;
	@Autowired
	private ExcelUtil excelUtil;
	@Autowired
	private CreateContrastDao createContrastDao;
	
	public XSSFWorkbook getTableCreateInfo(XSSFWorkbook wb) throws IOException {
		
		Map<String, String> tableDevCreateInfo = getTableCreateMap(jdbcTemplate1,"scmtest");
		Map<String, String> tableProCreateInfo = getTableCreateMap(jdbcTemplate2,"scm");
		// 把数据写入excel
		createTableSheet(wb,tableDevCreateInfo,tableProCreateInfo);
		return wb;
	}
	
	private Map<String, String> getTableCreateMap(JdbcTemplate jdbcTemplate,String dbName){
		List<Map<String, Object>> list = createContrastDao.getTableInfo(jdbcTemplate,dbName);
		Map<String, String> tableCreateInfo = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("Name")==null){
				continue;
			}
			String tableName = list.get(i).get("Name").toString();
			if(tableName.length()>4){
				if(tableName.substring(0, 4).equals("view")){
					continue;
				}
			}
			Map<String, Object> tableInfoMap = new HashMap<String, Object>();
			// 获取测试库Table对应的创建语句
			tableInfoMap = createContrastDao.getCreateInfoByTable(jdbcTemplate,tableName);
			String createTableStr=tableInfoMap.get("Create Table").toString();
			String betweenStr="";
			String resultStr=createTableStr;
			//如果存在“AUTO_INCREMENT”，则把这个字段值去掉
			if(StringUtils.contains(createTableStr, "AUTO_INCREMENT=")){
				betweenStr=StringUtils.substringBetween(createTableStr, "AUTO_INCREMENT=", "DEFAULT");
				resultStr=createTableStr.replace("AUTO_INCREMENT="+betweenStr, "");
			}
			tableCreateInfo.put(tableName,resultStr );
		}
		return tableCreateInfo;
	}
	
	private XSSFWorkbook createTableSheet(XSSFWorkbook wb,Map<String, String> tableDevCreateInfo,Map<String, String> tableProCreateInfo) {
		XSSFSheet sheet = wb.createSheet("表");

		List<String> titleNameList = new ArrayList<>(Arrays.asList("表名称","测试库","正式库","差异"));
		
		excelUtil.createSheetTitleRow(sheet,titleNameList);

		excelUtil.createSheetDataRows(sheet, tableDevCreateInfo,tableProCreateInfo);

		return wb;
	}
}
