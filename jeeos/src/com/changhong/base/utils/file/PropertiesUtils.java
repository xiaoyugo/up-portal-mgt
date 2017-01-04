package com.changhong.base.utils.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 读取properties 文件
 * 请确保该文件位于classpath根目录下
 * @author wanghao
 */
public class PropertiesUtils {

	private static Log log = LogFactory.getLog(PropertiesUtils.class);
	/**
	 * 
	 * @param propertyFile properties文件名
	 * @param propertyKey  键名称
	 * @return 键值
	 */
	public static String getPropertyValue(String propertyFile,String propertyKey){
		String propertyValue = "";
		if(!"".equals(propertyFile) && propertyFile!=null){
			 Properties prop = new Properties(); 
		     InputStream in = PropertiesUtils.class.getResourceAsStream("/"+propertyFile); 
		     try {
				prop.load(in);
				propertyValue = prop.getProperty(propertyKey).trim(); 
			} catch (IOException e) {
				log.error("can not load property file." + propertyFile, e);
			} finally {   
				IOUtils.closeQuietly(in);
            }
		}
		return propertyValue;
	}
}
