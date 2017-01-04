package com.changhong.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import com.changhong.base.constants.Constants;
import com.changhong.base.constants.enums.YN;
import com.changhong.base.dao.cache.CacheConstants;
import com.changhong.base.dao.cache.EhCacheManager;
import com.changhong.base.utils.DateUtils;
import com.changhong.base.utils.FastMap;
import com.changhong.base.utils.string.EncodeUtils;
import com.changhong.base.web.Servlets;
import com.changhong.base.web.Sessions;
import com.changhong.base.web.render.JsonResult;
import com.changhong.base.web.render.Renders;
import com.changhong.system.entity.ChRole;
import com.changhong.system.entity.ChUser;
import com.changhong.system.service.RoleService;
import com.changhong.system.service.UserServiceCh;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 登录与退出action
 * 
 * @author wanghao
 */
@Controller("system.action.LoginActionCh")
@Scope("prototype")
public class LoginActionCh extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private static final Log log = LogFactory.getLog(LoginAction.class);

	private String loginName;
	private String password;
	private String cookieRememberme;// cookie字符串
	private String loginMessage;
	private boolean remember;

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	@Autowired
	private UserServiceCh userService;
	@Autowired
	private RoleService roleService;

	public String create() {
		return null;
	}

	/**
	 * 登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String login() throws Exception {

		try {


			HttpSession session = Servlets.getSession();
			ChUser chUser = Sessions.getChUser();

			if (chUser != null && StringUtils.isNotEmpty(chUser.getId())) {// session存在，重新登录
				session.removeAttribute(Constants.CH_USER_SESSION_KEY);
				chUser = null;
			}

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap = new FastMap().newHashMap().set("loginName", loginName)
					.set("passWord", EncodeUtils.encodeMd5(password));

			chUser = userService.findOne("system.findChUsers", paramMap);

			if (chUser != null) {
				List<ChRole> chRoles = new ArrayList<ChRole>(0);
				String roleIds = chUser.getChUserRoleids();
				if (roleIds != null) {
					String[] roleID = roleIds.split(", ");
					for (String roleid : roleID) {
						ChRole cR = roleService.get(roleid);
						chRoles.add(cR);
					}
					chUser.setChRoles(chRoles);
					chUser.setChRole(chUser.getChRoles().get(0));
					chUser.setRoleId(chUser.getChRoles().get(0).getId());
				}
				chUser.setLoginTime(DateUtils.getCurrentDate());

				// 更新用户登录IP
				chUser.setChUserIp(Servlets.getClientIp());
				userService.update(chUser);
				session.setAttribute(Constants.CH_USER_SESSION_KEY, chUser);
				// 如果点击了记住密码
				if (!remember) {
					// 将当前成功登录的用户保存到session中
					Servlets.getSession().setAttribute("user", chUser);
					chUser.setChUsername(loginName);
					chUser.setChUserPassword(password);
					// 判断用户是否选中了“记住密码”，如果选中则放到Cookie总
					if (password != null) {
						// 创建用户名Cookie
						Cookie nameCookie = new Cookie("loginName", loginName);
						nameCookie.setPath("/"); 
						// 创建密码Cookie
						Cookie passwordCookie = new Cookie("password",password);
						passwordCookie.setPath("/");
						// 设置cookie的存活时间为30分钟
						nameCookie.setMaxAge(30 * 60);
						passwordCookie.setMaxAge(30 * 60);
						// 将用户名和密码存放到客户端的Cookie中
						
						
						ActionContext ctx = ActionContext.getContext();
						HttpServletResponse response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);

						response.addCookie(nameCookie);
						response.addCookie(passwordCookie);
					}
				}

				Renders.renderJson(new JsonResult("success"));
				return NONE;
			} else {
				Renders.renderJson(new JsonResult("用户名或密码不正确"));
				return NONE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Renders.renderJson(new JsonResult("系统出现异常"));
			return NONE;
		}
	}

	/**
	 * 注销
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String logout() throws Exception {
		HttpServletRequest request = Servlets.getRequest();
		HttpSession session = request.getSession(false);
		// 清空session
		if (session != null) {
			ChUser chUser = Sessions.getChUser();
			Map<String, YN> ruleSettingMap = (Map<String, YN>) EhCacheManager
					.get(CacheConstants.CONSTANTS_DATE_CACHE,
							CacheConstants.CH_RULE_SETTING_KEY);
			if (chUser != null && ruleSettingMap.get("loginLog") == YN.Y) {
				session.removeAttribute(Constants.CH_USER_SESSION_KEY);
			}
			session.invalidate();
		}
		return "logout";
	}

	// --------------------以下是getter/setter方法----------------------------------------

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginMessage() {
		return loginMessage;
	}

	public void setLoginMessage(String loginMessage) {
		this.loginMessage = loginMessage;
	}

	public String getCookieRememberme() {
		return cookieRememberme;
	}

	public void setCookieRememberme(String cookieRememberme) {
		this.cookieRememberme = cookieRememberme;
	}
}
