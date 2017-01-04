package com.changhong.base.constants.enums;
/**
 * @author wanghao
 */
public enum YN {
	Y("是"),
	N("否");
	
	private String desc; 

	YN(String desc){
	   this.desc = desc;
    }
	public String getDesc(){
	   return this.desc;
	}
}
