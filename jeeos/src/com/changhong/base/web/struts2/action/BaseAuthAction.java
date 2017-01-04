package com.changhong.base.web.struts2.action;

import com.changhong.base.constants.enums.Auth;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 权限action基类
 * 
 * 一般情况下，它是所有其他action的基类
 * 
 * @author wanghao
 */
public abstract class BaseAuthAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 访问权限
	 * 全部权限、部分权限、无访问权限
	 */
	private Auth auth=Auth.W;
	
	public Auth getAuth() {
		return auth;
	}
	public void setAuth(Auth auth) {
		this.auth = auth;
	}
}
