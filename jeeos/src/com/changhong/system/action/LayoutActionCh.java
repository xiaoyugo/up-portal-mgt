package com.changhong.system.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.changhong.base.dao.cache.CacheConstants;
import com.changhong.base.dao.cache.EhCacheManager;
import com.changhong.base.entity.Page;
import com.changhong.base.utils.FastMap;
import com.changhong.base.web.Sessions;
import com.changhong.base.web.render.Renders;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChRole;
import com.changhong.system.entity.ChRolebutton;
import com.changhong.system.entity.ChRolefunc;
import com.changhong.system.entity.ChUser;
import com.changhong.system.entity.ChUserfunc;
import com.changhong.system.service.RoleButtonService;
import com.changhong.system.service.RoleService;
import com.changhong.system.service.RolefuncService;
import com.changhong.system.service.UserServiceCh;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 系统页面布局
 * 
 * @author wanghao
 * 
 */
@Controller("system.action.LayoutActionCh")
@Scope("prototype")
public class LayoutActionCh extends ActionSupport implements Preparable {

	private static final Log log = LogFactory.getLog(LayoutActionCh.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Page page = new Page(100);
	private ChUser user;
	private List<ChFunction> menuSet;
	private List<ChFunction> menuList;
	private String changeRoleId;

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserServiceCh userService;
	@Autowired
	private RoleButtonService buttonService;
	@Autowired
	private RolefuncService rolefuncService;

	public void prepare() throws Exception {
		// 第一次登录从session中获得用户，登录以后的页面刷新不再重复从session中获得
		if (user == null) {
			user = Sessions.getChUser();
		}
	}

	@SuppressWarnings("unchecked")
	public String layout() throws Exception {
		ChRole chRole = new ChRole();
		if (user.getChUserRoleids() != null) {

			if (StringUtils.isEmpty(changeRoleId)) {
				chRole = roleService.get(user.getChRole().getId());
			} else {// 切换角色
				chRole = roleService.get(changeRoleId);
			}
		}
		// 获得菜单层级结构，下面的办法真的很烂

		menuSet = new ArrayList(chRole.getChFuncs());
		// Find function has been removed from user self define and remove it.
		Map<String, String> paramMap = new FastMap().newHashMap().set("roleId", chRole.getId()).set("userId", user.getId());
		List<ChUserfunc> userFuncList = userService.findUserFunc(paramMap);
		List<ChFunction> removedFuncList = new ArrayList<ChFunction>();
		if (CollectionUtils.isNotEmpty(userFuncList)) {
			ChFunction func;
			for (ChUserfunc userFunc : userFuncList) {
				func = userFunc.getChFunc();
				if (func != null) {
					removedFuncList.add(func);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(menuSet)) {
			Iterator<ChFunction> iter = menuSet.iterator();
			while (iter.hasNext()) {
				ChFunction func = iter.next();
				for (ChFunction removedFunc : removedFuncList) {
					if (func.getId().equals(removedFunc.getId())) {
						iter.remove();
					}
				}
			}
		}

		// 对菜单进行排序
		Collections.sort(menuSet, new Comparator() {
			public int compare(Object o1, Object o2) {
				String num1 = ((ChFunction) (o1)).getChFuncSortno();
				String num2 = ((ChFunction) (o2)).getChFuncSortno();
				return (num1).compareTo(num2);
			}
		});

		List<String> menuPaths = new ArrayList<String>();
		if (menuSet != null && menuSet.size() > 0) {
			menuList = new ArrayList<ChFunction>();
			List<ChFunction> subList = null;
			for (ChFunction m : menuSet) {// 获得一级菜单
				if (m.getChFunc() != null) {
					if ("1".equals(m.getChFunc().getId())) {
						menuList.add(m);
					}
				}
				if (StringUtils.isNotBlank(m.getChFuncPath())) {
					menuPaths.add(m.getChFuncPath());
				}
			}

			if (menuList.size() > 0) {
				for (ChFunction m : menuList) {// 获得二级菜单
					subList = new ArrayList<ChFunction>();
					for (ChFunction m1 : menuSet) {
						if (m1.getChFunc() != null) {
							if (m.getId().equals(m1.getChFunc().getId())) {
								subList.add(m1);
							}
						}
					}
					m.setSubList(subList);
				}

				for (ChFunction m : menuList) {// 获得三级菜单
					for (ChFunction m2 : m.getSubList()) {
						subList = new ArrayList<ChFunction>();
						for (ChFunction m1 : menuSet) {
							if (m1.getChFunc() != null) {
								if (m2.getId().equals(m1.getChFunc().getId())) {
									subList.add(m1);
								}
							}
						}
						m2.setSubList(subList);
					}
				}
			}

		}

		if (CollectionUtils.isNotEmpty(menuList)) {
			int i = 0;
			for (ChFunction func : menuList) {
				switch (i) {
				case 0:
					func.setChFuncImage("icon-desktop");
					break;
				case 1:
					func.setChFuncImage("icon-list");
					break;
				case 2:
					func.setChFuncImage("icon-list-alt");
					break;
				case 4:
					func.setChFuncImage("icon-text-width");
					break;
				default:
					func.setChFuncImage("icon-dashboard");
					break;
				}
				i++;
			}
		}
		user.setRoleId(chRole.getId());
		user.setChRole(chRole);
		user.setFuncPaths(menuPaths);

		List<ChFunction> funcList = findFuncListByRoleId(chRole.getId());
		Map<String, List<ChRolebutton>> funcBtnMap = new HashMap<String, List<ChRolebutton>>();
		if (CollectionUtils.isNotEmpty(funcList)) {
			for (ChFunction func : funcList) {
				List<ChRolebutton> chRoleButtonList = buttonService.findButtonsByRoleAndFunc(new FastMap().newHashMap().set("funcId", func.getId())
						.set("roleId", chRole.getId()));
				funcBtnMap.put(func.getId(), chRoleButtonList);
			}
		}
		user.setFuncBtnMap(funcBtnMap);

		return "layout";
	}

	private List<ChFunction> findFuncListByRoleId(String roleId) throws Exception {
		ChRole role = roleService.get(roleId);
		Map<String, String> paramMap = new FastMap().newHashMap().set("roleId", roleId);
		List<ChRolefunc> chRolefuncs = rolefuncService.findList("system.findRoleFuncs", paramMap);

		List<ChFunction> funcList = new ArrayList<ChFunction>();
		if (chRolefuncs != null && chRolefuncs.size() > 0) {
			for (ChRolefunc f : chRolefuncs) {
				ChFunction func = new ChFunction();
				String funcPath = f.getChFunc().getChFuncPath();
				if (!funcPath.equals("") && funcPath != "" && funcPath != null) {
					func.setId(f.getChFunc().getId());
					func.setChFuncName(f.getChFunc().getChFuncName());
					funcList.add(func);
				}
			}
		}
		return funcList;
	}

	/**
	 * 在线用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listOnlineUser() throws Exception {
		// 获得当前在线用户的id List
		List<String> userIdList = new ArrayList<String>();
		Map<String, ChUser> onlineUserMap = (Map<String, ChUser>) EhCacheManager.getAll(CacheConstants.HTTP_SESSION_CACHE);

		Collection<ChUser> users = onlineUserMap.values();
		Iterator<ChUser> iterator = users.iterator();
		while (iterator.hasNext()) {
			userIdList.add(iterator.next().getId());
		}
		page = userService.findUsers(page, "", "", userIdList);

		return "onlineUser";
	}

	/**
	 * 查询在线用户数量
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getOnlineUserCount() throws Exception {
		Renders.renderHtml(EhCacheManager.getAll(CacheConstants.HTTP_SESSION_CACHE).size());
		return NONE;
	}

	public ChUser getUser() {
		return user;
	}

	public void setUser(ChUser user) {
		this.user = user;
	}

	public List<ChFunction> getMenuSet() {
		return menuSet;
	}

	public void setMenuSet(List<ChFunction> menuSet) {
		this.menuSet = menuSet;
	}

	public List<ChFunction> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<ChFunction> menuList) {
		this.menuList = menuList;
	}

	public String getChangeRoleId() {
		return changeRoleId;
	}

	public void setChangeRoleId(String changeRoleId) {
		this.changeRoleId = changeRoleId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
