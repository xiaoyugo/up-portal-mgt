package com.changhong.base.dao.utils;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 外部查询语句文件根文件
 * 对应query.xml文件
 * @author wanghao
 */
@XmlRootElement
public class QueryFiles {
	
	private List<Query> query;
	
	@XmlElement
	public List<Query> getQuery() {
		return query;
	}
	public void setQuery(List<Query> query) {
		this.query = query;
	}

	public static class Query {
		
		private String file;
		
		@XmlAttribute
		public String getFile() {
			return file;
		}
		public void setFile(String file) {
			this.file = file;
		}
	}
	
}
