package com.changhong.base.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.changhong.base.constants.Constants;
import com.changhong.base.utils.string.EncodeUtils;

/**
 * cookie实现自动登录
 * @author wanghao
 *
 */
public class CookieLoginFilter implements Filter {
	private ServletContext servletContext= null;
	public void destroy() {
		servletContext= null;
	}
	
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;


		Cookie[] cookies = request.getCookies();   
        if (cookies!=null && cookies.length>0) {   
            for (Cookie cookie : cookies) {   
                if (Constants.COOKIE_REMEMBERME_KEY.equals(cookie.getName())) {   

                	String value = cookie.getValue();
                	//解密
                    byte[] bv =  EncodeUtils.decodeHex(value);
                    
                    String loginNameAndPassword = new String(bv);
                    
                    if (StringUtils.isNotEmpty(loginNameAndPassword)) {   
                        String[] split = loginNameAndPassword.split("\\|");   
                        if(split.length==2){
                        	String loginName = split[0];   
	                        String password = split[1];      
	            	        response.sendRedirect(request.getContextPath()+"/login/login_login.do?loginName="+loginName+"&password="+password);
	            		    return;
                        }
                    }
                }   
            }   
        }   
        
	    arg2.doFilter(request, response); 
	}
	public void init(FilterConfig arg0) throws ServletException {
		servletContext=arg0.getServletContext();
	}
}
