package com.changhong.base.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.changhong.base.utils.LocaleUtils;

/**
 * 国际化标签
 * @author wanghao
 */
public class I18nTag extends TagSupport{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String zhText;//中文
	private String zhMaxLength;//中文最大长度
	
    private String enText;//英文
    private String enMaxLength;//英文最大长度
    
	public int doEndTag() throws JspException{
		String result = "";
		if(LocaleUtils.EN_US.equals(LocaleUtils.getLocale())){
			result = enText;
			if(StringUtils.isNotBlank(enMaxLength)){
				int length = Integer.parseInt(enMaxLength);
				if(result.length()>length){
					result = result.substring(0,length)+"...";
				}
			}
		}else{
			result = zhText;
			if(StringUtils.isNotBlank(zhMaxLength)){
				int length = Integer.parseInt(zhMaxLength);
				if(result.length()>length){
					result = result.substring(0,length)+"...";
				}
			}
		}
		
		if (result != null){
			JspWriter out = pageContext.getOut();
			try {
				out.print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return EVAL_PAGE;
	}

	public String getZhText() {
		return zhText;
	}

	public void setZhText(String zhText) {
		this.zhText = zhText;
	}

	public String getZhMaxLength() {
		return zhMaxLength;
	}

	public void setZhMaxLength(String zhMaxLength) {
		this.zhMaxLength = zhMaxLength;
	}

	public String getEnText() {
		return enText;
	}

	public void setEnText(String enText) {
		this.enText = enText;
	}

	public String getEnMaxLength() {
		return enMaxLength;
	}

	public void setEnMaxLength(String enMaxLength) {
		this.enMaxLength = enMaxLength;
	}
}
