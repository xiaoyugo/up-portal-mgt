package com.changhong.base.utils.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.changhong.base.web.Servlets;


public class HttpClientUtils {
	
	//private static Log log = LogFactory.getLog(HttpClientUtil.class);
	
	/**
	 * HttpClient返回InputStream
	 * 目标地址有大量数据传输时，使用该方法最佳
	 * @param actionName
	 * @param param
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public static InputStream getResponseBodyAsInputStream(String actionName,Map<String,String> param) throws Exception{
		HttpEntity entity = getHttpEntity(actionName, param);
		InputStream in = null;
		if(entity!=null){
			in = entity.getContent();
		}
		return in;
	}
	
	/**
	 * HttpClient返回String
	 * String的编码是根据系统默认的编码方式
	 * @param actionName
	 * @param param
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static String getResponseBodyAsString(String actionName,Map<String,String> param) throws Exception{
		
		HttpEntity entity = getHttpEntity(actionName, param);
		String str = null;
		if(entity!=null){
			str = EntityUtils.toString(entity, "UTF-8");
		}
		return str;
	}
	/**
	 * 获得httpclient的返回结果
	 * @param actionName
	 * @param param
	 * @return
	 */
	private static HttpEntity getHttpEntity(String actionName,Map<String,String> param){
       
		HttpEntity entity = null;
        HttpClient httpClient = new DefaultHttpClient();
		
		HttpPost httpPost = new HttpPost(Servlets.genUrl(actionName,null));
		
		//参数
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		try {
			if(param!=null && param.size()>0){
				for(String key : param.keySet()){
					formparams.add(new BasicNameValuePair(key, param.get(key)));
				}
				UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
				httpPost.setEntity(urlEncodedFormEntity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			entity = response.getEntity();
		}catch (IOException e) {
			e.printStackTrace();
		} finally{
			//释放链接
			httpPost.releaseConnection();
		}
		return entity;
	}
}
