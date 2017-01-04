package com.changhong.base.dao.hibernate.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.changhong.base.dao.hibernate.BaseHibernateDao;
import com.changhong.base.dao.utils.QueryParser;
import com.changhong.base.dao.utils.Querys;
import com.changhong.base.dao.utils.builder.HqlBuilder;
import com.changhong.base.entity.Page;
import com.changhong.base.utils.bean.ReflectionUtils;
 /**
 * 基础dao
 * @author wanghao
 * @param <T>
 */
@Repository
public final class BaseHibernateDaoImpl<T extends Serializable> implements BaseHibernateDao<T> {
	
	//private final static Log log = LogFactory.getLog(BaseHibernateDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		//ehcache是否命中
		//Statistics st = sessionFactory.getStatistics();
		//log.info("ehcach 二级缓存 命中数量："+st.getSecondLevelCacheHitCount()+"，未命中数量："+st.getSecondLevelCacheMissCount());
		//log.info("ehcach 查询缓存 命中数量："+st.getQueryCacheHitCount()+"，未命中数量："+st.getQueryCacheMissCount());
		return session;
	}
	/**
	 * 保存
	 */
	public T save(T entity) throws Exception{
		getSession().save(entity);
		return entity;
	}
    /**
     * 更新
     */
	public T update(T entity) throws Exception{
		getSession().update(entity);
		return entity;
	}
	/**
	 * 保存或更新
	 */
	public T saveOrUpdate(T entity) throws Exception{
		getSession().saveOrUpdate(entity);
		return entity;
	}
	/**
	 * 删除
	 */
	public void delete(T entity) throws Exception{
		getSession().delete(entity);
	}
	
	/**
	 * 根据id查询
	 * load()方法会使用二级缓存，而get()方法在一级缓存没有找到会直接查询数据库，不会去二级缓存中查找。
	 */
	@SuppressWarnings("unchecked")
	public T get(Class<T> t,Serializable id) throws Exception{
		return (T)getSession().get(t, id);
	}
	
	//------------------------以下是批次更新或删除的方法------------------------
	public int batch(HqlBuilder hqlBuilder) throws Exception{
		Query query = getQueryByHqlQuery(hqlBuilder.getQueryHql(),hqlBuilder);
		return query.executeUpdate();
	}
	
	public int batch(String queryName,Map<String,?> paramMap) throws Exception {
		return batch(getHqlBuilder(queryName, paramMap));
	}
	
	//---------------------------以下是数量统计的方法----------------------------
	public int findCount(HqlBuilder hqlBuilder){
		Query query = getQueryByHqlQuery(hqlBuilder.getCountHql(), hqlBuilder);
		Long l = (Long)query.uniqueResult();
		if(l!=null){
			return l.intValue();
		}
		return 0;
	}
	
	public int findCount(String queryName,Map<String,?> paramMap) throws Exception{
		return findCount(getHqlBuilder(queryName, paramMap));
	}
	
	//----------------------------以下是返回一个实体类的方法------------------------
	@SuppressWarnings("unchecked")
	public T findOne(HqlBuilder hqlBuilder){
		 List list = findList(hqlBuilder);
		 if(list!=null && list.size()>0){
			 return (T)list.get(0);
		 }
		 return null;
	 }
	
	public T findOne(String queryName,Map<String,?> paramMap) throws Exception{
		return findOne(getHqlBuilder(queryName, paramMap));
	}

	//----------------------------以下是返回map的方法-------------------------------
	@SuppressWarnings("unchecked")
	public Map<?,?> findMap(HqlBuilder hqlBuilder,String keyPropertyName,Object clazzOrPropertyName) throws Exception{
		List list = findList(hqlBuilder);
		Map<Object,Object> map = new HashMap<Object,Object>();
		if(list!=null && list.size()>0){
			for(Object obj : list){
				if(clazzOrPropertyName==obj.getClass()){
					map.put(ReflectionUtils.getFieldValue(obj, keyPropertyName), obj);
				}else if(clazzOrPropertyName instanceof String){
					map.put(ReflectionUtils.getFieldValue(obj, keyPropertyName), ReflectionUtils.getFieldValue(obj, clazzOrPropertyName+""));
				}
			}
		}
		return map;
	}
	
	public Map<?,?> findMap(String queryName,String fieldName,Object clazzOrPropertyName,Map<String,?> paramMap) throws Exception{
		return findMap(getHqlBuilder(queryName, paramMap), fieldName,clazzOrPropertyName);
	}
	
   //--------------------------------以下是返回list 的方法-----------------------------
	@SuppressWarnings("unchecked")
	public List findList(HqlBuilder hqlBuilder){
		return getQueryByHqlQuery(hqlBuilder.getQueryHql(),hqlBuilder).list();
	}
	
	@SuppressWarnings("unchecked")
	public List findList(String queryName,Map<String,?> paramMap) throws Exception{
		return findList(getHqlBuilder(queryName, paramMap));
	}
	
    //----------------------- 以下是分页查询-----------------------------------------------
	/**
	 * hql语句查询，返回分页对象
	 * @param hqlBuilder
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page findPage(Page page,HqlBuilder hqlBuilder){
		int currentPage = page.getCurrentPage();
		if(currentPage==0){page.setCurrentPage(1);currentPage=1;}
		int pageSize = page.getPageSize();
		//设置hibernate分页参数
		Query query = getQueryByHqlQuery(hqlBuilder.getQueryHql(),hqlBuilder);
		query.setFirstResult(pageSize*(currentPage-1));
		query.setMaxResults(pageSize);
		
		//获得记录总行数
		int totalRecords = findCount(hqlBuilder);
		
		//返回分页对象
		return new Page(query.list(),currentPage,totalRecords,pageSize,page.getOrderattr(),page.getOrdertype());
	}
	
	public Page findPage(Page page,String queryName,Map<String,?> paramMap) throws Exception{
		
	    //使用临时map，是为了设置排序属性和排序类型
		Map<String,Object> mapTemp = new HashMap<String,Object>();
		if(paramMap!=null){
			for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
				 mapTemp.put(entry.getKey().toString(), entry.getValue());
			}
		}
		
		mapTemp.put(Querys.ORDER_ATTR,page.getOrderattr());
		mapTemp.put(Querys.ORDER_TYPE, page.getOrdertype());
		
		return findPage(page, getHqlBuilder(queryName, mapTemp));
	}
	
	
    //---------------------------------辅助方法--------------------------------------------------------
	private Query getQueryByHqlQuery(String hql,HqlBuilder hqlBuilder){
		Query query = getSession().createQuery(hql);
		//启用查询缓存 
		//在第一次执行list时,把查询结果集放入查询缓存
	    //第二次执行list时, 先遍历查询缓存，如果没有找到，就去数据库查询
		query.setCacheable(true);
		hqlBuilder.setParamsToQuery(query);
		return query;
	}

	/**
	 * 根据外部hql及参数，封装成HqlBuilder
	 * @param queryName
	 * @param paramMap
	 * @return
	 * @throws Exception 
	 */
	private HqlBuilder getHqlBuilder(String queryName,Map<String,?> paramMap) throws Exception{
		String hql = QueryParser.getQueryString(queryName, paramMap);
		HqlBuilder hqlBuilder = new HqlBuilder(hql);
		hqlBuilder.setParamMap(paramMap);
		return hqlBuilder;
	}
	
	@Override
	public List findByNameQuery(String hql) {
		Query query = getSession().createQuery(hql);
		return query.list();
	}
}

