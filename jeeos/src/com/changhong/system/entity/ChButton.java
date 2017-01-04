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
 * ChRolebutton entity. @author wanghao
 * 2013-07-25
 */
@Entity
@Table(name = "ch_button")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ChButton extends IDEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1632916995202355221L;
	// Fields
    private String chButtonCode;
	private String chButtonName;
	private String chButtonStatus;
	private String chButtonMemo;
	private ChFunction chFunc;

	// Constructors

	/** default constructor */
	public ChButton() {
	}

	/** minimal constructor */
	public ChButton(String id) {
		super(id);
	}

	/** full constructor */
	public ChButton(String id, String chButtonCode, String chButtonName, String chButtonStatus, String chButtonMemo,
	        String chRobuMemo) {
		super(id);
		this.chButtonCode = chButtonCode;
		this.chButtonName = chButtonName;
		this.chButtonStatus = chButtonStatus;
		this.chButtonMemo = chButtonMemo;
	}

	@Column(name = "ch_button_name", length = 256)
	public String getChButtonName() {
		return chButtonName;
	}

	public void setChButtonName(String chButtonName) {
		this.chButtonName = chButtonName;
	}

	@Column(name = "ch_button_status", length = 2)
	public String getChButtonStatus() {
		return chButtonStatus;
	}

	public void setChButtonStatus(String chButtonStatus) {
		this.chButtonStatus = chButtonStatus;
	}

	@Column(name = "ch_button_memo", length = 256)
	public String getChButtonMemo() {
		return chButtonMemo;
	}

	public void setChButtonMemo(String chButtonMemo) {
		this.chButtonMemo = chButtonMemo;
	}

	@Column(name = "ch_button_code", length = 128)
	public String getChButtonCode() {
		return chButtonCode;
	}

	public void setChButtonCode(String chButtonCode) {
		this.chButtonCode = chButtonCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ch_func_id")
	public ChFunction getChFunc() {
		return chFunc;
	}

	public void setChFunc(ChFunction chFunc) {
		this.chFunc = chFunc;
	}
}