package com.changhong.base.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.changhong.base.entity.Page;
/**
 * 分页页面，每一行的序号 标签
 * @author wanghao
 */
public class RowNumTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Page page;
	private int rowIndex;
	
	public int doEndTag() throws JspException{
		int currentPage = page.getCurrentPage();
		int pageSize = page.getPageSize();
		int rowNum = (currentPage-1)*pageSize+(rowIndex+1);
		
		JspWriter out = pageContext.getOut();
		try {
			out.print(rowNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}


}
