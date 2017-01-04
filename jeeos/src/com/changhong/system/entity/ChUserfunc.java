package com.changhong.system.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.changhong.base.entity.IDEntity;

/**
 * ChRolefunc entity. @author wanghao
 */
@Entity
@Table(name = "ch_userfunc")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ChUserfunc extends IDEntity implements java.io.Serializable {

    private static final long serialVersionUID = -3580567860358677906L;
	// Fields
	private ChUser chUser;
	private ChFunction chFunc;
	private ChRole chRole;

	@ManyToOne
	@JoinColumn(name = "ch_user_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public ChUser getChUser() {
		return this.chUser;
	}

	public void setChUser(ChUser chUser) {
		this.chUser = chUser;
	}

	@ManyToOne
	@JoinColumn(name = "ch_func_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public ChFunction getChFunc() {
		return this.chFunc;
	}

	public void setChFunc(ChFunction chFunc) {
		this.chFunc = chFunc;
	}

	@ManyToOne
	@JoinColumn(name = "ch_role_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public ChRole getChRole() {
		return chRole;
	}

	public void setChRole(ChRole chRole) {
		this.chRole = chRole;
	}
}