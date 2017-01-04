package com.changhong.system.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.changhong.base.entity.IDEntity;

/**
 * ChRole entity. 
 * @author wanghao
 * 
 */
@Entity
@Table(name = "ch_role")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ChRole extends IDEntity implements java.io.Serializable {
	private static final long serialVersionUID = -8138769426916959302L;
	
	// Fields
	private String chRoleName;
	private String chRoleFlag;
	private String chRoleDesc;
	private String chRoleModel;
	private String chRoleFunctions;
	private ChRole chRole;
	private String chRoleDeskSetting;//桌面设置

	private Set<ChRole> chRoles = new HashSet<ChRole>(0);
	private Set<ChFunction> chFuncs = new HashSet<ChFunction>(0);
	private Set<ChRange> chRanges = new HashSet<ChRange>(0);
	
	//临时变量
	private String chRoleModelName;
	private List<ChUser> userList;
	// Constructors

	/** default constructor */
	public ChRole() {
	}

	/** minimal constructor */
	public ChRole(String id) {
		super(id);
	}

	/** full constructor */
	public ChRole(String id, String chRoleName, String chRoleFlag, String chRoleDesc, String chRoleModel,
	        String chRoleFunctions, ChRole chRole, Set<ChFunction> chFuncs, Set<ChRole> chRoles,Set<ChRange> chRanges,String chRoleDeskSetting, String chRoleModelName) {
		super(id);
		this.chRoleName = chRoleName;
		this.chRoleFlag = chRoleFlag;
		this.chRoleDesc = chRoleDesc;
		this.chRoleModel = chRoleModel;
		this.chRoleFunctions = chRoleFunctions;
		this.chRole = chRole;
		this.chRoles = chRoles;
		this.chFuncs = chFuncs;
		this.chRanges = chRanges;
		this.chRoleDeskSetting=chRoleDeskSetting;
		this.chRoleModelName = chRoleModelName;
	}

	// Property accessors

	@Column(name = "ch_role_name", length = 20)
	public String getChRoleName() {
		return this.chRoleName;
	}

	public void setChRoleName(String chRoleName) {
		this.chRoleName = chRoleName;
	}

	@Column(name = "ch_role_flag", length = 1)
	public String getChRoleFlag() {
		return chRoleFlag;
	}

	public void setChRoleFlag(String chRoleFlag) {
		this.chRoleFlag = chRoleFlag;
	}

	@Column(name = "ch_role_desc", length = 100)
	public String getChRoleDesc() {
		return this.chRoleDesc;
	}

	public void setChRoleDesc(String chRoleDesc) {
		this.chRoleDesc = chRoleDesc;
	}

	@Column(name = "ch_role_model", length = 20)
	public String getChRoleModel() {
		return this.chRoleModel;
	}

	public void setChRoleModel(String chRoleModel) {
		this.chRoleModel = chRoleModel;
	}

	@Column(name = "ch_role_functions", length = 500)
	public String getChRoleFunctions() {
		return this.chRoleFunctions;
	}

	public void setChRoleFunctions(String chRoleFunctions) {
		this.chRoleFunctions = chRoleFunctions;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ch_role_parentId")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public ChRole getChRole() {
		return chRole;
	}

	public void setChRole(ChRole chRole) {
		this.chRole = chRole;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ch_rolefunc", 
			joinColumns = { @JoinColumn(name = "ch_role_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "ch_func_id", nullable = false, updatable = false) })
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<ChFunction> getChFuncs() {
		return this.chFuncs;
	}

	public void setChFuncs(Set<ChFunction> chFuncs) {
		this.chFuncs = chFuncs;
	}

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "chRole")
//	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
//	public Set<ChRole> getChRoles() {
//		return this.chRoles;
//	}
//
//	public void setChRoles(Set<ChRole> chRoles) {
//		this.chRoles = chRoles;
//	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "chRole")
//	@JoinTable(name = "CH_RANGE", 
//			joinColumns = { @JoinColumn(name = "ch_role_id", nullable = false, updatable = false) })
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<ChRange> getChRanges() {
    	return chRanges;
    }

	public void setChRanges(Set<ChRange> chRanges) {
    	this.chRanges = chRanges;
    }

	@Column(name = "ch_role_deskSetting", length = 100)
	public String getChRoleDeskSetting() {
		return chRoleDeskSetting;
	}

	public void setChRoleDeskSetting(String chRoleDeskSetting) {
		this.chRoleDeskSetting = chRoleDeskSetting;
	}
	@Transient
	public String getChRoleModelName() {
    	return chRoleModelName;
    }

	public void setChRoleModelName(String chRoleModelName) {
    	this.chRoleModelName = chRoleModelName;
    }
	
	@Transient
	public List<ChUser> getUserList() {
		return userList;
	}

	public void setUserList(List<ChUser> userList) {
		this.userList = userList;
	}

}