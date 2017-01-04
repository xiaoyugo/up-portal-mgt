package com.changhong.base.web;

import javax.servlet.http.HttpSession;

import com.changhong.base.constants.Constants;
import com.changhong.system.entity.ChUser;
/**
 * 1、获得session id
 * 2、从session中获取当前用户
 * 3、存放新的用户信息到session
 * @author wanghao
 */
public class Sessions {
	
	public Sessions() {
		
	}
	
	public static HttpSession getSession(){
		return Servlets.getRequest().getSession();
	}
	
	/**
	 * 获得sessionid
	 * @return session id
	 */
	public static String getSessionId(){
		return Servlets.getRequest().getSession().getId();
	}

		/**
	 * 从session中取到当前用户
	 * @author wanghao
	 */
	public static ChUser getChUser(){
		ChUser chUser = null;
		HttpSession session =  Servlets.getSession();
		if(session!=null){
			chUser = (ChUser) session.getAttribute(Constants.CH_USER_SESSION_KEY);
		}
		return chUser;
	}
	/**
	 * 设置新的用户信息到session
	 * @param chUser 新的用户
	 * @author wanghao
	 */
	public static ChUser setNewChUser(ChUser chUser){
		HttpSession session =  Servlets.getSession();
		if(session!=null){
			session.setAttribute(Constants.CH_USER_SESSION_KEY, chUser);
		}
		return chUser;
	}
}
