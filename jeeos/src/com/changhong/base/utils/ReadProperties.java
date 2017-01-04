package com.changhong.base.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 用来读取properties中的信息
 * 
 * @author wanghao
 */
public class ReadProperties {

	public static String FILE_COMMON = "/resources/common/params.properties";

	public static Properties loadSetting(String uri) {
		Properties prop = new Properties();
		InputStream is = ReadProperties.class.getResourceAsStream(uri);
		if (is != null) {
			try {
				prop.load(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return null;
		}
		return prop;
	}

	/**
	 * 读取iconDirector.properties中的路径信息
	 * 
	 * @param key
	 *            读取的属性
	 * @return
	 */
	public static String read(String key) {
		String str = null;
		Properties prop = loadSetting(FILE_COMMON);
		if (prop != null) {
			str = prop.getProperty(key);
		}
		return str;
	}
}
