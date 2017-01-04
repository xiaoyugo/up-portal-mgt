package com.changhong.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.changhong.base.entity.IDEntity;

/**
 * ChModelfunc entity. @yueyongsheng
 */
@Entity
@Table(name = "ch_modelfunc")
public class ChModelfunc extends IDEntity implements java.io.Serializable {
    private static final long serialVersionUID = -8058899863724293526L;
	// Fields
	private String chModelId;
	private String chFuncId;

	// Constructors

	/** default constructor */
	public ChModelfunc() {
	}

	/** minimal constructor */
	public ChModelfunc(String id) {
		super(id);
	}

	/** full constructor */
	public ChModelfunc(String id, String chFuncId, String chModelId) {
		super(id);
		this.chModelId = chModelId;
		this.chModelId = chFuncId;
	}

	// Property accessors
	@Column(name = "ch_modl_id")
	public String getChModelId() {
		return this.chModelId;
	}

	public void setChModelId(String chModelId) {
		this.chModelId = chModelId;
	}

	@Column(name = "ch_func_id")
	public String getChFuncId() {
		return this.chFuncId;
	}

	public void setChFuncId(String chFuncId) {
		this.chFuncId = chFuncId;
	}

}