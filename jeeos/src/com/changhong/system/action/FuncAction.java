package com.changhong.system.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.changhong.base.annotation.Menu;
import com.changhong.base.entity.Page;
import com.changhong.base.utils.FastMap;
import com.changhong.base.web.Servlets;
import com.changhong.base.web.render.EasyUiResult;
import com.changhong.base.web.render.Renders;
import com.changhong.base.web.struts2.action.BaseCrudAction;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChModel;
import com.changhong.system.entity.ChModelfunc;
import com.changhong.system.entity.ChRolebutton;
import com.changhong.system.service.RoleButtonService;
import com.changhong.system.service.FuncService;
import com.changhong.system.service.ModelFuncService;
import com.changhong.system.service.ModelService;
import com.google.common.collect.Lists;

/**
 * 权限（菜单）管理action
 * @author wanghao
 * 2013-07-16
 */
@Controller("system.action.FuncAction")
@Scope("prototype")
public class FuncAction extends BaseCrudAction<ChFunction>{
	
    private static final long serialVersionUID = 665738526040125854L;
	private ChFunction func;
	private ChFunction chFunc;
	private String id;
	private List<ChFunction> funcList;
	private List<ChFunction> subFuncList;
	private List<ChRolebutton> chRolebuttons;
	private List<ChModelfunc> chModelFuncs;
	
	private List<ChModel> chModelfs;
	private Page page = new Page(10);
	
	@Autowired
	private FuncService funcService;
	@Autowired
    private RoleButtonService buttonService;
	@Autowired
	private ModelService modelService;
	@Autowired
	private ModelFuncService modelFuncService;
	
	public ChFunction getModel() {
		return func;
	}
	
    @Override
	public void prepareModel() throws Exception {
		//新增权限
		if(!StringUtils.isNotEmpty(id)){
			func = new ChFunction();
			func.setChFuncSortno("0");
			if(StringUtils.isNotEmpty(chFunc.getId())){
				//非一级权限
				func.setChFunc(funcService.get(chFunc.getId()));
			}
		}
		//修改权限
		else{
			func = funcService.get(id);
		}
	}
	@SuppressWarnings("unchecked")
    @Override
	public String save() throws Exception {
		
		if(id==null){
			funcService.save(func);
			Renders.renderJson(new EasyUiResult("0","保存成功",Lists.newArrayList(func.getId())));
		}else{
			funcService.update(func);
			Renders.renderJson(Renders.UPDATE_SUCCESS_JSON);
		}
		
		String checkId = Servlets.getRequest().getParameter("checkId");
		if (StringUtils.isNotBlank(checkId) && StringUtils.isNotEmpty(checkId)) {
			String[] ids = checkId.split(",");
			for (String id : ids){
				ChModelfunc chModelFunc = new ChModelfunc();
				chModelFunc.setChModelId(id);
				chModelFunc.setChFuncId(func.getId());
				modelFuncService.saveOrUpdate(chModelFunc);
			}
		}
		
		return NONE;
	}
	
	@Override
	public String detail() throws Exception {
		return DETAIL;
	}
	
	@SuppressWarnings("unchecked")
    public String add()throws Exception{
		try{
			chFunc = funcService.get(id);
			
			chModelfs = modelService.findList("system.findModels", null);
		}catch (Exception e) {
			addActionMessage("没有上级权限，请先选择上级角色");
		}
		this.resetCurrentFuncId();
		return ADD;
	}

    @SuppressWarnings("unchecked")
    public String update() throws Exception{
    	chModelFuncs = modelFuncService.findList("system.findFuncsByModel", new FastMap().newHashMap().set("funcId", id));
    	chModelfs = modelService.findList("system.findModels", null);
    	this.resetCurrentFuncId();
		return UPDATE;
	}
	
	@Override
	public String delete() throws Exception {
		String ids = Servlets.getRequest().getParameter("ids");
		funcService.deleteByIds(ids.split(","));
		Renders.renderJson(Renders.DELETE_SUCCESS);
		return NONE;
	}

	@SuppressWarnings("unchecked")
	public String list() throws Exception {
		try{
			setId(id);
			page = funcService.findPage(page,"system.findFuncs",new FastMap().newHashMap().set("parentId", id));
			
		}catch (Exception e) {
			addActionMessage("不能获得权限列表，请检查查询条件");
		}
		this.resetCurrentFuncId();
		return SUCCESS;
	}
	/**
	 * 权限树
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Menu
	public String tree() throws Exception {
		funcList = funcService.findList("system.findFuncs",null);
		this.resetCurrentFuncId();
		return TREE;
	}
	
	/**
	 * 菜单排序
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Menu
    public String sort() throws Exception {
		//二级菜单
		funcList = funcService.findList("system.findFuncs",new FastMap().newHashMap().set("parentId", "1"));
		//所有三级菜单
        Map<String,String> paramMap = new FastMap().newHashMap()
			.set("existedParentId", "1");
		page = funcService.findPage(page, "system.findFuncsBy",paramMap);
		//chRolebuttons = buttonService.getButtonsByRoleAndFunc("菜单排序");
		return "sort";
	}
	
	/**
	 * 根据父菜单id获取子菜单
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
    public String subSort() throws Exception {
		String parentId = Servlets.getRequest().getParameter("parentFuncId");
		setId(parentId);
		//二级菜单
		funcList = funcService.findList("system.findFuncs",new FastMap().newHashMap().set("parentId", "1"));
		//对应的三级菜单
        Map<String,String> paramMap = new FastMap().newHashMap()
			.set("parentId", id);
		page = funcService.findPage(page, "system.findFuncsBy",paramMap);
		
		//chRolebuttons = buttonService.getButtonsByRoleAndFunc("菜单排序");
		return "sort";
	}
	
	public String sortSave() throws Exception {
		String funcIdSortNo = Servlets.getRequest().getParameter("funcIdSortNo");
		System.out.println(funcIdSortNo+"funcIdSortNo");
		String[] idSortNo = funcIdSortNo.split(" ");
		for (String idNo : idSortNo) {
			String id = idNo.substring(0, idNo.indexOf(","));
			String No = idNo.substring(idNo.indexOf(",")+1);
			func = funcService.get(id);
			func.setChFuncSortno(No);
			funcService.update(func);
		}
		Renders.renderJson(Renders.SAVE_SUCCESS);
		return NONE;
	}
	//-------------------以下是getter/setter方法--------------------------------------
 	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public ChFunction getChFunc() {
		return chFunc;
	}
	public void setChFunc(ChFunction chFunc) {
		this.chFunc = chFunc;
	}
	public List<ChFunction> getSubFuncList() {
    	return subFuncList;
    }
	public void setSubFuncList(List<ChFunction> subFuncList) {
    	this.subFuncList = subFuncList;
    }
	public List<ChRolebutton> getChRolebuttons() {
    	return chRolebuttons;
    }
	public void setChRolebuttons(List<ChRolebutton> chRolebuttons) {
    	this.chRolebuttons = chRolebuttons;
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
}
