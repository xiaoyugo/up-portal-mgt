package com.changhong.base.json;

import java.lang.reflect.Type;

import org.apache.poi.ss.formula.functions.T;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MyTypeSerializer implements JsonSerializer<T> {

	@Override
	public JsonElement serialize(T arg0, Type arg1,
			JsonSerializationContext arg2) {
		// TODO Auto-generated method stub
		return null;
	}   
	  
//    @Override  
//    public JsonElement serialize(T src, Type typeOfSrc,   
//            JsonSerializationContext context) {   
//        ExclusionStrategy strategy = new DmsExclusionStrategy(   
//                src.getExcludeFields(), src.getExcludeClasses());   
//        Gson gson = new GsonBuilder().setExclusionStrategies(strategy)   
//                .serializeNulls().create();   
//        return gson.toJsonTree(src);   
//    }   
}  