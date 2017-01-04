/**
 * 
 */
package com.changhong.base.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.changhong.base.utils.ReadProperties;
import com.changhong.base.utils.http.HttpRequestUtils;

/**
 * HTTP 请求基类
 * @author wanghao
 *
 */
@Service
public class BaseHttpRequestImpl {
	
	private static final Log log = LogFactory.getLog(BaseHttpRequestImpl.class);
	/**
	 * 通过post方法从相册后台获取数据
	 * @param interfaceName
	 * @param json
	 * @return
	 */
	public String getDateByHttpPost(String interfaceName, String json){
		String url = ReadProperties.read("server_ip") + interfaceName;
		log.info("**Request url:"+url);
		log.info("****Request json:"+json);
		String httpJson = HttpRequestUtils.doPost(url,json);
		log.info("******Response json:"+httpJson);
		return httpJson;
	}

}
