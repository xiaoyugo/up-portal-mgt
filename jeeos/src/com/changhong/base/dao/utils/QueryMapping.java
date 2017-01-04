package com.changhong.base.dao.utils;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 外部查询语句映射类
 * 对应*.query.xml文件
 * @author wanghao
 */
@XmlRootElement
public class QueryMapping {
	
	/**
	 * 查询的类型
	 * type:hql或sql
	 */
	//private String type;
	/**
	 * 每条查询List集合
	 */
	private List<Query> query;
	
/*	@XmlAttribute
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}*/
	
	@XmlElement
	public List<Query> getQuery() {
		return query;
	}
	public void setQuery(List<Query> query) {
		this.query = query;
	}
	
	/**
	 * 
	 *每一个查询，作为内部类存在
	 */
	@XmlRootElement
	public static class Query {
		/**
		 * 查询名称，必须唯一
		 */
		private String name;
		
		/**
		 * hql语句
		 */
		private String hql;
		/**
		 * sql语句
		 */
		private String sql;
		
		@XmlAttribute
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		@XmlElement
		public String getHql() {
			return hql;
		}
		public void setHql(String hql) {
			this.hql = hql;
		}
		@XmlElement
		public String getSql() {
			return sql;
		}
		public void setSql(String sql) {
			this.sql = sql;
		}
	}


}
