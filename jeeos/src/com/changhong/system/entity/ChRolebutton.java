package com.changhong.system.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.changhong.base.entity.IDEntity;

/**
 * ChRolebutton entity. @author wanghao
 * 2013-07-25
 */
@Entity
@Table(name = "ch_rolebutton")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ChRolebutton extends IDEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1632916995202355221L;
	// Fields
	private ChRole chRole;
	private ChFunction chFunc;
	private ChButton chButton;
	// Constructors

	/** default constructor */
	public ChRolebutton() {
	}

	/** minimal constructor */
	public ChRolebutton(String id) {
		super(id);
	}

	/** full constructor */
	public ChRolebutton(String id, ChRole chRole, ChFunction chFunc,ChButton chButton) {
		super(id);
		this.chRole = chRole;
		this.chFunc = chFunc;
		this.chButton = chButton;
	}

	@ManyToOne
	@JoinColumn(name = "ch_role_id")
	public ChRole getChRole() {
		return this.chRole;
	}

	public void setChRole(ChRole chRole) {
		this.chRole = chRole;
	}

	@ManyToOne
	@JoinColumn(name = "ch_func_id")
	public ChFunction getChFunc() {
		return this.chFunc;
	}

	public void setChFunc(ChFunction chFunc) {
		this.chFunc = chFunc;
	}

	@ManyToOne
	@JoinColumn(name = "ch_button_id")
	public ChButton getChButton() {
		return chButton;
	}

	public void setChButton(ChButton chButton) {
		this.chButton = chButton;
	}

}