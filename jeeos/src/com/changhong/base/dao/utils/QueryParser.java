package com.changhong.base.dao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.changhong.base.constants.Constants;
import com.changhong.base.dao.cache.CacheConstants;
import com.changhong.base.dao.cache.EhCacheManager;
import com.changhong.base.utils.bean.JaxbMapper;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 外部查询文件解析器，获得查询语句：hql或sql
 * @author wanghao
 */
public class QueryParser {
	
	private final static Log log = LogFactory.getLog(QueryParser.class);
	
	/**
	 * 获得外部查询字符串
	 * @param queryName 查询id
	 * @param paramMap 参数paramMap
	 * @return
	 */
	public static String getQueryString(String queryName, Map<String,?> paramMap)throws Exception {  
       
		  //过滤掉value是null或""的数据
          Map<String,Object> map = new HashMap<String,Object>();
          if(paramMap!=null && paramMap.size()>0){
            for (Map.Entry<String, ?> entry : paramMap.entrySet()) {
  			  Object value = entry.getValue();
  				if(value!=null && !"".equals((value+"").trim()) && !"%null%".equals(value+"") && !"%%".equals(value+"")){
  					map.put(entry.getKey().toString(), entry.getValue());
  				}
  			}
          }
          Configuration cfg = new Configuration();  
          //字符串模板加载器
          StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();  
          //获得hql或sql模板
          String queryStringTemplate = getQueryStringTemplate(queryName);
          if(queryStringTemplate==null || "".equals(queryStringTemplate)){
          	   throw new Exception("查询名称为：queryName："+queryName+"的查询不存在");  
          }
          log.info("queryName is :"+queryName);
          stringTemplateLoader.putTemplate(queryName, queryStringTemplate);  
            
          cfg.setTemplateLoader(stringTemplateLoader);  
          cfg.setDefaultEncoding("UTF-8");  
          Template template = cfg.getTemplate(queryName,"UTF-8"); 
            
          StringWriter writer = new StringWriter();   
            
          template.process(map, writer);  
            
          //获得查询字符串
          String queryString = writer.toString().trim();
            
          return queryString;  
    }  

	/**
	 * 根据queryName获得hql或sql语句模板
	 * @param queryName
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("unchecked")
	private static String getQueryStringTemplate(String queryName) throws Exception{
    	Map<String,String> allQueryMap = (Map<String, String>) EhCacheManager.get(CacheConstants.CONSTANTS_DATE_CACHE, CacheConstants.QUERY_SQL_HQL_KEY);
    	String queryTemplate = "";
    	if(Constants.QUERY_SQL_HQL_FROM.equals("xml")){
    		queryTemplate = getAllQueryTemplateMap().get(queryName);
    	}else if(allQueryMap.size()>0){
    		queryTemplate = allQueryMap.get(queryName);
    	}
  	    return queryTemplate;
    }
    
    /**
     * 获得外部所有查询语句的hql和sql的模板，放入map
     * 
     * 上线后，在服务启动时执行该方法，并将返回结果方法缓存
     * 
     * 开发时，每次都执行该方法，避免反复启动服务
     * 
     * @return
     * @throws IOException
     */
    public static Map<String,String> getAllQueryTemplateMap() throws Exception{
    	//map：key：queryName，value：hql或sql
    	Map<String,String> queryTemptateMap = new HashMap<String,String>();
    	
    	//读取query.xml
    	URL url = QueryParser.class.getResource(Querys.QUERY_FILE_PATH);
    	InputStream in = url.openStream();
//    	InputStream in = new FileInputStream(new File(url.toURI()));
    	
	    QueryFiles queryFile = JaxbMapper.fromXml(IOUtils.toString(in,"UTF-8"),QueryFiles.class);
	    
	    //获得query.xml中所有的<query file=""/>
	    List<QueryFiles.Query> queryFileList = queryFile.getQuery();
	    if(queryFileList!=null){
	    	for(QueryFiles.Query query : queryFileList){
		    	String filePath = query.getFile();
		    	
		    	//根据filePath读取所有的*.query.xml
		    	url = QueryParser.class.getResource("/"+filePath);
		    	in = url.openStream();
//		    	in = new FileInputStream(new File(url.toURI()));
		    	if(in==null){
		    		throw new Exception("文件："+filePath+"不存在");
		    	}
		    	QueryMapping queryMapping = JaxbMapper.fromXml(IOUtils.toString(in,"UTF-8"),QueryMapping.class);
		    	
		    	//将*.query.xml中的所有queryName和hql语句、sql语句放入map
		    	for(QueryMapping.Query q : queryMapping.getQuery()){
		    		String queryName = q.getName();
		    		if(queryTemptateMap.containsKey(queryName)){
		    			throw new Exception("外部查询语句的queryName重复，queryName："+queryName);
		    		}
		    		String hql = q.getHql();
		    		String sql = q.getSql();
		    	
		    		if(StringUtils.isNotBlank(hql) && StringUtils.isNotBlank(sql)){
		    			throw new Exception("外部查询语句的hql语句和sql不可同时出现在同一个query中，queryName："+queryName);
		    		}
		    		if(StringUtils.isNotBlank(hql)){
		    			queryTemptateMap.put(queryName,hql);
		    		}else if(StringUtils.isNotBlank(sql)){
		    			queryTemptateMap.put(queryName,sql);
		    		}else{
		    			throw new Exception("外部查询语句的hql或sql不存在，queryName："+queryName);
		    		}
		  	    }
		    }
	    }
	    IOUtils.closeQuietly(in);
	    //上线后，赋给静态变量
	    //ALL_QUERY_MAP = queryTemptateMap;
	    
	    return queryTemptateMap;
    }
    
    
    public static void main(String[] args) throws Exception{
    	/*Map<String,Object> m = new HashMap<String, Object>();
    	m.put("loginName", "1");
    	m.put("email", "1");
    	System.out.println(getQueryString("3",m));*/
    	String fm = "[:]\\s*\\w+";
    	java.util.regex.Pattern p = Pattern.compile(fm);
    	Matcher matcher = p.matcher("dfdfd :      eeeee  ee  eee343   aaa4   adfdfdfdf");  
    	while (matcher.find()) {
    		System.out.println("wwww"+matcher.group());
		}
    }
}
