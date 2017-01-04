package com.changhong.base.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.dao.hibernate.BaseHibernateDao;
import com.changhong.base.dao.utils.builder.HqlBuilder;
import com.changhong.base.entity.Page;
import com.changhong.base.service.BaseService;
@Service
@Transactional
public class BaseServiceImpl<T extends Serializable> implements BaseService<T> {
	
	private Class<T> entityClass;
	
	/**
	 * 通过反射,运行时动态获得类型信息，即获得T.class对象
	 */
	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
	      entityClass =(Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Autowired
	private BaseHibernateDao<T> dao;

	public T save(T entity) throws Exception{
		return dao.save(entity);
	}

	public T saveOrUpdate(T entity) throws Exception{
		return dao.saveOrUpdate(entity);
	}

	public T update(T entity) throws Exception{
		return dao.update(entity);
	}

	public void delete(T entity) throws Exception{
		dao.delete(entity);
	}
	
	public List<T> deleteByIds(Serializable[] ids) throws Exception{
		List<T> dts = new ArrayList<T>();
		T del = null;
		if (ids != null && ids.length > 0){
			for (Serializable id : ids) {
				del = dao.get(entityClass, id);
				if (del != null) {
					dts.add(del);
					dao.delete(del);
				}
			}
		}
		return dts;
	}
	public boolean deleteById(Serializable id) throws Exception{
		T del = null;
		del = dao.get(entityClass, id);
		if (del != null) {
			dao.delete(del);
			return true;
		}
		return false;
	}
	public T get(Serializable id) throws Exception{
		
		return dao.get(entityClass,id);
	}

	public int batch(HqlBuilder hqlBuilder) throws Exception{
		return dao.batch(hqlBuilder);
	}
		
	public int batch(String queryName,Map<String,?> paramMap) throws Exception{
		return dao.batch(queryName, paramMap);
	}
	
	public int findCount(HqlBuilder hqlBuilder) throws Exception{
		return dao.findCount(hqlBuilder);
	}
	public int findCount(String queryName,Map<String,?> paramMap) throws Exception{
		return dao.findCount(queryName, paramMap);
	}
	public T findOne(HqlBuilder hqlBuilder) throws Exception{
		return dao.findOne(hqlBuilder);
	}
	
	public T findOne(String queryName,Map<String,?> paramMap) throws Exception{
		return dao.findOne(queryName, paramMap);
	}

	public Map<?,?> findMap(HqlBuilder hqlBuilder,String keyPropertyName,Object clazzOrPropertyName) throws Exception{
		return dao.findMap(hqlBuilder, keyPropertyName,clazzOrPropertyName);
	}
	
	public Map<?, ?> findMap(String queryName, String keyPropertyName,Object clazzOrPropertyName,Map<String,?> paramMap) throws Exception{
		return dao.findMap(queryName, keyPropertyName,clazzOrPropertyName, paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public List findList(HqlBuilder hqlBuilder) throws Exception{
		
		return dao.findList(hqlBuilder);
	}
	
	@SuppressWarnings("unchecked")
	public List findList(String queryName,Map<String,?> paramMap) throws Exception{
		return dao.findList(queryName, paramMap);
	}
	
    public Page findPage(Page page, HqlBuilder hqlBuilder) throws Exception{
		
		return dao.findPage(page, hqlBuilder);
	}
    public Page findPage(Page page,String queryName,Map<String,?> paramMap) throws Exception{
    	return dao.findPage(page, queryName, paramMap);
    }

	@Override
	public List findByNameQuery(String query) {
		return dao.findByNameQuery(query);
	}
	
}
