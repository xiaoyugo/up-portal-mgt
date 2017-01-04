package com.changhong.system.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.changhong.base.entity.IDEntity;

/**
 * ChRange entity. @author wanghao
 */
@Entity
@Table(name = "ch_range")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ChRange extends IDEntity implements java.io.Serializable {
	private static final long serialVersionUID = -5570420949566271288L;
	// Fields
    
	private ChRole chRole;
	private String chRangDesc;
	private String chRangMemo;
	
	private Set<ChRole> chRoles = new HashSet<ChRole>(0);

	// Constructors

	/** default constructor */
	public ChRange() {
	}

	/** minimal constructor */
	public ChRange(String id) {
		super(id);
	}

	/** full constructor */
	public ChRange(String id, ChRole chRole, String chRangDesc, String chRangMemo, Set<ChRole> chRoles) {
		super(id);
		this.chRole = chRole;
		this.chRangDesc = chRangDesc;
		this.chRangMemo = chRangMemo;
		this.chRoles = chRoles;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ch_role_id")
	public ChRole getChRole() {
		return this.chRole;
	}

	public void setChRole(ChRole chRole) {
		this.chRole = chRole;
	}

	@Column(name = "ch_rang_desc", length = 200)
	public String getChRangDesc() {
		return this.chRangDesc;
	}

	public void setChRangDesc(String chRangDesc) {
		this.chRangDesc = chRangDesc;
	}

	@Column(name = "ch_rang_memo", length = 100)
	public String getChRangMemo() {
		return this.chRangMemo;
	}

	public void setChRangMemo(String chRangMemo) {
		this.chRangMemo = chRangMemo;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
	public Set<ChRole> getChRoles() {
    	return chRoles;
    }

	public void setChRoles(Set<ChRole> chRoles) {
    	this.chRoles = chRoles;
    }

}