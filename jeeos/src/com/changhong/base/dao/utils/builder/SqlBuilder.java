package com.changhong.base.dao.utils.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.changhong.base.dao.utils.Querys;

/**
 * sql构建器
 * 用于生成sql查询字符串 以及 设置查询参数
 * @author wanghao
 * 2009-07-28 16：05 pm
 */
public class SqlBuilder{

	//查询字符串
	private StringBuilder sqlStr;
	//list value
	private List<Object> values;
	
	public SqlBuilder() {
		sqlStr = new StringBuilder("");
	}
	
	public SqlBuilder(String sql) {
		sqlStr = new StringBuilder(sql);
	}
	public SqlBuilder clear() {
		sqlStr = new StringBuilder("");
		return this;
	}
	/**
	 * 链接子sql语句
	 */
	public SqlBuilder append(String sql) {
		sqlStr.append(sql);
		return this;
	}
	/**
	 * sql语句字符串
	 * @return
	 */
	public String getQuerySql() {
		return sqlStr.toString();
	}
	
	
//	/**
//	 * 查询记录总数字符串  mysql 
//	 * @return
//	 */
//	public String getTotalRecordsSql() {
//		String sql = sqlStr.toString();
//		int fromIndex = sql.toLowerCase().indexOf("from");
//		//"from" 前的select字串，不包括"from"
//		String beforeFromSql = "select count(1) ";
//		//"from"后面的select字串，包括"from"
//		String afterFromSql = sql.substring(fromIndex);
//		
//		return beforeFromSql+ afterFromSql ;
//	}
	/**
	 * 查询记录总数字符串  for  sql server
	 * @return
	 */
	public String getTotalRecordsSql() {
		String sql = sqlStr.toString();
		int fromIndex = sql.toLowerCase().indexOf("from");
		//"from" 前的select字串，不包括"from"
		String beforeFromSql = "select count(1) ";
		//"from"后面的select字串，包括"from"
		String afterFromSql = sql.substring(fromIndex);
		int endIndex =afterFromSql.toLowerCase().lastIndexOf("order by"); 
		return beforeFromSql+ ((endIndex<0)?afterFromSql:afterFromSql.substring(0, endIndex));
	}
	
	
	 


	/**
	 * 设置参数的值
	 * @param param
	 * @param value
	 * @return
	 */
	public SqlBuilder setParam(Object value) {
		putToList(value);
		return this;
	}
	
	public SqlBuilder setParams(List<Object> values) {
		putToList(values);
		return this;
	}
    /**
     * 设置Map名值对
     * @param paramMap
     */
	public void setParamMap(Map<String, ?> paramMap) {
		if(paramMap!=null && paramMap.size()>0){
			for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
				Object value = entry.getValue();
				String key = entry.getKey();
				if(value!=null && !"".equals((value+"").trim()) && !"%null%".equals(value+"") && !"%%".equals(value+"")
				   && StringUtils.isNotBlank(key) 
				   && !Querys.ORDER_ATTR.equals(key) && !Querys.ORDER_TYPE.equals(key) && !key.startsWith(Querys.PREFIX)){
					
					putToList(value);
				}
			}
		}
	}
	
    /**
     * 将参数值放入list
     * @return
     */
	private List<Object> putToList(Object value) {
		if (values == null) {
			values = new ArrayList<Object>();
		}
		if(value instanceof Collection<?>){
			values.addAll((Collection<?>) value);
		}else{
			values.add(value);
		}
		return values;
	}
	
	/**
	 * 获得参数值的的数组，便于作为dbutils.queryRunner.query的参数
	 * @return
	 */
	public Object[] getValues(){
		if(values!=null){
			return values.toArray();
		}
		return null;
	}
}
