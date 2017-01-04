package com.changhong.base.web.struts2.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.changhong.base.annotation.Menu;
import com.changhong.base.constants.enums.Auth;
import com.changhong.base.web.Servlets;
import com.changhong.base.web.Sessions;
import com.changhong.base.web.struts2.action.BaseAuthAction;
import com.changhong.system.entity.ChUser;
import com.changhong.system.service.RoleService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * action 方法级别的权限控制拦截器
 * 
 * @author wanghao
 * 2014-08-15
 *
 */
public class AuthInterceptorCh  extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(AuthInterceptorCh.class);
	@Autowired
	private RoleService roleService;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		 Object action = invocation.getAction();
	  
	     if(Sessions.getChUser()!=null && action instanceof BaseAuthAction){
	    	 Class<?> actionClass = action.getClass();
	  	     ValueStack valueStack = invocation.getStack();
	    	 
	    	 String methodName = invocation.getProxy().getMethod();
		     Method method = actionClass.getMethod(methodName);
		     HttpServletRequest request = Servlets.getRequest();
		    
		     //用户可访问的所有菜单url
		     ChUser user = Sessions.getChUser();
	    	 List<String> menuPaths = user.getFuncPaths();
	    	 
	    	 //当前正在访问的url
		     String menuPath = request.getRequestURI();
		     menuPath = menuPath.replace(request.getContextPath()+"/", "");
		     menuPath = "/"+menuPath;
		     
		     Menu menuAuth = method.getAnnotation(Menu.class);
		     
		     if(menuAuth != null){
		    	 Auth auth = roleService.findAuthByRoleId(user.getRoleId(),menuPath);
			    if(auth == Auth.N){
			    	return "noPermession";
			    }else{
			    	valueStack.setValue("auth", auth);
			    }
		     }
		     //对菜单进行权限扫描
		    /* if(menuPaths.contains(menuPath)){
		    	 menuAuth = method.getAnnotation(Menu.class);
		    	 if(menuAuth==null){
		    		 log.info("菜单："+menuPath+" 没有加上注解@Menu，该url及其所在的action都不进行权限扫描");
		    	 }
		     }else{//非菜单url，对其所在action的菜单url进行权限扫描
		    	 //log.info(menuPath+" 不是菜单，对该url所在actoin中的菜单进行权限扫描");
		    	 for(Method m : actionClass.getDeclaredMethods()){
			    		menuAuth = m.getAnnotation(Menu.class);
					    if(menuAuth!=null){
					    	menuPath=menuPath.split("_")[0]+"_"+m.getName()+".do";
					    	break;
					    }
					 }
		     }
		     if(menuAuth!=null){
		    	 //查询权限
		    	 Auth auth = roleService.findAuthByRoleId(user.getRoleId(),menuPath);
			     if(auth!=null){
			    	 if(auth==Auth.N){
			    		 return "noPermession";
			    	 }else{
			    		 valueStack.setValue("auth", auth);
			    	 }
		         }
		     }
		     */
	     }
	    
	     return invocation.invoke();
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
