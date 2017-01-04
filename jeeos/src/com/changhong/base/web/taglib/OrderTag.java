package com.changhong.base.web.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * table 排序标签
 * @author wanghao
 */
public class OrderTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 页面上需要排序的属性
	 */
	private String orderattr;

	public int doEndTag() throws JspException{
		String result = processResult();
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
	public String processResult() {
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String contextPath = request.getContextPath();
		String varName =request.getParameter("pageVarName");//Page 变量在action中的名词，默认为page
		String curorderattr = request.getParameter(varName+".orderattr");//当前正在排序的列
		String curordertype = request.getParameter(varName+".ordertype");//升序或降序
		
		StringBuilder sb = new StringBuilder("");
		
		sb.append("<a style=\"text-decoration: none\"  href=\"##\" class=\"orderBy\" ordertype=\"").append("desc".equals(curordertype)?"asc":"desc").append("\" rel=\"").append(orderattr).append("\">");
        if(StringUtils.isNotBlank(curorderattr) && curorderattr.equals(orderattr)){
        	if("desc".equals(curordertype)){
    			sb.append("&nbsp;<i class=\"icon-sort-down\"></i>");
    		}else if("asc".equals(curordertype)){
    			sb.append("&nbsp;<i class=\"icon-sort-up\"></i>");
    		}
        }else{
        	sb.append("&nbsp;<i class=\"icon-sort\"></i>");
        }
		sb.append("</a>");
		return sb.toString();
	}

	public String getOrderattr() {
		return orderattr;
	}

	public void setOrderattr(String orderattr) {
		this.orderattr = orderattr;
	}
}
