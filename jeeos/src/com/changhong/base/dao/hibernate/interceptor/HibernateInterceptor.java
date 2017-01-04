package com.changhong.base.dao.hibernate.interceptor;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
/**
 * 
 * @author wanghao
 * 
 * 没发现hibernate的interceptor有什么用？？
 */
public class HibernateInterceptor extends EmptyInterceptor {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static final Log log = LogFactory.getLog(HibernateInterceptor.class);
  
	/**
	 * 在保存entity成功后，执行，返回false，继续执行其他拦截器，但拦截器的所有设置都无效
	 * 返回true，使拦截器设置生效，继续执行其他拦截器
	 */
	 public boolean onSave(Object entity, Serializable id, Object[] state,
	            String[] propertyNames, Type[] types) {    
       /*     log.info(entity);
            log.info(id);
            for(Object s : state){
            	log.info("state: "+s);
            }
            for(String s: propertyNames){
            	log.info("propertyName: "+s);
            }
            for(Type t : types){
            	log.info("type： "+t);
            }*/
            
            //设置创建时间和创建人,这样不用再每个aciton中设置了,
		    //当然也可以在每个action中设置
           /* if(entity instanceof BaseEntity){
            	BaseEntity be = ((BaseEntity) entity);
            	SysUser createUser = be.getCreateUser();
            	String createTime = be.getCreateTime();
            	if(createUser==null){
            		be.setCreateUser(Sessions.getSysUser());
            	}
            	if(StringUtils.isEmpty(createTime)){
            		be.setCreateTime(DateUtils.getCurrentDate());
            	}
            	return true;
            }*/
            //
	        //return true;
	        
	        return super.onSave(entity, id, state, propertyNames, types);  
	} 
	
}
