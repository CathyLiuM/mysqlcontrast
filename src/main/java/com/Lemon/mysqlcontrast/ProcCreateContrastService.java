package com.Lemon.mysqlcontrast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
@Component
public class ProcCreateContrastService {
	@Resource(name="primaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate1;
	@Resource(name="secondaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;
	@Autowired
	private ExcelUtil excelUtil;
	@Autowired
	private CreateContrastDao createContrastDao;
	
	public XSSFWorkbook getProcCreateInfo(XSSFWorkbook wb) throws IOException {
		
		Map<String, String> procDevCreateInfo=getProcCreateMap(jdbcTemplate1,"scmtest");
		Map<String, String> procProCreateInfo=getProcCreateMap(jdbcTemplate2,"scm");
		// 把数据写入sheet
		createProcSheet(wb,procDevCreateInfo,procProCreateInfo);
		return wb;
	}
	
	private Map<String, String> getProcCreateMap(JdbcTemplate jdbcTemplate,String dbName){
		List<Map<String, Object>> list = createContrastDao.getProcInfo(jdbcTemplate,dbName);
		Map<String, String> procCreateInfo = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("Name")==null){
				continue;
			}
			String procName = list.get(i).get("Name").toString();
			Map<String, Object> procInfoMap = new HashMap<String, Object>();
			procInfoMap = createContrastDao.getCreateInfoByProc(jdbcTemplate,procName);
			procCreateInfo.put(procName, procInfoMap.get("Create Procedure").toString());
		}
		return procCreateInfo;
	}
	
	private XSSFWorkbook createProcSheet(XSSFWorkbook wb,Map<String, String> procDevCreateInfo,Map<String, String> procProCreateInfo) {
		XSSFSheet sheet = wb.createSheet("存储过程");
		List<String> titleNameList = new ArrayList<>(Arrays.asList("存储过程名称","测试库","正式库","差异"));
		
		excelUtil.createSheetTitleRow(sheet,titleNameList);

		excelUtil.createSheetDataRows(sheet, procDevCreateInfo,procProCreateInfo);

		return wb;
	}
}
