package com.changhong.base.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.changhong.base.entity.Page;
import com.changhong.base.utils.LocaleUtils;

/**
 * 分页标签
 * @author wanghao
 */
public class PageTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * page 值
	 */
	private Page page;
	/**
	 * 样式类型，目前只有一种样式
	 */
	private String style;
	/**
	 * Page 变量在action中的名词，默认为page
	 */
	private String varName;
	
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
		
		int currentPage=page.getCurrentPage();
		int previousPage=page.getPreviousPage();;
		int nextPage=page.getNextPage();
		int totalPage=page.getTotalPage();
		int totalRecords=page.getTotalRecords();
		int pageSize=page.getPageSize();
		String orderattr=page.getOrderattr();
		String ordertype=page.getOrdertype();
		
		varName = StringUtils.isBlank(varName)?"page":varName;
		
		StringBuilder sb = new StringBuilder("");
		sb.append("\n<input type=\"hidden\" name=\"pageVarName\"  value=\""+varName+"\"/>\n");
		sb.append("<input type=\"hidden\" id=\"currentPage\" name=\""+varName+".currentPage\"  value=\""+currentPage+"\"/>\n");
		sb.append("<input type=\"hidden\" id=\"totalPage\" name=\""+varName+".totalPage\" value=\""+totalPage+"\"/>\n");
		sb.append("<input type=\"hidden\" id=\"orderattr\" name=\""+varName+".orderattr\" value=\""+orderattr+"\"/>\n");
		sb.append("<input type=\"hidden\" id=\"ordertype\" name=\""+varName+".ordertype\" value=\""+ordertype+"\"/>\n");
		
		if("new page style".equals(style)){
			
		}else{//默认样式
			if(LocaleUtils.EN_US.equals(LocaleUtils.getLocale())){
				sb.append("Current:"+currentPage+" \n");
				sb.append("Total:"+totalPage+" \n");
				sb.append("Total Entries:<span id=\"pageTotalRecords\">"+totalRecords+"</span>| \n");
				
				//首页、上一页
				if(previousPage<=0){
					sb.append("<font color=\"#999999\">First</font>| \n");
				}
				if(previousPage>0){
					sb.append("<a href=\"javascript:toPage($('#totalPage')[0].form,1);\">First</a> | \n");
				}
				if(previousPage<=0){
					sb.append("<font color=\"#999999\">Previou</font>| \n");
				}
				if(previousPage>0){
					sb.append("<a href=\"javascript:toPage($('#totalPage')[0].form,'"+previousPage+"');\">Previou</a> | \n");
				}
				
				//下一页、末页
				if(nextPage<=0){
					sb.append("<font color=\"#999999\">Next</font>| \n");
				}
				if(nextPage>0){
					sb.append("<a href=\"javascript:toPage($('#totalPage')[0].form,'"+nextPage+"');\">Next</a> | \n");
				}
				if(nextPage<=0){
					sb.append("<font color=\"#999999\">Last</font>| \n");
				}
				if(nextPage>0){
					sb.append("<a href=\"javascript:toPage($('#totalPage')[0].form,'"+totalPage+"');\">Last</a> | \n");
				}
				String pageSize_10="",pageSize_20="",pageSize_30="",pageSize_40="" ,pageSize_50="";
				if(pageSize==10){pageSize_10="selected=\"selected\"";}
				else if(pageSize==10){pageSize_10="selected=\"selected\"";}
				else if(pageSize==20){pageSize_20="selected=\"selected\"";}
				else if(pageSize==30){pageSize_30="selected=\"selected\"";}
				else if(pageSize==40){pageSize_40="selected=\"selected\"";}
				else if(pageSize==50){pageSize_50="selected=\"selected\"";}
				else{pageSize_10="selected=\"selected\"";}
				
				sb.append("Per<select style=\"width:80px\" name=\""+varName+".pageSize\" id=\"page_pageSize\" onchange=\"toPage($('#totalPage')[0].form,'1');\">\n");
				sb.append("<option value=\"10\" "+pageSize_10+">10</option>" +
						"<option value=\"20\" "+pageSize_20+">20</option>" +
						"<option value=\"30\" "+pageSize_30+">30</option>" +
						"<option value=\"40\" "+pageSize_40+">40</option>" +
						"<option value=\"50\" "+pageSize_50+">50</option>\n");
				sb.append("</select>Entries\n");
			}else{
				sb.append("第"+currentPage+"页 \n");
				sb.append("共"+totalPage+"页  \n");
				sb.append("共<span id=\"pageTotalRecords\">"+totalRecords+"</span>条| \n");
				
				//首页、上一页
				if(previousPage<=0){
					sb.append("<font color=\"#999999\">首页</font>| \n");
				}
				if(previousPage>0){
					sb.append("<a href=\"javascript:toPage($('#totalPage')[0].form,1);\">首页</a> | \n");
				}
				if(previousPage<=0){
					sb.append("<font color=\"#999999\">上一页</font>| \n");
				}
				if(previousPage>0){
					sb.append("<a href=\"javascript:toPage($('#totalPage')[0].form,'"+previousPage+"');\">上一页</a> | \n");
				}
				
				//下一页、末页
				if(nextPage<=0){
					sb.append("<font color=\"#999999\">下一页</font>| \n");
				}
				if(nextPage>0){
					sb.append("<a href=\"javascript:toPage($('#totalPage')[0].form,'"+nextPage+"');\">下一页</a> | \n");
				}
				if(nextPage<=0){
					sb.append("<font color=\"#999999\">末页</font>| \n");
				}
				if(nextPage>0){
					sb.append("<a href=\"javascript:toPage($('#totalPage')[0].form,'"+totalPage+"');\">末页</a> | \n");
				}
				String pageSize_10="",pageSize_20="",pageSize_30="",pageSize_40="" ,pageSize_50="" ;
				if(pageSize==10){pageSize_10="selected=\"selected\"";}
				else if(pageSize==10){pageSize_10="selected=\"selected\"";}
				else if(pageSize==20){pageSize_20="selected=\"selected\"";}
				else if(pageSize==30){pageSize_30="selected=\"selected\"";}
				else if(pageSize==40){pageSize_40="selected=\"selected\"";}
				else if(pageSize==50){pageSize_50="selected=\"selected\"";}
				else{pageSize_10="selected=\"selected\"";}
				
				sb.append("每页<select style=\"width:80px\" name=\""+varName+".pageSize\" id=\"page_pageSize\" onchange=\"toPage($('#totalPage')[0].form,'1');\">\n");
				sb.append("<option value=\"10\" "+pageSize_10+">10</option>" +
						"<option value=\"20\" "+pageSize_20+">20</option>" +
						"<option value=\"30\" "+pageSize_30+">30</option>" +
						"<option value=\"40\" "+pageSize_40+">40</option>" +
						"<option value=\"50\" "+pageSize_50+">50</option>\n");
				sb.append("</select>条\n");
			}
		}
		//js排序
		sb.append("<script type=\"text/javascript\">\n");
		    //排序
	    	sb.append("$(function(){\n");
		      sb.append("$('a.orderBy').click(function() {\n");
		        sb.append("$('#orderattr').val($(this).attr('rel'));\n");
	            sb.append("if($(this).attr('ordertype')==''){\n");
		               sb.append("$('#ordertype').val('desc');\n");
		        sb.append("}else{\n");
		               sb.append("$('#ordertype').val($(this).attr('ordertype'));\n");
		        sb.append("}\n");
		        sb.append("$('#totalPage')[0].form.submit();\n");
		     sb.append("});\n");
		   sb.append("});\n");
		   
		   //去指点的页
		   sb.append("function toPage(form,currentPage){\n");
		       sb.append("$('#currentPage').val(currentPage);\n");
		       sb.append("form.submit();\n");
		   sb.append("}\n");
		 sb.append("</script>\n");
		 
		return sb.toString();
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

}
