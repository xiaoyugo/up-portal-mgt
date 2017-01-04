package com.changhong.waterclean.model;

public class Device {

	private String sn;
	private String software;
	private String createtime;
	private String onlinestate;
	private String faultstate;
	private String username;
	private String agentname;
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSoftware() {
		return software;
	}
	public void setSoftware(String software) {
		this.software = software;
	}
	public String getActivetime() {
		return createtime;
	}
	public void setActivetime(String activetime) {
		this.createtime = activetime;
	}
	public String getOnlineState() {
		return onlinestate;
	}
	public void setOnlineState(String onlineState) {
		this.onlinestate = onlineState;
	}
	public String getFaultState() {
		return faultstate;
	}
	public void setFaultState(String faultState) {
		this.faultstate = faultState;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAgentname() {
		return agentname;
	}
	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}
	
}
