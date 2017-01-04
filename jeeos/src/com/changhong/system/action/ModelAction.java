package com.changhong.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.changhong.base.web.Servlets;
import com.changhong.base.web.render.Renders;
import com.changhong.base.web.struts2.action.BaseCrudAction;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChModel;
import com.changhong.system.entity.ChRolefunc;
import com.changhong.system.service.FuncService;
import com.changhong.system.service.ModelFuncService;
import com.changhong.system.service.ModelService;
import com.changhong.system.service.RoleService;

@Controller("system.action.ModelAction")
@Scope("prototype")
public class ModelAction extends BaseCrudAction<ChModel> {

    private static final long serialVersionUID = -2643124229891653076L;

    private String id;
	private ChModel chModel;
	private String checkIds;
	
	@Autowired
	private ModelService modelService;
	@Autowired
	private ModelFuncService modelFuncService;
	@Autowired
	private FuncService funcService;
	@Autowired
	private RoleService roleService;
	
	@Override
    public String add() throws Exception {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String delete() throws Exception {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String detail() throws Exception {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String list() throws Exception {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public void prepareModel() throws Exception {
		if(StringUtils.isBlank(id)){
			chModel = new ChModel();
		}
		else{
			chModel = modelService.get(id);
		}
    }

	@Override
    public String save() throws Exception {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public String update() throws Exception {
	    // TODO Auto-generated method stub
	    return null;
    }

	@Override
    public ChModel getModel() {
	    return chModel;
    }
	
    public String funcList() throws Exception {
		String modelId = Servlets.getRequest().getParameter("modelId");
		String roleId = Servlets.getRequest().getParameter("roleId");
		List<ChFunction> funcList = funcService.getFunctionByModel(modelId);
		
		if(StringUtils.isNotBlank(roleId)){
			List<ChRolefunc> roleFuncList = roleService.findChRoleFuncByRoleId(roleId);
			if(roleFuncList!=null && roleFuncList.size()>0){
				for(ChRolefunc rm : roleFuncList){
					checkIds += (","+rm.getChFunc().getId());
				}
			}
		}
		Map<String,Object> result = new HashMap();
		result.put("checkIds", checkIds);
		result.put("funcList", funcList);
		Renders.renderJsonWithoutDeepInto(result);
		return NONE;
	}
	
	 public String getId() {
	    	return id;
	    }

		public void setId(String id) {
	    	this.id = id;
	    }

		public ChModel getChModel() {
	    	return chModel;
	    }

		public void setChModel(ChModel chModel) {
	    	this.chModel = chModel;
	    }

		public String getCheckIds() {
			return checkIds;
		}

		public void setCheckIds(String checkIds) {
			this.checkIds = checkIds;
		}

}
