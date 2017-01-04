package com.changhong.system.service;

import java.util.List;
import java.util.Map;

import com.changhong.base.service.BaseService;
import com.changhong.system.entity.ChButton;
import com.changhong.system.entity.ChRolebutton;

public interface RoleButtonService extends BaseService<ChRolebutton> {

	
	public List<ChRolebutton> findButtonsByRoleAndFunc (Map<String,String> paramMap) throws Exception;
	
	public List<ChButton> findButtonByFunc(String funcId)throws Exception;
	
}
