package com.changhong.system.service;

import com.changhong.base.service.BaseService;
import com.changhong.system.entity.ChRuleSetting;

public interface RuleSettingService extends BaseService<ChRuleSetting> {
	

	/**
	 * 根据规则类型查询规则设置
	 * @param ruleType
	 * @return
	 */
	public ChRuleSetting findOne(String ruleType) throws Exception;

	 /**
	  * 加载/更新规则在缓存存中的数据
	  */
	public int updateRuleSettingInCache()throws Exception;
}
