package com.changhong.system.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.changhong.base.annotation.Menu;
import com.changhong.base.constants.Constants;
import com.changhong.base.entity.Page;
import com.changhong.base.utils.FastMap;
import com.changhong.base.web.Servlets;
import com.changhong.base.web.render.EasyUiResult;
import com.changhong.base.web.render.Renders;
import com.changhong.base.web.struts2.action.BaseCrudAction;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChModel;
import com.changhong.system.entity.ChModelfunc;
import com.changhong.system.entity.ChRole;
import com.changhong.system.entity.ChRolefunc;
import com.changhong.system.service.FuncService;
import com.changhong.system.service.ModelService;
import com.changhong.system.service.RoleService;
import com.changhong.system.service.RolefuncService;
import com.google.common.collect.Lists;
/**
 * 角色管理action
 * @author wanghao
 * 2013-07-07 
 */
@Controller("system.action.RoleAction")
@Scope("prototype")
public class RoleAction extends BaseCrudAction<ChRole>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private ChRole role;
	private ChRole chRole;
	private List<ChRole> roleList;
	private List<ChFunction> funcList;
	private List<ChRolefunc> roleFuncList;
	private List<ChModelfunc> chModelFuncs;
	private List<ChModel> chModelfs;
	private Page page = new Page(Constants.PAGE_SIZE);
	@Autowired
	private RoleService roleService;
	@Autowired
	private FuncService funcService;
	@Autowired
	private RolefuncService roleFuncService;
	@Autowired
	private ModelService modelService;
	
	private String checkIds;
	private String rwAuths;
	private String modelId;

	public ChRole getModel() {
		return role;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void prepareModel() throws Exception {
		//新增角色
		if(StringUtils.isBlank(id)){
			role = new ChRole();
			if(StringUtils.isNotEmpty(chRole.getId())){
				//新增非一级角色
				role.setChRole(roleService.get(chRole.getId()));
			}
		}
		//修改角色
		else{
			role = roleService.get(id);
			modelId = role.getChRoleModel();
			funcList = funcService.getFunctionByModel(modelId);
			
			List<ChRolefunc> roleFuncList = roleService.findChRoleFuncByRoleId(role.getId());
			if(roleFuncList!=null && roleFuncList.size()>0){
				for(ChRolefunc rm : roleFuncList){
					checkIds += (","+rm.getChFunc().getId());
				}
			}
			
			//chModelFuncs = modelFuncService.findList("system.findFuncsByModel", new FastMap().newHashMap().set("funcId", id));
		}
		
		chModelfs = modelService.findList("system.findModels", null);
	}

	/**
	 * 角色门树
	 * @return
	 * @throws Exception
	 */
	
	public String tree() throws Exception {
		roleList = roleService.findRoles(null);
		return TREE;
	}
	
	public String update(){
		return UPDATE;
	}
	
	@SuppressWarnings("unchecked")
    public String showFunc() throws Exception {
		String roleId = Servlets.getRequest().getParameter("id");
		Map<String,String> paramMap = new FastMap().newHashMap().set("roleId", roleId);
		roleFuncList = roleFuncService.findList("system.findRoleFuncs", paramMap);
		
		chRole = roleService.get(roleId);
		
		return "show";
	}
	
	@SuppressWarnings("unchecked")
	public String add()throws Exception{
		try{
			chRole = roleService.get(id);
			//获得用户权限
			funcList = funcService.findList("system.findFuncs",null);
		}catch (Exception e) {
			addActionMessage("没有上级角色，请先选择上级角色");
		}
		
		chModelfs = modelService.findList("system.findModels", null);
		
		return ADD;
	}
	@Override
	public String detail() throws Exception {
		return DETAIL;
	}
	
	@Override
	public String save() throws Exception {
		if(StringUtils.isEmpty(id)){
			roleService.saveOrUpdate(role,checkIds);
			Renders.renderJson(new EasyUiResult("0","保存成功",Lists.newArrayList(role.getId())));
		}else{
			roleService.saveOrUpdate(role,checkIds);
			roleService.saveOrUpdateChRoleFunc(role, checkIds);
			
			Renders.renderJson(Renders.UPDATE_SUCCESS);
		}
		return NONE;
	}
	
	@SuppressWarnings("unchecked")
	@Menu
	public String list() throws Exception {
		try{
			if( null == id){
				id = "1";
			}
			setId(id);
			String roleName = Servlets.getRequest().getParameter("roleName");
			String roleModel = Servlets.getRequest().getParameter("roleModel");
			Map<String,String> paramMap = new FastMap().newHashMap().set("parentId", id).set("roleName", roleName).set("roleModel", roleModel);
			page = roleService.findPage(page,"system.findRoles",paramMap);
			
			List<ChRole> chRoles = page.getList();
			for (ChRole chRole : chRoles) {
				String chModel = chRole.getChRoleModel();
				ChModel model = modelService.get(chModel);
				chRole.setChRoleModelName(model.getChModlName());
			}
			this.resetCurrentFuncId();
		}catch (Exception e) {
			addActionMessage("不能获得角色列表，请检查查询条件");
		}
		return SUCCESS;
	}
	
	
	public String delete() throws Exception {
		String ids = Servlets.getRequest().getParameter("ids");
		roleService.deleteByIds(ids.split(","));
		Renders.renderJson(Renders.DELETE_SUCCESS);
		return NONE;
	}
	
	/**
	 * 改变用户状态
	 * 2013-07-10
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public String changeRoleState() throws Exception {
		//改变用户信息
		String ids = Servlets.getRequest().getParameter("ids");
		role = roleService.get(ids);
		roleService.changeUserState(role, role.getChRoleFlag());
		Renders.renderJson("");
		return NONE;
	}
	
	public String showSearch() throws Exception {
		return "showSearch";
	}
	
	public String search() throws Exception {
		
		return NONE;
	}
	
	//-----------------------以下是getter/setter方法----------------------------------
	public ChRole getRole() {
		return role;
	}
	public void setRole(ChRole role) {
		this.role = role;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public ChRole getChRole() {
		return chRole;
	}
	public void setChRole(ChRole chRole) {
		this.chRole = chRole;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<ChRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<ChRole> roleList) {
		this.roleList = roleList;
	}
	public List<ChFunction> getFuncList() {
		return funcList;
	}
	public void setFuncList(List<ChFunction> funcList) {
		this.funcList = funcList;
	}
	public String getCheckIds() {
		return checkIds;
	}
	public void setCheckIds(String checkIds) {
		this.checkIds = checkIds;
	}
	public String getRwAuths() {
		return rwAuths;
	}
	public void setRwAuths(String rwAuths) {
		this.rwAuths = rwAuths;
	}
	public List<ChRolefunc> getRoleFuncList() {
    	return roleFuncList;
    }
	public void setRoleFuncList(List<ChRolefunc> roleFuncList) {
    	this.roleFuncList = roleFuncList;
    }
	public List<ChModel> getChModelfs() {
    	return chModelfs;
    }
	public void setChModelfs(List<ChModel> chModels) {
    	this.chModelfs = chModels;
    }
	public List<ChModelfunc> getChModelFuncs() {
    	return chModelFuncs;
    }
	public void setChModelFuncs(List<ChModelfunc> chModelFuncs) {
    	this.chModelFuncs = chModelFuncs;
    }
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
}
