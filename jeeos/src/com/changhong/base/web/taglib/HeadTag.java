package com.changhong.base.web.taglib;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.opensymphony.xwork2.ActionContext;

/**
 * jsp <head>标签
 * 
 * @author wanghao
 */
public class HeadTag extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private boolean ddaccordion=false;//
	// private boolean crud=true;//是否包含crud操作
	private boolean easyui = false;// 是否加载easyui js
	private boolean lhgdialog = false;// 是否加载lhgdialog js
	private boolean datePicker = false;// 是否加载日期时间 js控件
	private boolean multiFile = false;// 是否加载附件上传 js控件
	private boolean fck = false;// 是否加载fck 编辑器 js控件
	private boolean tree2 = false;// 是否加载zTree 2.0版本的js、css
	private boolean tree3 = false;// 是否加载zTree 3.4版本的js、css
	private boolean textareaAutoresize = false;// textarea是否自动大小
	private boolean aceElement = false;

	// private String locale="zh_CN";

	private StringBuilder result;

	@Override
	public int doStartTag() throws JspException {

		Locale locale = ActionContext.getContext().getLocale();

		// String projectName =
		// PropertiesUtil.getPropertyValue("messages_"+locale+".properties",
		// "global.project.name");

		result = new StringBuilder();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String ctx = request.getContextPath();
		result.append("<head>\n");
		result.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n");
		result.append("<link type=\"text/css\" rel=\"stylesheet\" href=\""
				+ ctx + "/js_css_image/css/main.css\"/>\n");
		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ ctx
				+ "/js_css_image/css/easyui/themes/default/easyui.css\"/>\n");
		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ ctx + "/js_css_image/css/easyui/themes/icon.css\"/>\n");

		/* ace theme */
		result.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n");
		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ ctx + "/css/bootstrap.min.css\"/>\n");
		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ ctx + "/css/bootstrap-responsive.min.css\"/>\n");
		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ ctx + "/css/font-awesome.min.css\"/>\n");
		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ ctx + "/css/ace.min.css\"/>\n");
		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ ctx + "/css/ace-responsive.min.css\"/>\n");
		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ ctx + "/css/ace-skins.min.css\"/>\n");
		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""
				+ ctx + "/css/main.css\"/>\n");

/*		result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://fonts.useso.com/css?family=Open+Sans:400,300\"/>\n");
*/
		result.append("<!--[if IE 7]><link rel=\"stylesheet\" href=\"" + ctx
				+ "/css/font-awesome-ie7.min.css\"/><![endif]-->\n");
		result.append("<!--[if IE 8]><link rel=\"stylesheet\" href=\"" + ctx
				+ "/css/ace-ie.min.css\"/><![endif]-->\n");

		if (easyui) {
			// result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ctx+"/js_css_image/css/easyui/themes/default/easyui.css\"/>\n");
			// result.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+ctx+"/js_css_image/css/easyui/themes/icon.css\"/>\n");

		}
		if (tree2) {
			result.append("<link rel=\"StyleSheet\" type=\"text/css\" href=\""
					+ ctx + "/js_css_image/css/zTree/2.0/zTreeStyle.css\"/>\n");
		}
		if (tree3) {
			result.append("<link rel=\"StyleSheet\" type=\"text/css\" href=\""
					+ ctx + "/js_css_image/css/zTree/3.4/zTreeStyle.css\"/>\n");
		}

		result.append("<!--基础js-->\n");
		// result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/jquery/jquery-1.8.0.min.js\"></script>\n");
		result.append("<script type=\"text/javascript\" src=\"" + ctx
				+ "/js_css_image/css/easyui/jquery-1.7.2.min.js\"></script>\n");
		result.append("<script type=\"text/javascript\" src=\"" + ctx
				+ "/js_css_image/css/easyui/jquery.easyui.min.js\"></script>\n");
		result.append("<script type=\"text/javascript\" src=\""
				+ ctx
				+ "/js_css_image/js/base/jquery.mgm.utils-1.0.js\"></script>\n");
		result.append("<script type=\"text/javascript\" src=\"" + ctx
				+ "/js_css_image/js/base/lang/" + locale.toString()
				+ ".js\"></script>\n");
		// result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/jquery/jquery.easyui.pack.js\"></script>\n");
		if (true/* crud */) {
			result.append("<!--crud 操作js，所有crud操作均采用ajax方式-->\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/base/jquery.mgm.crud.init-1.0.js\"></script>\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/base/jquery.mgm.crud-1.0.js\"></script>\n");
			// easyui
			// form,server返回json时，contentType需为text/html或text/plain，否则将提示下载内容
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/jquery/jquery.easyui.form_1.3.1.js\"></script>\n");
			 result.append("<script type=\"text/javascript\" src=\""+ctx+"/js/jquery-1.7.2.min.js\"></script>\n");
		}
		if (easyui) {
			result.append("<!--布局 js-->\n");
			// result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/jquery/jquery.easyui.min_1.3.1.js\"></script>\n");
			// result.append("<script type=\"text/javascript\" src=\""+ctx+"/js_css_image/js/menu.js\"></script>\n");
		}
		if (lhgdialog) {
			result.append("<!--dialog js-->\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/lhgdialog/lhgdialog/lhgdialog.min.js?skin=idialog\"></script>\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/lhgdialog/lhgdialog/lhgdialog.alert.js?skin=idialog\"></script>\n");
		}
		if (datePicker) {
			result.append("<!--date、time js-->\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/My97DatePicker/WdatePicker.js\" defer=\"defer\"></script>\n");
		}
		if (multiFile) {
			result.append("<!--附件上传 js-->\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/jquery/jquery.MultiFile.js\" ></></script>\n");
		}
		if (fck) {
			result.append("<!--富编辑器 fck js-->\n");
			result.append("<script type=\"text/javascript\"src=\"" + ctx
					+ "/js_css_image/js/fckeditor/fckeditor.js\" ></script>\n");
		}
		if (tree2) {
			result.append("<!--ztree v2.0 js-->\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/zTree/2.0/jquery.ztree-2.6.min.js\"></script>\n");
		}
		if (tree3) {
			result.append("<!--ztree v3.0 js-->\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/zTree/3.4/jquery.ztree.all-3.4.min.js\"></script>\n");
			result.append("<script type=\"text/javascript\" src=\"" + ctx
					+ "/js_css_image/js/base/zTreeExt.js\"></script>\n");
		}
		if (textareaAutoresize) {
			result.append("<!--textarea 自动大小  js-->\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js_css_image/js/jquery/autoresize.jquery.min.js\"></script>\n");
		}
		
		if (aceElement){
			result.append("<!-- ace element js-->\n");
			result.append("<script type=\"text/javascript\" src=\""
					+ ctx
					+ "/js/ace-elements.min.js\"></script>\n");
		}

		if (result != null) {
			JspWriter out = pageContext.getOut();
			try {
				out.print(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Tag.EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		// result.append("</head>\n");
		if (result != null) {
			JspWriter out = pageContext.getOut();
			try {
				out.print("</head>\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Tag.EVAL_PAGE;
	}

	public boolean isEasyui() {
		return easyui;
	}

	public void setEasyui(boolean easyui) {
		this.easyui = easyui;
	}

	public boolean isLhgdialog() {
		return lhgdialog;
	}

	public void setLhgdialog(boolean lhgdialog) {
		this.lhgdialog = lhgdialog;
	}

	public boolean isMultiFile() {
		return multiFile;
	}

	public void setMultiFile(boolean multiFile) {
		this.multiFile = multiFile;
	}

	public boolean isDatePicker() {
		return datePicker;
	}

	public void setDatePicker(boolean datePicker) {
		this.datePicker = datePicker;
	}

	public boolean isFck() {
		return fck;
	}

	public void setFck(boolean fck) {
		this.fck = fck;
	}

	public boolean isTree3() {
		return tree3;
	}

	public void setTree3(boolean tree3) {
		this.tree3 = tree3;
	}

	public boolean isTextareaAutoresize() {
		return textareaAutoresize;
	}

	public void setTextareaAutoresize(boolean textareaAutoresize) {
		this.textareaAutoresize = textareaAutoresize;
	}

	public boolean isTree2() {
		return tree2;
	}

	public void setTree2(boolean tree2) {
		this.tree2 = tree2;
	}
	/*
	 * public String getLocale() { return locale; }
	 * 
	 * public void setLocale(String locale) { this.locale = locale; }
	 */

	public boolean isAceElement() {
		return aceElement;
	}

	public void setAceElement(boolean aceElement) {
		this.aceElement = aceElement;
	}
}
