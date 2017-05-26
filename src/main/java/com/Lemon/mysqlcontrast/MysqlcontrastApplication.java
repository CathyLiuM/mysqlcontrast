package com.Lemon.mysqlcontrast;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MysqlcontrastApplication implements CommandLineRunner{
	
	@Autowired
	private ProcCreateContrastService procCreateContrastService;
	@Autowired
	private TableCreateContrastService tableCreateContrastService;
	@Autowired
	private FunctionCreateContrastService functionCreateContrastService;
	@Autowired
	private ExcelUtil excelUtil;
	
	
	public static void main(String[] args) {
		SpringApplication.run(MysqlcontrastApplication.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook();
		// TODO Auto-generated method stub
		procCreateContrastService.getProcCreateInfo(wb);
		tableCreateContrastService.getTableCreateInfo(wb);
		functionCreateContrastService.getFunctionCreateInfo(wb);
		excelUtil.writeExcel(wb);
	}

}
