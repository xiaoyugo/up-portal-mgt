package com.changhong.base.constants.enums;
/**
 * 访问权限，目前设置3种访问权限
 * 
 * @author wanghao
 */
public enum Auth {
    W("具有全部访问权限"),
    R("具有部分访问权限"),
    N("不具有任何访问权限");
    
    private String desc; 

    Auth(String desc){
    	this.desc = desc;
    }
    public String getDesc(){
    	return this.desc;
    }
}
