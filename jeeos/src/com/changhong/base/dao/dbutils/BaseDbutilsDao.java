package com.changhong.base.dao.dbutils;

import java.util.List;
import java.util.Map;

import com.changhong.base.dao.utils.builder.SqlBuilder;
import com.changhong.base.entity.Page;

/**
 * dbutils dao接口
 * @author wanghao
 *
 */
public interface BaseDbutilsDao {
	
	/**
	 * 执行insert/update/delete语句
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public int update(SqlBuilder sqlBuilder) throws Exception;
	/**
	 * 外部sql，执行insert/update/delete语句
	 * @param queryName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int update(String queryName,Map<String,?> paramMap) throws Exception;

	/**
	 * 批量执行指定的SQL语句
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public int[] batch(String sql, Object[][] params) throws Exception;
    
	/**
	 * 执行统计查询语句，语句的执行结果必须只返回一个数值
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public long findCount(SqlBuilder sqlBuilder) throws Exception;
    /**
     * 外部sql查询
     * @param queryName
     * @param paramMap
     * @return
     * @throws Exception
     */
	public long findCount(String queryName,Map<String,?> paramMap) throws Exception;
	  /** 
     * 查询出结果集中的第一条记录，并封装成对象 
     * @param beanClass 类名 
     * @param sqlBuilder
     * @return 对象 
     */ 
	public <T> T findOne(Class<T> beanClass, SqlBuilder sqlBuilder) throws Exception;
	/**
	 * 外部sql查询
	 * @param <T>
	 * @param beanClass
	 * @param queryName 查询名
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public  <T> T findOne(Class<T> beanClass,String queryName,Map<String,?> paramMap) throws Exception;

    /** 
     * 执行查询，将结果集的每行结果保存到Bean中，然后将所有Bean保存到List中 
     * @param beanClass 类名 
     * @param sqlBuilder
     * @return 查询结果 
     */ 
	public <T> List<T> findList(Class<T> beanClass, SqlBuilder sqlBuilder) throws Exception;
	/**
	 * 外部sql查询，返回list
	 * @param <T>
	 * @param beanClass
	 * @param queryName
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public  <T> List<T> findList(Class<T> beanClass,String queryName,Map<String,?> paramMap) throws Exception;
	/**
	 * 执行查询，结果集保存到Map中
	 * @param <T>
	 * @param beanClass
	 * @param sqlBuilder
	 * @param keyPropertyName 作为Map结果集的key
	 * @param clazzOrPropertyName 作为Map结果集的value，实体或实体中的属性名称
	 * @return
	 * @throws Exception
	 */
	public <T> Map<?, ?> findMap(Class<T> beanClass,SqlBuilder sqlBuilder,String keyPropertyName,Object clazzOrPropertyName) throws Exception;
	/**
	 * 外部sql查询，返回Map
	 * @param <T>
	 * @param beanClass
	 * @param paramMap
	 * @param keyPropertyName 作为Map结果集的key
	 * @param clazzOrPropertyName 作为Map结果集的value，实体或实体中的属性名称
	 * @return
	 * @throws Exception
	 */
	public  <T> Map<?, ?> findMap(Class<T> beanClass,String queryName,
			Map<String,?> paramMap,String keyPropertyName,Object clazzOrPropertyName) throws Exception;

	/**
     *  mysql 分页查询,仅适用于mysql
     * @param entityClass
     * @param sqlBuilder
     * @param page
     * @return
     */
	public <T> Page findPage(Class<T> beanClass,SqlBuilder sqlBuilder,Page page) throws Exception;
	
	/**
	 * 外部sql分页查询，
	 * @param <T>
	 * @param beanClass 封装查询出来的数据的对象
	 * @param page 分页对象
	 * @param queryName 查询名
	 * @param paramMap 参数名值对
	 * @return
	 * @throws Exception
	 */
    public <T> Page findPage(Class<T> beanClass,Page page, String queryName,
    		Map<String,?> paramMap) throws Exception;

}
