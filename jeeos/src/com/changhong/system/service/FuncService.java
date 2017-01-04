package com.changhong.system.service;

import java.util.List;

import com.changhong.base.service.BaseService;
import com.changhong.system.entity.ChFunction;

public interface FuncService extends BaseService<ChFunction> {
	
	public List<ChFunction> getFunctionByModel(String modelId) throws Exception;
	
	public List<ChFunction> getAllLeafFuncByParentId(String pId) throws Exception;
	
	/**
	 * 获取所有的菜单
	 * @return
	 * @throws Exception
	 */
	public List<ChFunction> getAllFunctions()throws Exception;
}
