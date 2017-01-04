package com.changhong.system.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.changhong.base.entity.IDEntity;

/**
 * ChModel entity. @yueyongsheng
 */
@Entity
@Table(name = "ch_model")
public class ChModel extends IDEntity implements java.io.Serializable {

    private static final long serialVersionUID = 6116988057835368986L;
	// Fields
	private String chModlName;
	private String chModlDesc;
	
	private Set<ChFunction> chFuncs = new HashSet<ChFunction>(0);
	
	// Constructors
	/** default constructor */
	public ChModel() {
	}

	/** minimal constructor */
	public ChModel(String id) {
		super(id);
	}

	/** full constructor */
	public ChModel(String id, String chModlName, String chModlDesc) {
		super(id);
		this.chModlName = chModlName;
		this.chModlDesc = chModlDesc;
	}

	// Property accessors
	@Column(name = "ch_modl_name", length = 20)
	public String getChModlName() {
		return this.chModlName;
	}

	public void setChModlName(String chModlName) {
		this.chModlName = chModlName;
	}

	@Column(name = "ch_modl_desc", length = 100)
	public String getChModlDesc() {
		return this.chModlDesc;
	}

	public void setChModlDesc(String chModlDesc) {
		this.chModlDesc = chModlDesc;
	}
	
	@ManyToMany(mappedBy = "chModels", cascade = CascadeType.ALL)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<ChFunction> getChFuncs() {
    	return chFuncs;
    }

	public void setChFuncs(Set<ChFunction> chFuncs) {
    	this.chFuncs = chFuncs;
    }

}