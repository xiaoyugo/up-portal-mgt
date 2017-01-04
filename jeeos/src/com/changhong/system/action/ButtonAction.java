package com.changhong.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.changhong.base.annotation.Menu;
import com.changhong.base.constants.Constants;
import com.changhong.base.entity.Page;
import com.changhong.base.utils.FastMap;
import com.changhong.base.web.Servlets;
import com.changhong.base.web.render.JsonResult;
import com.changhong.base.web.render.Renders;
import com.changhong.base.web.struts2.action.BaseCrudAction;
import com.changhong.system.entity.ChButton;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChRole;
import com.changhong.system.entity.ChRolebutton;
import com.changhong.system.entity.ChRolefunc;
import com.changhong.system.service.ButtonService;
import com.changhong.system.service.RoleButtonService;
import com.changhong.system.service.FuncService;
import com.changhong.system.service.RoleService;
import com.changhong.system.service.RolefuncService;

/**
 * 按钮管理action
 * 
 * @author wanghao 2013-07-25
 */
@Controller("system.action.ButtonAction")
@Scope("prototype")
public class ButtonAction extends BaseCrudAction<ChRolebutton> {

	private static final long serialVersionUID = 4263349098215921129L;

	private String id;
	private ChRolebutton roleButton;
	private List<ChFunction> funcList;
	private List<ChRolefunc> roleFuncList;
	private List<ChRole> roleList;
	private List<ChRolebutton> chRolebuttons;
	private ChRolebutton button;
	private String roleName;
	private String funcName;
	private String roleId;
	private String funcId;
	private Page page = new Page(Constants.PAGE_SIZE);
	private List<ChButton> buttonList;
	private List<ChRolebutton> haveButtonList;
	private String buttonIds;

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleButtonService roleButtonService;
	@Autowired
	private ButtonService buttonService;
	@Autowired
	private RolefuncService rolefuncService;
	@Autowired
	private FuncService funcService;

	/**
	 * 给菜单添加增删改查按钮
	 * 
	 * @return
	 * @throws Exception 
	 */
	public void addButton() throws Exception {
		String funcId = Servlets.getRequest().getParameter("funcId");
		if (StringUtils.isNotBlank(funcId)) {
			try {
				ChFunction chFunction = funcService.get(funcId);
				ChButton addButton = new ChButton();
				addButton.setChButtonCode("add");
				addButton.setChButtonMemo("add");
				addButton.setChButtonName("增加");
				addButton.setChButtonStatus("1");
				addButton.setChFunc(chFunction);
				ChButton delButton = new ChButton();
				delButton.setChButtonCode("delete");
				delButton.setChButtonMemo("delete");
				delButton.setChButtonName("删除");
				delButton.setChButtonStatus("1");
				delButton.setChFunc(chFunction);
				ChButton editButton = new ChButton();
				editButton.setChButtonCode("eidt");
				editButton.setChButtonMemo("eidt");
				editButton.setChButtonName("编辑");
				editButton.setChButtonStatus("1");
				editButton.setChFunc(chFunction);
				ChButton searchButton = new ChButton();
				searchButton.setChButtonCode("search");
				searchButton.setChButtonMemo("search");
				searchButton.setChButtonName("搜索");
				searchButton.setChButtonStatus("1");
				searchButton.setChFunc(chFunction);
				buttonService.save(addButton);
				buttonService.save(delButton);
				buttonService.save(editButton);
				buttonService.save(searchButton);
			} catch (Exception e) {
				e.printStackTrace();
				Renders.renderJsonWithoutDeepInto(new JsonResult("1","数据异常"));
			}
		}
		Renders.renderJsonWithoutDeepInto(new JsonResult("0","保存成功"));
	}

	@Override
	public String add() throws Exception {
		String roleId = Servlets.getRequest().getParameter("roleId");
		String funcId = Servlets.getRequest().getParameter("funcId");
		String roleName = roleService.get(roleId).getChRoleName();
		String funcName = funcService.get(funcId).getChFuncName();
		this.setRoleName(roleName);
		this.setFuncName(funcName);
		return ADD;
	}

	@Override
	public String delete() throws Exception {
		String ids = Servlets.getRequest().getParameter("ids");
		roleButtonService.deleteByIds(ids.split(","));
		Renders.renderJson(Renders.DELETE_SUCCESS);
		return NONE;
	}

	@Override
	public String detail() throws Exception {
		return null;
	}

	public String tree() throws Exception {
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Menu
	public String list() throws Exception {
		roleList = roleService.findList("system.findRoles", new FastMap().newHashMap().set("_query_existedParentRole", true));
		if (StringUtils.isNotBlank(funcName) && StringUtils.isNotBlank(roleName)) {
			buttonList = roleButtonService.findButtonByFunc(funcName);
			haveButtonList = roleButtonService.findButtonsByRoleAndFunc(new FastMap().newHashMap().set("funcId", funcName).set("roleId", roleName));
			List<ChRolefunc> chRolefuncs = getChRolefuncByRoleId(roleName);

			funcList = new ArrayList<ChFunction>();
			if (chRolefuncs != null && chRolefuncs.size() > 0) {
				for (ChRolefunc f : chRolefuncs) {
					ChFunction func = new ChFunction();
					String funcPath = f.getChFunc().getChFuncPath();
					if (StringUtils.isNotBlank(funcPath) && !StringUtils.startsWith(funcPath, "/system/")
							&& !StringUtils.equals(f.getChFunc().getParentFuncId(), "1")) {
						func.setId(f.getChFunc().getId());
						func.setChFuncName(f.getChFunc().getChFuncName());
						funcList.add(func);
					}
				}
			}
		}

		return LIST;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String buttonList() throws Exception {
		buttonList = roleButtonService.findButtonByFunc(funcId);
		haveButtonList = roleButtonService.findButtonsByRoleAndFunc(new FastMap().newHashMap().set("funcId", funcId).set("roleId", roleId));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("buttonList", buttonList);
		result.put("haveButtonList", haveButtonList);
		Renders.renderJsonWithoutDeepInto(result);
		return NONE;
	}

	public String funcList() throws Exception {
		String roleId = Servlets.getRequest().getParameter("roleId");
		List<ChRolefunc> chRolefuncs = getChRolefuncByRoleId(roleId);

		List<ChFunction> jsonList = new ArrayList<ChFunction>();
		if (chRolefuncs != null && chRolefuncs.size() > 0) {
			for (ChRolefunc f : chRolefuncs) {
				ChFunction func = new ChFunction();
				String funcPath = f.getChFunc().getChFuncPath();
				if (StringUtils.isNotBlank(funcPath) && !StringUtils.startsWith(funcPath, "/system/")
						&& !StringUtils.equals(f.getChFunc().getParentFuncId(), "1")) {
					func.setId(f.getChFunc().getId());
					func.setChFuncName(f.getChFunc().getChFuncName());
					jsonList.add(func);
				}
			}
		}
		Renders.renderJson(jsonList);
		return NONE;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String saveButton() throws Exception {
		haveButtonList = roleButtonService.findButtonsByRoleAndFunc(new FastMap().newHashMap().set("funcId", funcId).set("roleId", roleId));
		if (CollectionUtils.isNotEmpty(haveButtonList)) {
			for (ChRolebutton roleBtn : haveButtonList) {
				if (roleBtn != null && roleBtn.getId() != null) {
					roleButtonService.deleteById(roleBtn.getId());
				}
			}
		}

		if (StringUtils.isNotBlank(buttonIds)) {
			for (String buttonId : buttonIds.split(",")) {
				if (StringUtils.isNotBlank(buttonId)) {
					ChRolebutton roleButton = new ChRolebutton();

					ChFunction func = new ChFunction();
					func.setId(funcId);
					ChRole role = new ChRole();
					role.setId(roleId);
					ChButton button = new ChButton();
					button.setId(buttonId);

					roleButton.setChRole(role);
					roleButton.setChFunc(func);
					roleButton.setChButton(button);
					roleButtonService.save(roleButton);
				}
			}
		}
		Renders.renderJson("");
		return NONE;
	}

	/**
	 * 由角色名称获取角色所有的权限
	 * 
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<ChRolefunc> getChRolefuncByRoleId(String roleId) throws Exception {
		@SuppressWarnings("unused")
		ChRole role = roleService.get(roleId);
		@SuppressWarnings("rawtypes")
		Map<String, String> paramMap = new FastMap().newHashMap().set("roleId", roleId);
		List<ChRolefunc> chRolefuncs = rolefuncService.findList("system.findRoleFuncs", paramMap);
		return chRolefuncs;
	}

	@Override
	public void prepareModel() throws Exception {
		if (StringUtils.isBlank(id)) {
			roleButton = new ChRolebutton();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String save() throws Exception {
		@SuppressWarnings("rawtypes")
		ChRole role = roleService.findOne("system.findRoles", new FastMap().newHashMap().set("roleName", roleName));
		@SuppressWarnings("rawtypes")
		ChFunction func = funcService.findOne("system.findFuncs", new FastMap().newHashMap().set("funcName", funcName));
		roleButton.setChRole(role);
		roleButton.setChFunc(func);
		roleButtonService.save(roleButton);
		Renders.renderJson(new JsonResult("0", "保存成功"));
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String update() throws Exception {
		roleButton = roleButtonService.get(id);
		// if (roleButton.getChRobuFlag().equals("是"))
		// roleButton.setChRobuFlag("否");
		// else
		// roleButton.setChRobuFlag("是");
		roleButtonService.update(roleButton);

		String roleName = Servlets.getRequest().getParameter("roleName");
		String funcName = Servlets.getRequest().getParameter("funcName");
		this.setRoleName(roleName);
		this.setFuncName(funcName);
		List<ChRolefunc> chRolefuncs = getChRolefuncByRoleId(roleName);
		funcList = new ArrayList<ChFunction>();
		for (ChRolefunc rf : chRolefuncs) {
			String funcPath = rf.getChFunc().getChFuncPath();
			if (!funcPath.equals("") && funcPath != "" && funcPath != null)
				funcList.add(rf.getChFunc());
		}

		roleList = roleService.findList("system.findRoles", new FastMap().newHashMap().set("_query_existedParentRole", true));
		Map<String, String> paramMap = new FastMap().newHashMap().set("roleName", roleName).set("funcName", funcName);
		page = roleButtonService.findPage(page, "system.findButtons", paramMap);

		// chRolebuttons = buttonService.getButtonsByRoleAndFunc("按钮管理");

		return LIST;
	}

	@Override
	public ChRolebutton getModel() {
		return roleButton;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ChRolebutton getRoleButton() {
		return roleButton;
	}

	public void setRoleButton(ChRolebutton roleButton) {
		this.roleButton = roleButton;
	}

	public ChRolebutton getButton() {
		return button;
	}

	public void setButton(ChRolebutton button) {
		this.button = button;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<ChFunction> getFuncList() {
		return funcList;
	}

	public void setFuncList(List<ChFunction> funcList) {
		this.funcList = funcList;
	}

	public List<ChRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<ChRole> roleList) {
		this.roleList = roleList;
	}

	public List<ChRolefunc> getRoleFuncList() {
		return roleFuncList;
	}

	public void setRoleFuncList(List<ChRolefunc> roleFuncList) {
		this.roleFuncList = roleFuncList;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public List<ChRolebutton> getChRolebuttons() {
		return chRolebuttons;
	}

	public void setChRolebuttons(List<ChRolebutton> chRolebuttons) {
		this.chRolebuttons = chRolebuttons;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public List<ChButton> getButtonList() {
		return buttonList;
	}

	public void setButtonList(List<ChButton> buttonList) {
		this.buttonList = buttonList;
	}

	public String getButtonIds() {
		return buttonIds;
	}

	public void setButtonIds(String buttonIds) {
		this.buttonIds = buttonIds;
	}

	public List<ChRolebutton> getHaveButtonList() {
		return haveButtonList;
	}

	public void setHaveButtonList(List<ChRolebutton> haveButtonList) {
		this.haveButtonList = haveButtonList;
	}
}
