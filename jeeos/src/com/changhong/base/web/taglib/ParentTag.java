package com.changhong.base.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
/**
 * 
 * @author wanghao
 */
public abstract class ParentTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//具体值或el表达式
	private Object value;
	
	//以下为html元素
	private String id;
	private String name;
	private String styleClass;
	private String size;
	private String style;
	private String title;
	private String disabled;
	private String multiple;
	private String onblur;
	private String onchange;
	private String onclick;
	private String onfocus;
	private String ondblclick;
	private String onmousedown;
	private String onmousemove;
	private String onmouseout;
	private String onmouseover;
	private String onmouseup;
	private String onkeydown;
	private String onkeypress;
	private String onkeyup;
	
	

	/**
	 * 具体tag类实现该方法
	 * @return
	 */
	public abstract String processResult();
	
	/**
	 * 
	 * @param value 具体值或EL表达式
	 * @return
	 * @throws JspException
	 */
	public Object getExValue(Object value){
         try {
        	if(value!=null){
        		return ExpressionEvaluatorManager.evaluate(value+"", value.toString(), Object.class, this, pageContext);
        	}
		} catch (JspException e) {
			e.printStackTrace();
		}  
		return null;
	}
	
	/**
	 *  EVAL_BODY_INCLUDE    当doStartTag()返回时，指明servlet应对标签体进行评估。
		SKIP_BODY    当doStartTag()返回时，指明servlet应忽视标签体。
		EVAL_PAGE    当doEndTag()返回时，指明页面其余部分应被评估。
		SKIP_PAGE    当doEndTag()返回时，指明页面其余部分就被跳过。
	 */
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
	
	public String getHtmlAttributes(){
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(name)){
			sb.append(" name=\"" + name + "\"");
		}
		if (StringUtils.isNotEmpty(id)){
			sb.append(" id=\"" + id + "\"");
		}
		if (StringUtils.isNotEmpty(styleClass)){
			sb.append(" class=\"" + styleClass + "\"");
		}
		if (StringUtils.isNotEmpty(disabled)){
			sb.append(" disabled=\"" + disabled + "\"");
		}
		if (StringUtils.isNotEmpty(multiple)){
			sb.append(" multiple=\"" + multiple + "\"");
		}
		if (StringUtils.isNotEmpty(onblur)){
			sb.append(" onblur=\"" + onblur + "\"");
		}
		if (StringUtils.isNotEmpty(onchange)){
			sb.append(" onchange=\"" + onchange + "\"");
		}
		if (StringUtils.isNotEmpty(onclick)){
			sb.append(" onclick=\"" + onclick + "\"");
		}
		if (StringUtils.isNotEmpty(onfocus)){
			sb.append(" onfocus=\"" + onfocus + "\"");
		}
		if (StringUtils.isNotEmpty(ondblclick)){
			sb.append(" ondblclick=\"" + ondblclick + "\"");
		}
		if (StringUtils.isNotEmpty(onmousedown)){
			sb.append(" onmousedown=\"" + onmousedown + "\"");
		}
		if (StringUtils.isNotEmpty(onmousemove)){
			sb.append(" onmousemove=\"" + onmousemove + "\"");
		}
		if (StringUtils.isNotEmpty(onmouseout)){
			sb.append(" onmouseout=\"" + onmouseout + "\"");
		}
		if (StringUtils.isNotEmpty(onmouseover)){
			sb.append(" onmouseover=\"" + onmouseover + "\"");
		}
		if (StringUtils.isNotEmpty(onmouseup)){
			sb.append(" onmouseup=\"" + onmouseup + "\"");
		}
		if (StringUtils.isNotEmpty(size)){
			sb.append(" size=\"" + size + "\"");
		}
		if (StringUtils.isNotEmpty(style)){
			sb.append(" style=\"" + style + "\"");
		}
		if (StringUtils.isNotEmpty(onkeydown)){
			sb.append(" onkeydown=\"" + onkeydown + "\"");
		}
		if (StringUtils.isNotEmpty(onkeypress)){
			sb.append(" onkeypress=\"" + onkeypress + "\"");
		}
		if (StringUtils.isNotEmpty(onkeyup)){
			sb.append(" onkeyup=\"" + onkeyup + "\"");
		}
		if (StringUtils.isNotEmpty(title)){
			sb.append(" title=\"" + title + "\"");
		}

		return sb.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getOnblur() {
		return onblur;
	}

	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getOnfocus() {
		return onfocus;
	}

	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	public String getOndblclick() {
		return ondblclick;
	}

	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	public String getOnmousedown() {
		return onmousedown;
	}

	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	public String getOnmousemove() {
		return onmousemove;
	}

	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	public String getOnmouseout() {
		return onmouseout;
	}

	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	public String getOnmouseover() {
		return onmouseover;
	}

	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	public String getOnmouseup() {
		return onmouseup;
	}

	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	public String getOnkeydown() {
		return onkeydown;
	}

	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	public String getOnkeypress() {
		return onkeypress;
	}

	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	public String getOnkeyup() {
		return onkeyup;
	}

	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	
}
