package com.changhong.base.web.render;

import java.util.Collection;

/**
 * 渲染json数据
 * @author wanghao
 */
public class JsonResult {
    
	//ajax请求结果代码，0：成功，1：失败
	private String code="0";
	//ajax请求返回到客户端的信息
	private Object msg;
	//ajax请求返回到客户端的其他信息
	private Collection<?> msgs;
	
    public JsonResult(){
	    
    }
   /**
    * json 返回的数据为：'{"msg":"msgContent"}'时，用下面的构造方法
    * @param msg
    */
    public JsonResult(Object msg){
	     this.msg = msg;
    }
    
    public JsonResult(String code,Object msg){
    	 this.code = code;
	     this.msg = msg;
   }
    
    public JsonResult(String code,Collection<?> msgs){
    	 this.code = code;
	     this.msgs = msgs;
    }
    public JsonResult(String code,Object msg,Collection<?> msgs){
    	 this.code = code;
	     this.msg = msg;
	     this.msgs = msgs;
    }
    
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public Collection<?> getMsgs() {
		return msgs;
	}
	public void setMsgs(Collection<?> msgs) {
		this.msgs = msgs;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
