package com.changhong.base.json;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.GsonBuilder;

public class JsonUtil {
	
	private static GsonBuilder instance;

	private static synchronized GsonBuilder build() {
		if (instance == null) { 
			instance = new GsonBuilder();
		}
		return instance;
	}
	
	private static GsonBuilder getInstance(){
		return build();
	}
	
	public static String toJson(Object obj){
		return getInstance().create().toJson(obj);
	}
	
	public static String toJson(List <?> list){
		return getInstance().create().toJson(list);
	} 
	
	public static <T> T fromJson(String json, Class<T> clazz){
		return getInstance().create().fromJson(json, clazz);
	}
	
	public static List <?> fromJson(String json, Type  t){
		return getInstance().create().fromJson(json, t);
	}
	
}
