package com.changhong.base.utils;

import com.opensymphony.xwork2.ActionContext;
/**
 * @author wanghao
 */
public class LocaleUtils {
	
	public static final String EN_US = "en_US";
	public static final String ZH_CN = "zh_CN";
	
	public static String getLocale(){
		return ActionContext.getContext().getLocale().toString();
	}
	/**
	 * 获得国际化数据
	 * @param zhText 中文数据
	 * @param enText 英文数据
	 * @return
	 */
	public static String getLocaleText(String zhText,String enText){
		String result = "";
		if(EN_US.equals(getLocale())){
			result = enText;
		}else{
			result = zhText;
		}
		return result;
	}
}
