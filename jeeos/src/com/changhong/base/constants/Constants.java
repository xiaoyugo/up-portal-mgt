package com.changhong.base.constants;


/**
 * 静态常量
 * @author wanghao
 * 2009-03-21
 */
public class Constants {
	
	/**
	 * 用户在session中的变量名
	 */
	public static final String CH_USER_SESSION_KEY="userSessionCh";   

	/**
	 * cookie名
	 */
    public static final String COOKIE_REMEMBERME_KEY="cookie.rememberme"; 
    
    /**
     * 默认分页页面，一页显示的记录条数
     */
    public static final int PAGE_SIZE=10;
    
    /**
     * 取到sql、hql模板的数据来源：xml或database
     * 为xml时，从*.query.xml中获取
     * 为database时，从数据库查询
     */
    public static String QUERY_SQL_HQL_FROM = "xml"; 
    
    public static final String  ROOT_FUNC_ID = "1";
}
