package com.changhong.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "ch_rolefunc")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ChRolefunc extends IDEntity implements java.io.Serializable {

    private static final long serialVersionUID = -3580567860358677906L;
	// Fields
	private ChRole chRole;
	private ChFunction chFunc;
	private Integer chRofcMemo;

	// Constructors

	/** default constructor */
	public ChRolefunc() {
	}

	/** minimal constructor */
	public ChRolefunc(String id) {
		super(id);
	}

	/** full constructor */
	public ChRolefunc(String id, ChRole chRole, ChFunction chFunc, Integer chRofcMemo) {
		super(id);
		this.chRole = chRole;
		this.chFunc = chFunc;
		this.chRofcMemo = chRofcMemo;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ch_role_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public ChRole getChRole() {
		return this.chRole;
	}

	public void setChRole(ChRole chRole) {
		this.chRole = chRole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ch_func_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public ChFunction getChFunc() {
		return this.chFunc;
	}

	public void setChFunc(ChFunction chFunc) {
		this.chFunc = chFunc;
	}

	@Column(name = "ch_rofc_memo")
	public Integer getChRofcMemo() {
		return this.chRofcMemo;
	}

	public void setChRofcMemo(Integer chRofcMemo) {
		this.chRofcMemo = chRofcMemo;
	}

}