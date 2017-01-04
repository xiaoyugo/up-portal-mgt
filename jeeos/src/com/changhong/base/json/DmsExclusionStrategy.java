package com.changhong.base.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class DmsExclusionStrategy implements ExclusionStrategy {
	public DmsExclusionStrategy() {

	}

	public DmsExclusionStrategy(String[] excludeFields,
			Class<?>[] excludeClasses) {
		this.excludeFields = excludeFields;
		this.excludeClasses = excludeClasses;
	}

	private String[] excludeFields;
	private Class<?>[] excludeClasses;

	public boolean shouldSkipClass(Class<?> clazz) {
		if (this.excludeClasses == null) {
			return false;
		}

		for (Class<?> excludeClass : excludeClasses) {
			if (excludeClass.getName().equals(clazz.getName())) {
				return true;
			}
		}

		return false;
	}

	public boolean shouldSkipField(FieldAttributes f) {
		if (this.excludeFields == null) {
			return false;
		}

		for (String field : this.excludeFields) {
			if (field.equals(f.getName())) {
				return true;
			}
		}

		return false;
	}

	public final String[] getExcludeFields() {
		return excludeFields;
	}

	public final Class<?>[] getExcludeClasses() {
		return excludeClasses;
	}
}