package com.changhong.base.web.taglib;

import org.apache.commons.lang3.StringUtils;

import com.changhong.base.utils.LocaleUtils;


/**
 * 位置标签
 * @author wanghao
 */
public class PositionTag extends ParentTag{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String positionlable;

	@Override
	public String processResult() {
		String updText = "修改";
		String addText = "新增";
		if(LocaleUtils.EN_US.equals(LocaleUtils.getLocale())){
			 updText = "Modify";
			 addText = "Add A New One";
		}
		if(StringUtils.isNotEmpty(positionlable)){
			return positionlable;
		}
		if(getValue()!=null && StringUtils.isNotEmpty(getValue().toString())){
			return getExValue(getValue())!=null?updText:addText;
		}
		return addText;
	}

	public void setPositionlable(String positionlable) {
		this.positionlable = positionlable;
	}
}
