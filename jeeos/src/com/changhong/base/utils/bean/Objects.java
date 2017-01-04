package com.changhong.base.utils.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Objects {

	/**
	 * 数据类型判断
	 * @param clazz
	 * @return
	 */
	public final static boolean isPrimitive(Class<?> clazz) {
		
		return clazz.isPrimitive() || contains(clazz);
	}
	
	private final static boolean contains(Class<?> clazz){
		
		List<Class<?>> clazzList = new ArrayList<Class<?>>();
		
		clazzList.add(String.class);
		clazzList.add(Integer.class);
		clazzList.add(Long.class);
		clazzList.add(Double.class);
		clazzList.add(BigDecimal.class);
		clazzList.add(Number.class);
		clazzList.add(Boolean.class);
		clazzList.add(java.util.Date.class);
		clazzList.add(java.sql.Date.class);
		clazzList.add(java.sql.Timestamp.class);
		
		return clazzList.contains(clazz);
	}
}
