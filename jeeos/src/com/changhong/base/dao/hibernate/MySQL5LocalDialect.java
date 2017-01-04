package com.changhong.base.dao.hibernate;

import org.hibernate.dialect.MySQLInnoDBDialect;
/**
 * MySQLDialect 支持中文排序
 * @author wanghao
 */
public class MySQL5LocalDialect extends MySQLInnoDBDialect/*MySQL5Dialect*/{//jbpm4.4 需要是MySQLInnoDBDialect，mysql从5.1开始就不再支持innodb引擎，需要装一个插件
	@SuppressWarnings("deprecation")
	public MySQL5LocalDialect() {
	   super();
	   //在mysql中使用默认字符集为utf8，对中文内容进行排序时，排出来的顺序乱七八糟；
	   //不需要改变表定义及默认编码，只要在查询时将字段先转换成gbk编码再排序
	   //Hibernate.STRING是hibernate3中的，hibernate4中没有了
//	   registerFunction("convert_gbk",new SQLFunctionTemplate(Hibernate.STRING, "convert(?1 using gbk)") );  
   }
}
