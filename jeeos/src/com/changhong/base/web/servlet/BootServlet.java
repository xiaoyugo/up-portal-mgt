package com.changhong.base.web.servlet;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URL;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.changhong.system.service.RuleSettingService;

/**
 * 
 * 将常量数据放入缓存的servlet
 * 
 * @author wanghao
 *
 */
public class BootServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4177925985208589275L;
	
	private static final Log log = LogFactory.getLog(BootServlet.class);
	
	private RuleSettingService ruleSettingService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		log.info("Server booting...");
		log.info("OS Name:" + System.getProperty("os.name"));
		log.info("OS Version:" + System.getProperty("os.version"));
		log.info("OS Architecture:" + System.getProperty("os.arch"));
		log.info("CPU Maybe:" + System.getProperty("sun.cpu.isalist"));
		log.info("JRE Version:" + System.getProperty("java.version"));
		log.info("JRE Runtime:" + System.getProperty("java.runtime.version"));
		
		URL url = getClass().getClassLoader().getResource("/");
		String SERVER_PATH = new File(url.getPath()).getParent() + File.separator;
		log.info("SERVER_PATH:" + SERVER_PATH);
		
		String SERVER_ROOT = new File(url.getPath()).getParentFile().getParentFile().getParentFile().getParent() + File.separator;
		log.info("SERVER_ROOT:"+SERVER_ROOT);
		
		//JVM Memory Monitor
		log.info("Heap Memory Usage:"+ManagementFactory.getMemoryMXBean().getHeapMemoryUsage() );
		log.info("Non-Heap Memory Usage:"+ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage());
		
		//将一些常量数据放入缓存
		ServletContext servletContext = this.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		ruleSettingService  = (RuleSettingService) wac.getBean("ruleSettingServiceImpl",RuleSettingService.class);
    	try {
			ruleSettingService.updateRuleSettingInCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		ruleSettingService = null;
		super.destroy();
	}

}
