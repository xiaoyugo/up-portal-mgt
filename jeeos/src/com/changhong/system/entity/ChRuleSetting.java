package com.changhong.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.changhong.base.constants.enums.YN;
import com.changhong.base.entity.IDEntity;
/**
 * 系统相关规则设置
 * @author wanghao
 */
@Entity
@Table(name = "ch_rule_setting")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ChRuleSetting extends IDEntity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private YN ruleCode;
	private String ruleType;
	
	@Column(name = "RULE_CODE", length = 10)
	@Enumerated(EnumType.STRING)
	public YN getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(YN ruleCode) {
		this.ruleCode = ruleCode;
	}
	
	@Column(name = "RULE_TYPE", length = 20)
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
}
