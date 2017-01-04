package com.changhong.base.utils;


/**
 * map链,用于快速put名值对
 * @author wanghao
 */

public class FastMap<K,V> {
	
	private HashMap<K,V> hashMap;
	private LinkedHashMap<K,V> linkedHashMap;
	
	public FastMap(){}

	public HashMap<K,V> newHashMap(){
		hashMap = new HashMap<K,V>();
		return hashMap;
	}
	
	public LinkedHashMap<K,V> newLinkedHashMap(){
		linkedHashMap = new LinkedHashMap<K,V>();
		return linkedHashMap;
	}
	
	@SuppressWarnings("hiding")
	public class HashMap<K,V> extends java.util.HashMap<K, V> {
		private static final long serialVersionUID = 7747838991335339497L;
		public HashMap<K,V> set(K key, V value) {
			super.put(key, value);
			return this;
		}
	}
	
	@SuppressWarnings("hiding")
	public class LinkedHashMap<K,V> extends java.util.LinkedHashMap<K, V> {
		private static final long serialVersionUID = 7747838991335339497L;
		public LinkedHashMap<K,V> set(K key, V value) {
			super.put(key, value);
			return this;
		}
	}
}
