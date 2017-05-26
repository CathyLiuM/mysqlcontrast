package com.Lemon.mysqlcontrast;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CreateContrastDao {
	//////////Procdure/////////////////////
	/**
	 * 存储过程
	 * @return
	 */
	public List<Map<String, Object>> getProcInfo(JdbcTemplate jdbcTemplate,String dbName) {
		List<Map<String, Object>> list=jdbcTemplate.queryForList("show PROCEDURE status where db=?",new Object[] {dbName});
//		List<HashMap<String, Object>> list = procstatusApplicationDevMapper.getTestProcInfo();
		return list;
	}
	/**
	 * 存储过程的create语句
	 * @param procName
	 * @return
	 */
	public Map<String,Object> getCreateInfoByProc(JdbcTemplate jdbcTemplate,String procName){
//		Map<String,Object> createInfoMap = jdbcTemplate.queryForMap("show create PROCEDURE "+procName);
//		return createInfoMap;
		return a(jdbcTemplate,"PROCEDURE",procName);
	}
	//////////////Table///////////////////
	
	/**
	 * 表
	 * @return
	 */
	public List<Map<String, Object>> getTableInfo(JdbcTemplate jdbcTemplate,String dbName){
		List<Map<String, Object>> list=jdbcTemplate.queryForList("show table status from "+dbName);
		return list;
	}
	/**
	 * 表名对应的创建语句
	 * @param tableDevName
	 * @return
	 */
	public Map<String,Object> getCreateInfoByTable(JdbcTemplate jdbcTemplate,String tableDevName){
//		Map<String,Object> createInfoMap = jdbcTemplate.queryForMap("show create table "+tableDevName);
//		return createInfoMap;
		return a(jdbcTemplate,"table",tableDevName);
	}
	
	public Map<String,Object> a(JdbcTemplate jdbcTemplate,String type,String name){
		return jdbcTemplate.queryForMap("show create "+type+"  "+name);
	}
	/////////Function///////////////
	/**
	 *函数
	 * @return
	 */
	public List<Map<String, Object>> getFuncInfo(JdbcTemplate jdbcTemplate,String dbName){
		List<Map<String, Object>> list=jdbcTemplate.queryForList("show function status where db=?",new Object[] {dbName});
		return list;
	}
	/**
	 * 函数名对应的创建语句
	 * @param funcDevName
	 * @return
	 */
	public Map<String,Object> getCreateInfoByFunc(JdbcTemplate jdbcTemplate,String funcDevName){
		Map<String,Object> createInfoMap = jdbcTemplate.queryForMap("show create function "+funcDevName);
		return createInfoMap;
	}
}
