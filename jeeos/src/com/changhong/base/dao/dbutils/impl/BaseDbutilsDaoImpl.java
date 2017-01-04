package com.changhong.base.dao.dbutils.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.dao.dbutils.BaseDbutilsDao;
import com.changhong.base.dao.utils.QueryParser;
import com.changhong.base.dao.utils.Querys;
import com.changhong.base.dao.utils.builder.SqlBuilder;
import com.changhong.base.entity.Page;
import com.changhong.base.utils.bean.Objects;
import com.changhong.base.utils.bean.ReflectionUtils;
/**
 * dbutils dao接口 实现类
 * @author wanghao
 */
@Service(value="baseDbutilsDao")
@Transactional
public class BaseDbutilsDaoImpl implements BaseDbutilsDao {

	@Autowired
	private DataSource dataSource; 
	
	protected QueryRunner getQueryRunner() {
		QueryRunner queryRunner = new QueryRunner(dataSource);
		return queryRunner;
	}

	public int update(SqlBuilder sqlBuilder) throws Exception {
		return getQueryRunner().update(sqlBuilder.getQuerySql(), sqlBuilder.getValues());
	}

	public int update(String queryName, Map<String, ?> paramMap)throws Exception {
		return update(getSqlBuilder(queryName, paramMap));
	}
	
	public int[] batch(String sql, Object[][] params) throws Exception {
		return getQueryRunner().batch(sql, params);
	}

	//----------------------------数量统计---------------------------------------
	@SuppressWarnings("unchecked")
	public long findCount(SqlBuilder sqlBuilder) throws Exception {
		Number num = (Number) getQueryRunner().query(sqlBuilder.getTotalRecordsSql(),new ScalarHandler(), sqlBuilder.getValues());
		
		
		return (num != null) ? num.longValue() : -1;
	}

	public long findCount(String queryName,Map<String, ?> paramMap) throws Exception {
		return findCount(getSqlBuilder(queryName, paramMap));
	}

	//---------------------------查询一条记录，封装到实体T
	@SuppressWarnings("unchecked")
	public <T> T findOne(Class<T> beanClass, SqlBuilder sqlBuilder)throws Exception {
		String sql = sqlBuilder.getQuerySql();
		Object[] params = sqlBuilder.getValues();
		return (T) getQueryRunner().query(sql,Objects.isPrimitive(beanClass)? new ScalarHandler(): new BeanHandler(beanClass), params);
	}

	public <T> T findOne(Class<T> beanClass, String queryName,Map<String, ?> paramMap) throws Exception {
		return findOne(beanClass,getSqlBuilder(queryName, paramMap));
	}
	
	
	//---------------------------查询多条记录，封装到List<T>
	@SuppressWarnings("unchecked")
	public <T> List<T> findList(Class<T> beanClass, SqlBuilder sqlBuilder)throws Exception {
		String sql = sqlBuilder.getQuerySql();
		Object[] params = sqlBuilder.getValues();
		return (List<T>) getQueryRunner().query(sql,Objects.isPrimitive(beanClass)? new  ColumnListHandler(): new BeanListHandler(beanClass), params);
	}

	public <T> List<T> findList(Class<T> beanClass, String queryName,Map<String, ?> paramMap) throws Exception {
		return findList(beanClass,getSqlBuilder(queryName, paramMap));
	}

	//---------------------------执行查询，将结果集保存到Map
	@SuppressWarnings("unchecked")
	public <T> Map<?, ?> findMap(Class<T> beanClass,SqlBuilder sqlBuilder,
			String keyPropertyName,Object clazzOrPropertyName) throws Exception {
		String sql = sqlBuilder.getQuerySql();
		Object[] params = sqlBuilder.getValues();
		
		List<T> list = (List<T>) getQueryRunner().query(sql,Objects.isPrimitive(beanClass) ? new  ColumnListHandler(): new BeanListHandler(beanClass), params);
		Map<Object,Object> map = new HashMap<Object,Object>();
		if(list!=null && list.size()>0){
			for(Object obj : list){
				if(clazzOrPropertyName==obj.getClass()){
					map.put(ReflectionUtils.getFieldValue(obj, keyPropertyName), obj);
				}else if(clazzOrPropertyName instanceof String){
					map.put(ReflectionUtils.getFieldValue(obj, keyPropertyName), ReflectionUtils.getFieldValue(obj, clazzOrPropertyName+""));
				}
			}
			for(Object obj : list){
				map.put(ReflectionUtils.getFieldValue(obj, keyPropertyName), (T) obj);
			}
		}
		return map;
	}

	public <T> Map<?, ?> findMap(Class<T> beanClass, String queryName,Map<String, ?> paramMap, 
			String keyPropertyName,Object clazzOrPropertyName)throws Exception {
		return findMap(beanClass,getSqlBuilder(queryName, paramMap),keyPropertyName,clazzOrPropertyName);
	}

//	@SuppressWarnings("unchecked")
//	public <T> Page findPage(Class<T> beanClass, SqlBuilder sqlBuilder,Page page) throws Exception {
//		//查询总条数
//		Long totalRecords = findCount(sqlBuilder);
//		
//		//mysql limit 分页：第一个参数指定返回的第一行在所有数据中的位置，从0开始（注意不是1），第二个参数指定最多返回行 
//		int currentPage = page.getCurrentPage(), pageSize = page.getPageSize();
//		String pageSql =sqlBuilder.getQuerySql() +" LIMIT "+pageSize * (currentPage - 1)+","+pageSize;
//		sqlBuilder.clear().append(pageSql);
//		
//		List list = findList(beanClass,sqlBuilder);
//		page = new Page(list, currentPage, Integer.parseInt(totalRecords+""), pageSize,page.getOrderattr(),page.getOrdertype());
//		return page;
//	}
	
	@SuppressWarnings("unchecked")
	public <T> Page findPage(Class<T> beanClass, SqlBuilder sqlBuilder,Page page) throws Exception {
		//查询总条数
		Long totalRecords = findCount(sqlBuilder);
		
		//mysql limit 分页：第一个参数指定返回的第一行在所有数据中的位置，从0开始（注意不是1），第二个参数指定最多返回行 
		int currentPage = page.getCurrentPage(), pageSize = page.getPageSize();
		int statrow =currentPage>0?pageSize * (currentPage - 1):0; 
		String pageSql =sqlBuilder.getQuerySql() +" OFFSET    "+statrow+" rows FETCH NEXT "+pageSize+" rows only ";
		sqlBuilder.clear().append(pageSql);
		
		List list = findList(beanClass,sqlBuilder);
		page = new Page(list, currentPage, Integer.parseInt(totalRecords+""), pageSize,page.getOrderattr(),page.getOrdertype());
		return page;
	}

	public <T> Page findPage(Class<T> beanClass, Page page, String queryName,Map<String, ?> paramMap) throws Exception {
		//使用临时map，是为了设置排序属性和排序类型
		Map<String,Object> mapTemp = new HashMap<String,Object>();
		if(paramMap!=null && paramMap.size()>0){
			for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
				 mapTemp.put(entry.getKey().toString(), entry.getValue());
			}
		}
		mapTemp.put(Querys.ORDER_ATTR,page.getOrderattr());
		mapTemp.put(Querys.ORDER_TYPE, page.getOrdertype());
		return findPage(beanClass, getSqlBuilder(queryName, mapTemp), page);
	}

	//--------------------------------------辅助方法-----------------------------------------------------------
	 /**
     * 获得SqlBuilder
     * @param queryName
     * @param paramMap
     * @return
	 * @throws Exception 
     */
    private SqlBuilder getSqlBuilder(String queryName,Map<String,?> paramMap) throws Exception{

    	String sql = QueryParser.getQueryString(queryName, paramMap);
    	//变量名称List
    	List<String> paramNameList = new ArrayList<String>();
    	//变量值List
    	List<Object> paramValueList = new ArrayList<Object>();
    	    	
    	//将sql中的":变量名"替换成?
    	String fm = "(:)\\s*\\w+";
    	java.util.regex.Pattern p = Pattern.compile(fm);
    	Matcher matcher = p.matcher(sql);  
    	while (matcher.find()) {
    	    paramNameList.add(matcher.group());
        }
    	if(paramNameList.size()>0){
	    	for(String s : paramNameList){
	    	    sql = sql.replace(s,"?");
	    	    s = s.trim().replace(":","").trim();
	    	    if(paramMap.get(s)!=null && !"".equals((paramMap.get(s)+"").trim())){
	    	    	 paramValueList.add(paramMap.get(s));
	    	    }
	    	}
    	}

    	SqlBuilder sqlBuilder = new SqlBuilder(sql);
    	sqlBuilder.setParams(paramValueList);
    	return sqlBuilder;
    }
}
