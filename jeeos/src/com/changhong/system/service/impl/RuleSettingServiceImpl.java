package com.changhong.system.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.constants.enums.YN;
import com.changhong.base.dao.cache.CacheConstants;
import com.changhong.base.dao.cache.EhCacheManager;
import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.base.utils.FastMap;
import com.changhong.system.entity.ChRuleSetting;
import com.changhong.system.service.RuleSettingService;
/**
 * 系统规则设置service
 * @author wanghao
 */
@Service
@Transactional
public class RuleSettingServiceImpl extends BaseServiceImpl<ChRuleSetting>  implements RuleSettingService {

	public ChRuleSetting findOne(String ruleType) throws Exception{
	     return findOne("system.findChRuleSettings",new FastMap<String,String>().newHashMap().set("ruleType", ruleType));
	}

	@SuppressWarnings("unchecked")
	public int updateRuleSettingInCache()throws Exception{
		Map<String, YN> chRuleSettingMap = (Map<String, YN>) findMap("system.findChRuleSettings", "ruleType","ruleCode",null);
		
		EhCacheManager.remove(CacheConstants.CONSTANTS_DATE_CACHE, CacheConstants.CH_RULE_SETTING_KEY);
    	EhCacheManager.put(CacheConstants.CONSTANTS_DATE_CACHE, CacheConstants.CH_RULE_SETTING_KEY, chRuleSettingMap);
    	
		return 1;
	}
}
