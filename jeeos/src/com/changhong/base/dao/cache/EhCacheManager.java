package com.changhong.base.dao.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ehcache mananger 类
 * @author wanghao
 */
//@Component
public class EhCacheManager {
	
	private static final Log log = LogFactory.getLog(EhCacheManager.class);

    //spring注入方式创建cacheManager
	//@Autowired
    //private CacheManager cacheManager;
	 
	//非spring 注入方式创建cacheManager
	private static final CacheManager cacheManager  = new CacheManager(EhCacheManager.class.getResourceAsStream(CacheConstants.EHCACHE_FILE_PATH));
	private static Cache cache;
	
	/**
     *获得cache，如果cache不存在，使用默认cache
	 */
	private static Cache getCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			synchronized(cacheManager) {
				cacheManager.addCache(cacheName);
				cache = cacheManager.getCache(cacheName);
				log.info("cache:"+cacheName+"未找到，使用默认缓存");
			}
		}
		return cache;
	}

	/**根据cacheName和key，从cache中获取对象
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName,Serializable key) {
		Element element = getCache(cacheName).get(key);
		if(element!=null){
			return element.getObjectValue();
		}
		return null;
	}
	
	/**根据cacheName，从cache中获取所有对象的名值对
	 * @param cacheName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,?> getAll(String cacheName) {
		Map<String,Object> map = null;
	    Element element = null;
		cache = getCache(cacheName);;
		map = new HashMap<String,Object>();
		List<String> keys = cache.getKeys();
		for(String key : keys){
			element = cache.get(key);
			if(element!=null){
				map.put(key,element.getObjectValue()) ;
			}
		}
		return map;
	}
	/**
	 * 将对象放入到指定的cache中
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName,Serializable key, Object value) {
		Element element = new Element(key, value);
    	getCache(cacheName).put(element);
	}
	/**
	 * 根据key从cache中删除数据
	 * @param cacheName
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public static void remove(String cacheName,Serializable key){
		cache = getCache(cacheName);;
		//根据完全的key移除缓存
		if(cache.remove(key)){
			log.info("remove cache,the key of cache is:"+key.toString());
		}
		
		 //根据key中的部分关键字进行移除
	    List<Serializable> keys = cache.getKeys();
	    for(Serializable fullKeyName : keys){
	    	if(fullKeyName.toString().contains(key.toString())){
	    		if(cache.remove(fullKeyName)){
					log.info("remove cache,the key of cache is:"+fullKeyName.toString());
				}
	    	}
	    }
	}
	
	/**
	 * 清除缓存中的所有数据
	 * @param cacheName
	 */
	public static void removeAll(String cacheName) {
		getCache(cacheName).removeAll();
	}
}
