package com.changhong.base.dao.cache;

/**
 * 缓存静态常量
 * @author wanghao
 */
public class CacheConstants {

	/**
	 * ehcache.xml文件路径
	 */
	public static final String EHCACHE_FILE_PATH= "/resources/datasource/ehcache.xml";
    /**
     * HttpSession 缓存名称，需与ehcache.xml中的名称保持一致
     */
    public static final String HTTP_SESSION_CACHE = "httpSessionCache";
    
    /**
     * 系统常量数据 缓存名称，这些数据来自数据库，设置完毕后一般不轻易变化，如系统参数、系统设置等数据
     */
    public static final String CONSTANTS_DATE_CACHE = "constantsDateCache";
    
    /**
     * 数据字典在缓存中的key
     */
    public static final String DATA_DICTIONARY_KEY = "dataDictionaryKey";
    
    /**
     * 系统规则在缓存中的key
     */
    public static final String CH_RULE_SETTING_KEY = "chRuleSettingKey";
    
    /**
     * 查询语句在缓存中的key
     */
    public static final String QUERY_SQL_HQL_KEY = "querySqlHqlKey";
    /**
     * 动线数据在缓存中的key
     */
    public static final String FLOWLINE_KEY = "flowLineKey";
}
