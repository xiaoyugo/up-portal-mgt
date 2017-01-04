package com.changhong.base.utils;


/**
 * 解析字符串
 * @author wanghao
 * 2013.07.02 17:22
 */
public class ParseString {

	/**
	 * 将用逗号分隔开的appid解析出来放到数组中
	 * @param bindApps 用逗号分隔的字符串
	 * @return 解析后的数组
	 */
	public final static String[] parseBindApps(String bindApps,String mark){
		String[] strList = bindApps.split(mark);
		return strList;
	}
}
