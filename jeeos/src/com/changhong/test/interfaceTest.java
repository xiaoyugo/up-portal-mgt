package com.changhong.test;

import com.changhong.base.utils.http.HttpRequestUtils;
import java.net.URLEncoder;
import java.net.URLDecoder;
import com.google.gson.JsonObject;
public class interfaceTest {
	public static void main(String[] args){
		JsonObject json=new JsonObject();
		json.addProperty("faultcode", 0x0504);
		json.addProperty("faultvalue", 0x02);
		JsonObject param=new JsonObject();
		param.add("page", json);
		//param.addProperty("queryitem","大菠萝啤酒杯");
		System.out.println(param.toString());
		//http://202.98.157.48:8080/foodmanager/api/unrecognizedno/getbyno?json=%7B%22page%22%3A%7B%22currentPage%22%3A1%2C%22pageSize%22%3A10%7D%2C%22queryitem%22%3A%226924530011000%22%7D
		String url="http://127.0.0.1:8888/watercleanMS/api/fault/isfaultexist?json="+URLEncoder.encode(json.toString());
		System.out.println(url);
		String result=HttpRequestUtils.doGet(url);
		System.out.println(result);
	}

}
