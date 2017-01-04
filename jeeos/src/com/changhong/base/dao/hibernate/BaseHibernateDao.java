package com.changhong.base.dao.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.changhong.base.dao.utils.builder.HqlBuilder;
import com.changhong.base.entity.Page;
/**
 * 
 * hibernate dao接口
 * @author wanghao
 *
 * @param <T>
 */
public interface BaseHibernateDao<T extends Serializable> {

	/**
	 * 保存对象
	 * @param entity
	 * @return 实体对象
	 */
	public T save(T entity) throws Exception;
	
	/**
	 * 更新对象
	 * @param entity
	 * @return 实体对象
	 */
	public T update(T entity) throws Exception;

	/**
	 * 保存或更新对象
	 * @param entity
	 * @return 实体对象
	 */
	public T saveOrUpdate(T entity) throws Exception;

	/**
	 * 删除对象
	 * @param entity
	 */
	public void delete(T entity) throws Exception;
	
	/**
	 * 由主键获得实体实例
	 * @param t
	 * @param id
	 * @return
	 */
	 public T get(Class<T> t,Serializable id) throws Exception;
	 
	/**
	 * HqlBuilder方式批量更新/批量删除
	 */
	public int batch(HqlBuilder hqlBuilder) throws Exception;
	
	/**
	 * 外部hql方式批次更新或删除
	 * @param queryName 查询name
	 * @param paramMap 参数名值对
	 * @return
	 */
	public int batch(String queryName,Map<String,?> paramMap) throws Exception;
		

	/**
	 * HqlBuilder查询，数量统计
	 * @param hqlBuilder
	 * @return
	 */
	 public int findCount(HqlBuilder hqlBuilder) throws Exception;
	 
	 /**
	  * 外部hql查询，数量统计
	  * @param queryName 查询name
	  * @param paramMap
	  * @return
	  */
	 public int findCount(String queryName,Map<String,?> paramMap) throws Exception;
	 
	 /**
	  * HqlBuilder查询，返回一个
	  * @param hqlBuilder
	  * @return
	  */
	 public T findOne(HqlBuilder hqlBuilder) throws Exception;
	 
	 /**
	  * 外部hql查询，返回一个
	  * @param queryName 查询name
	  * @param paramMap 参数名值对
	  * @return
	  */
	 public T findOne(String queryName,Map<String,?> paramMap) throws Exception;

	/**
	  * HqlBuilder查询，返回Map
	  * @param hqlBuilder hql构建器
	  * @param keyPropertyName 作为Map结果集的key
	  * @param clazzOrPropertyName 作为Map结果集的value，实体或实体中的属性名称
	  * @return 返回map结果集
	  */
	 public Map<?,?> findMap(HqlBuilder hqlBuilder,String keyPropertyName,Object clazzOrPropertyName) throws Exception;
	
	/**
	 *  外部hql查询，返回Map
	  * @param queryName 查询name
	  * @param keyPropertyName 作为Map结果集的key
	  * @param clazzOrPropertyName 作为Map结果集的value，实体或实体中的属性名称
	  * @param paramMap 参数名值对
	  * @return 返回map结果集
	  */
	public Map<?,?> findMap(String queryName,String keyPropertyName,Object clazzOrPropertyName,Map<String,?> paramMap) throws Exception;
	
	 /**
	  * HqlBuilder查询，返回List
	  * @param hqlBuilder hql构建器
	  * @return 返回List结果集
	  */
	@SuppressWarnings("unchecked")
	public List findList(HqlBuilder hqlBuilder) throws Exception;
	
	 /**
	  * 外部 hql查询，返回List
	  * @param queryName 查询name
	  * @paramMap 参数名值对
	  * @return 返回List结果集
	  */
	@SuppressWarnings("unchecked")
	public List findList(String queryName,Map<String,?> paramMap) throws Exception;
	
	/**
	 * HqlBuilder分页查询，直接返回分页对象。
	 * @param hqlBuilder hql构建器
	 * @param page:当前分页对象
	 * @return
	 */
	public Page findPage(Page page,HqlBuilder hqlBuilder) throws Exception;
	
	/**
	 * 外部 hql分页查询，直接返回分页对象。
	 * @param hqlBuilder hql构建器
	 * @param page:当前分页对象
	 * @return
	 */
	public Page findPage(Page page,String queryName,Map<String,?> paramMap) throws Exception;
	
	public List findByNameQuery(String query);
}
