package com.Lemon.mysqlcontrast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class FunctionCreateContrastService {
	@Resource(name="primaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate1;
	@Resource(name="secondaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;
	@Autowired
	private ExcelUtil excelUtil;
	@Autowired
	private CreateContrastDao createContrastDao;
	
	public XSSFWorkbook getFunctionCreateInfo(XSSFWorkbook wb){
		
		Map<String, String> funcDevCreateInfo = getFuncCreateMap(jdbcTemplate1,"scmtest");
		Map<String, String> funcProCreateInfo = getFuncCreateMap(jdbcTemplate2,"scm");
		// 把数据写入sheet
		createFuncSheet(wb,funcDevCreateInfo,funcProCreateInfo);
		return wb;
	}
	
	private Map<String, String> getFuncCreateMap(JdbcTemplate jdbcTemplate,String dbName){
		// 获取测试库scmtest下的所有Function
		List<Map<String, Object>> list = createContrastDao.getFuncInfo(jdbcTemplate,dbName);
		Map<String, String> funcCreateInfo = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("Name")==null){
				continue;
			}
			String funcName = list.get(i).get("Name").toString();
			Map<String, Object> funcInfoMap = new HashMap<String, Object>();
			// 获取测试库Function对应的创建语句
			funcInfoMap = createContrastDao.getCreateInfoByFunc(jdbcTemplate,funcName);
			funcCreateInfo.put(funcName, funcInfoMap.get("Create Function").toString());
		}
		return funcCreateInfo;
	}
	
	private XSSFWorkbook createFuncSheet(XSSFWorkbook wb,Map<String, String> funcDevCreateInfo,Map<String, String> funcProCreateInfo) {
		XSSFSheet sheet = wb.createSheet("函数");

		List<String> titleNameList = new ArrayList<>(Arrays.asList("函数名称","测试库","正式库","差异"));
		
		excelUtil.createSheetTitleRow(sheet,titleNameList);

		excelUtil.createSheetDataRows(sheet, funcDevCreateInfo,funcProCreateInfo);

		return wb;
	}
	
}
