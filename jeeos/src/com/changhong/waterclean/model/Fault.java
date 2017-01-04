package com.changhong.waterclean.model;

public class Fault {
	private String id;
	private String faultcode;
	private String faultvalue;
	private int frequecy;
	private String commodel;
	private String faultdescribe;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFaultcode() {
		return faultcode;
	}
	public void setFaultcode(String faultcode) {
		this.faultcode =faultcode;
	}
	public String getFaultvalue() {
		return faultvalue;
	}
	public void setFaultvalue(String faultvalue) {
		this.faultvalue = faultvalue;
	}
	public int getFrequecy() {
		return frequecy;
	}
	public void setFrequecy(int frequecy) {
		this.frequecy = frequecy;
	}
	public String getCommodel() {
		return commodel;
	}
	public void setCommodel(String commodel) {
		this.commodel = commodel;
	}
	public String getFaultdescribe() {
		return faultdescribe;
	}
	public void setFaultdescribe(String faultdescribe) {
		this.faultdescribe = faultdescribe;
	}


}
