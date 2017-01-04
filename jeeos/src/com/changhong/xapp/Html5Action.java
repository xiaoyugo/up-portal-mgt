/**
 * 
 */
package com.changhong.xapp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.changhong.base.json.JsonUtil;
import com.changhong.base.utils.http.HttpRequestUtils;
import com.changhong.base.web.Servlets;
import com.google.gson.JsonObject;

/**
 * @author killneo
 *
 */
@Controller("xapp.action.Html5Action")
@Scope("prototype")
public class Html5Action {
	
	private String currentFuncId;

	public String list(){
		currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
	/*	String url="http://10.3.34.63:8080/albummanager/photoframe/qurybytag";
		JsonObject param = new JsonObject();
		param.addProperty("tagId", "x001");
		param.addProperty("page", 1);
		param.addProperty("num", 20);
		JsonObject requestHeader = new JsonObject();
		requestHeader.addProperty("version", "1.0");
		JsonObject jo = new JsonObject();
		jo.add("param", param);
		jo.add("requestHeader", requestHeader);
		System.out.println(jo);
		String result = HttpRequestUtils.doPost(url, jo.toString());*/
		String url = "http://www.google.com";
		String result = HttpRequestUtils.doGet(url);
		if(StringUtils.equals(result, "error")){
			return "error";
		}
		System.out.println(result);
		return "list";
	}
	
	public String getCurrentFuncId() {
		return currentFuncId;
	}

	public void setCurrentFuncId(String currentFuncId) {
		this.currentFuncId = currentFuncId;
	}

}
