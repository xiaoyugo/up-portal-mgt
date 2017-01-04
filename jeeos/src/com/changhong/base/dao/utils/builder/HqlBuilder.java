package com.changhong.base.dao.utils.builder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;

import com.changhong.base.dao.utils.Querys;
/**
 * hql构建器
 * 用于生成hql语句查询字符串以及设置查询参数
 * @author wanghao
 * 2009-07-27-09：45 am
 */
public class HqlBuilder {
	//hql字符串
	private StringBuilder hqlStr;
	
	//Map参数名值对key：参数名称，value：参数值
	private Map<String,Object> paramMap;
	
	public HqlBuilder() {
		hqlStr = new StringBuilder("");
	}
	
	public HqlBuilder(String hql) {
		hqlStr = new StringBuilder(hql);
	}

	/**
	 * 设置参数名值对
	 * @return
	 */
	public HqlBuilder setParam(String name, Object value) {
		getParamMap().put(name, value);
		return this;
	}
	/**
	 * 设置List、set参数
	 * @return
	 */
	public HqlBuilder setParamList(String name, List<?> values) {
		getParamMap().put(name, values);
		return this;
	}
	 /**
     *设置Map参数名值对
     * @param paramMap
     */
	public void setParamMap(Map<String, ?> paramMap) {
		if(paramMap!=null){
			getParamMap().putAll(paramMap);
		}
	}

	private Map<String, Object> getParamMap() {
		if(paramMap==null){
			paramMap = new HashMap<String, Object>();
		}
		return paramMap;
	}
   
	/**
	 * 将builder中的参数设置到query中
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Query setParamsToQuery(Query query) {
		if(paramMap!=null && paramMap.size()>0){
			for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
				Object value = entry.getValue();
				String key = entry.getKey();
				if(value!=null && !"".equals((value+"").trim()) && !"%null%".equals(value+"") && !"%%".equals(value+"")
				   && StringUtils.isNotBlank(key) 
				   && !Querys.ORDER_ATTR.equals(key) && !Querys.ORDER_TYPE.equals(key) && !key.startsWith(Querys.PREFIX)){
					
					if(value instanceof Collection){
						query.setParameterList(key, (Collection)value);
					}else{
						query.setParameter(key,value);
					}
				}
			}
		}
		return query;
	}
	
	/**
	 * 链接 子hql语句
	 * @param
	 * @return
	 */
	public HqlBuilder append(String hql) {
		hqlStr.append(hql);
		return this;
	}
	/**
	 * hql语句字符串
	 * @return
	 */
	public String getQueryHql() {
		return hqlStr.toString();
	}

	/**
	 * 查询记录总数字符串
	 * @return
	 */
	public String getCountHql() {
		String hql = hqlStr.toString();
		int fromIndex = hql.toLowerCase().indexOf("from");
		//"from" 前的select字串，不包括"from"
		String beforeFromHql = "select count(*) ";
		
		//"from"后面 "order by"前面的select字串，包括"from"
		int orderIndex = hql.toLowerCase().indexOf("order by");  
		String afterFromHql = hql.substring(fromIndex, orderIndex);
		
		return beforeFromHql+ afterFromHql;
	}
}
