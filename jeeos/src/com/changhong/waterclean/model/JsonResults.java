package com.changhong.waterclean.model;

import java.util.List;

import com.changhong.base.entity.Page;

public class JsonResults {
 private String code;
 private String msg;
 private Page page;
 private List<?> data;
 private int json=0;
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public Page getPage() {
	return page;
}
public void setPage(Page page) {
	this.page = page;
}
public List<?> getData() {
	return data;
}
public void setData(List<?> data) {
	this.data = data;
}
public int getJson() {
	return json;
}
public void setJson(int json) {
	this.json = json;
}
 
}
