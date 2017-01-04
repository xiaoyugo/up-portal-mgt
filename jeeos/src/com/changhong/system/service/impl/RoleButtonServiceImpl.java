package com.changhong.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.base.utils.FastMap;
import com.changhong.system.entity.ChButton;
import com.changhong.system.entity.ChRolebutton;
import com.changhong.system.service.RoleButtonService;

@Service
@Transactional
public class RoleButtonServiceImpl extends BaseServiceImpl<ChRolebutton> implements RoleButtonService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ChButton> findButtonByFunc(String funcId) throws Exception {
		Map<String,String> paramMap = new FastMap().newHashMap().set("funcId", funcId);
		List<ChButton> buttonList = this.findList("system.findButtonsByFuncId", paramMap);
    	return buttonList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChRolebutton> findButtonsByRoleAndFunc(Map<String, String> paramMap) throws Exception {
		return this.findList("system.findButtons", paramMap);
	}

}
