package com.changhong.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.dao.utils.builder.HqlBuilder;
import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.base.utils.FastMap;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChModelfunc;
import com.changhong.system.service.FuncService;
@Service
@Transactional
public class FuncServiceImpl extends BaseServiceImpl<ChFunction> implements FuncService {

	@Override
	public List<ChFunction> getFunctionByModel(String modelId) throws Exception {
		Map<String,String> paramMap = new FastMap().newHashMap().set("modelId", modelId);
		List<ChModelfunc> chModelFuncs = this.findList("system.findFuncsByModel", paramMap);
		
		List<ChFunction> fList = new ArrayList<ChFunction>();
		if(CollectionUtils.isNotEmpty(chModelFuncs)){
			for(ChModelfunc mf : chModelFuncs){
				//Add self
				ChFunction funct = this.get(mf.getChFuncId());
				if (!isPresent(fList, funct)) {
					fList.add(funct);
					recursionFunc(fList,funct);
				}
			}
		}
		return fList;
	}
	
	private List<ChFunction> recursionFunc(List<ChFunction> fList,ChFunction func){
		if(func.getChFunc() != null){
			ChFunction parentFunc = func.getChFunc();
			if(!isPresent(fList,parentFunc)){
				fList.add(parentFunc);
				recursionFunc(fList,parentFunc);
			}
		}
		return fList;
	}
	
	private boolean isPresent(List<ChFunction> fList, ChFunction func) {
		boolean isPresent = false;
		for (ChFunction f : fList) {
			if (func.getId().equals(f.getId())) {
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	@Override
	public List<ChFunction> getAllLeafFuncByParentId(String pId)
			throws Exception {
		ChFunction rootFunc = this.get(pId);
		if(rootFunc != null){
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChFunction> getAllFunctions() throws Exception {
		String hql = "from ChFunction as cf where 1=1";
		HqlBuilder hqlBuilder = new HqlBuilder(hql);
		return super.findList(hqlBuilder);
	}
}
