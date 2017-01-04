package com.changhong.system.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import com.google.gson.annotations.Expose;

/**
 * ChFunction entity. @author wanghao
 */
@Entity
@Table(name = "ch_function")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChFunction extends IDEntity implements java.io.Serializable {

	private static final long serialVersionUID = 4365968464177870402L;
	// Fields
	@Expose
	private ChFunction chFunc;
	@Expose
	private String chFuncPath;
	@Expose
	private String chFuncName;
	private String chFuncSortno;
	private String chFuncDelflag;
	private String chFuncState;
	private String chFuncMemo;
	private String chFuncImage;

	private String parentFuncId;

	private Set<ChRole> chRoles = new HashSet<ChRole>(0);
	private Set<ChModel> chModels = new HashSet<ChModel>(0);
	private List<ChFunction> chFuncs = new ArrayList<ChFunction>(0);
	// 以下两个是临时对象，用于生成菜单树

	private List<ChFunction> subList;
	private List<ChButton> btnList;

	private int subSize;

	// Constructors
	/** default constructor */
	public ChFunction() {
	}

	/** minimal constructor */
	public ChFunction(String id) {
		super(id);
	}

	/** full constructor */
	public ChFunction(String id, ChFunction chFunc, String chFuncPath,
			String chFuncName, String chFuncSortno, String chFuncDelflag,
			String chFuncState, String chFuncMemo, Set<ChRole> chRoles,
			List<ChFunction> chFuncs) {
		super(id);
		this.chFunc = chFunc;
		this.chFuncPath = chFuncPath;
		this.chFuncName = chFuncName;
		this.chFuncSortno = chFuncSortno;
		this.chFuncDelflag = chFuncDelflag;
		this.chFuncState = chFuncState;
		this.chFuncMemo = chFuncMemo;
		this.chRoles = chRoles;
		this.chFuncs = chFuncs;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ch_func_faid")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public ChFunction getChFunc() {
		return this.chFunc;
	}

	public void setChFunc(ChFunction chFunc) {
		this.chFunc = chFunc;
	}

	@Column(name = "ch_func_path", length = 100)
	public String getChFuncPath() {
		return this.chFuncPath;
	}

	public void setChFuncPath(String chFuncPath) {
		this.chFuncPath = chFuncPath;
	}

	@Column(name = "ch_func_name", length = 20)
	public String getChFuncName() {
		return this.chFuncName;
	}

	public void setChFuncName(String chFuncName) {
		this.chFuncName = chFuncName;
	}

	@Column(name = "ch_func_sortno", length = 10)
	public String getChFuncSortno() {
		return this.chFuncSortno;
	}

	public void setChFuncSortno(String chFuncSortno) {
		this.chFuncSortno = chFuncSortno;
	}

	@Column(name = "ch_func_delflag", length = 1)
	public String getChFuncDelflag() {
		return this.chFuncDelflag;
	}

	public void setChFuncDelflag(String chFuncDelflag) {
		this.chFuncDelflag = chFuncDelflag;
	}

	@Column(name = "ch_func_state", length = 1)
	public String getChFuncState() {
		return this.chFuncState;
	}

	public void setChFuncState(String chFuncState) {
		this.chFuncState = chFuncState;
	}

	@Column(name = "ch_func_memo", length = 100)
	public String getChFuncMemo() {
		return this.chFuncMemo;
	}

	public void setChFuncMemo(String chFuncMemo) {
		this.chFuncMemo = chFuncMemo;
	}

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "chFuncs")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<ChRole> getChRoles() {
		return this.chRoles;
	}

	public void setChRoles(Set<ChRole> chRoles) {
		this.chRoles = chRoles;
	}

	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "chFunc")
	// @Cache(usage=CacheConcurrencyStrategy.NONE)
	// public List<ChFunction> getChFuncs() {
	// return this.chFuncs;
	// }
	//
	// public void setChFuncs(List<ChFunction> chFuncs) {
	// this.chFuncs = chFuncs;
	// }

	@Transient
	public List<ChFunction> getSubList() {
		return subList;
	}

	public void setSubList(List<ChFunction> subList) {
		this.subList = subList;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ch_modelfunc", joinColumns = { @JoinColumn(name = "ch_func_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ch_modl_id", nullable = false, updatable = false) })
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<ChModel> getChModels() {
		return chModels;
	}

	public void setChModels(Set<ChModel> chModels) {
		this.chModels = chModels;
	}

	@Transient
	public String getParentFuncId() {
		if (this.getChFunc() != null) {
			return this.getChFunc().getId();
		}
		return parentFuncId;
	}

	public void setParentFuncId(String parentFuncId) {
		this.parentFuncId = parentFuncId;
	}

	@Transient
	public String getChFuncImage() {
		return chFuncImage;
	}

	public void setChFuncImage(String chFuncImage) {
		this.chFuncImage = chFuncImage;
	}

	@Transient
	public List<ChButton> getBtnList() {
		return btnList;
	}

	public void setBtnList(List<ChButton> btnList) {
		this.btnList = btnList;
	}

	@Transient
	public int getSubSize() {
		if (subList != null) {
			subSize = subList.size();
		}
		return subSize;
	}

}