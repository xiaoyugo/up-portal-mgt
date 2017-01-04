package com.changhong.base.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

/**
 * 测试用的HTTP请求公共类
 * @author wanghao
 * 2013-12-04 18:08
 */
public class HttpRequestUtils {
	
	private static final Log log = LogFactory.getLog(HttpRequestUtils.class);

	public static String doPost(String url, String json){
		String message = null;
		DefaultHttpClient client = new ChHttpClient().getHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {			
			@SuppressWarnings("deprecation")
			StringEntity entity = new StringEntity(json, HTTP.UTF_8);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			
			HttpResponse response = client.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			StringBuffer buffer = new StringBuffer();
			InputStreamReader inputReader = new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferReader = new BufferedReader(inputReader);
			String str = new String("");

			while ((str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferReader.close();
			message = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("do post 请求超时:"+url);
			return "error";
		}finally{
			httpPost.releaseConnection();
		}
		return message;
	}
	
	public static String doGet(String url){
		String message = null;
		DefaultHttpClient client = new ChHttpClient().getHttpClient();	
		HttpGet httpGet = new HttpGet(url);
		try {			

			HttpResponse response = client.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			StringBuffer buffer = new StringBuffer();
			InputStreamReader inputReader = new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferReader = new BufferedReader(inputReader);
			String str = new String("");
			while ((str = bufferReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferReader.close();
			message = buffer.toString();
			log.info(message);
			httpGet.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			log.info("do get 请求超时:"+url);
			return "error";
		}finally{
			httpGet.releaseConnection();
		}
		return message;
	}
}

