package com.changhong.foodmanage.model;

import java.util.List;

import com.changhong.base.entity.Page;

public class JsonResults {
 private String code;
 private String msg;
 private int totalRecords;
 private List<?> data;
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

public List<?> getData() {
	return data;
}
public void setData(List<?> data) {
	this.data = data;
}
public int getTotalRecords() {
	return totalRecords;
}
public void setTotalRecords(int totalRecords) {
	this.totalRecords = totalRecords;
}
 
}
