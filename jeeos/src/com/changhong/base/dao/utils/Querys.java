package com.changhong.base.dao.utils;

/**
 * 外部查询 常量值
 * @author wanghao
 */
public class Querys {
	/**
	 * 排序属性
	 */
	public final static String ORDER_ATTR="orderattr";
	/**
	 * 排序字段
	 */
	public final static String ORDER_TYPE="ordertype";
	/**
	 * 属性前缀，ru 如：<#if  _query_propertyName??>，这时_query_propertyName不set进sql/hql语句中，仅仅作为判断的条件
	 */
	public final static String PREFIX="_query_";
	/**
	 * 根查询文件路径
	 */
	public final static String QUERY_FILE_PATH = "/resources/query/query.xml";
	
}
