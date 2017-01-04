/**
 * 
 */
package com.changhong.base.utils.http;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

/**
 * @author hao.wang
 *
 */
public class ChHttpClient extends DefaultHttpClient{
	
	 /** 
     * 获取 HttpClient,主要是封装了超时设置,默认请求超时5s,等待数据超时5s. 
     * @return 
     */  
    public DefaultHttpClient getHttpClient(){  
        BasicHttpParams httpParams = new BasicHttpParams();  
        HttpConnectionParams.setConnectionTimeout(httpParams, 20000);  
        HttpConnectionParams.setSoTimeout(httpParams, 20000);  
        DefaultHttpClient client = new DefaultHttpClient(httpParams);  
        return client;  
    }
    
    /** 
     * 获取 HttpClient,主要是封装了超时设置 
     * @param rTimeOut 请求超时 
     * @param sTimeOut 等待数据超时 
     * @return 
     */  
    public DefaultHttpClient getHttpClient(int rTimeOut,int sTimeOut){  
    	BasicHttpParams httpParams = new BasicHttpParams();  
    	HttpConnectionParams.setConnectionTimeout(httpParams, rTimeOut);  
    	HttpConnectionParams.setSoTimeout(httpParams, sTimeOut);  
    	DefaultHttpClient client = new DefaultHttpClient(httpParams);  
    	return client;  
    }

}
