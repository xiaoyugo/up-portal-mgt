package com.changhong.base.web.taglib;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.collections.CollectionUtils;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.changhong.base.web.Sessions;
import com.changhong.system.entity.ChRolebutton;
import com.changhong.system.entity.ChUser;

/**
 * 自定义权限标签
 * @author wanghao
 */
public class NoAuthTag extends BodyTagSupport{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//封装属性  
    private String funcId=""; //模块ID既菜单ID
    private String buttonCode="";//按钮代码
      
    public int doStartTag(){  
       boolean hasPermission = false;
       try{
           funcId = ExpressionEvaluatorManager.evaluate(funcId+"", funcId.toString(), Object.class, this, pageContext).toString();
           buttonCode = ExpressionEvaluatorManager.evaluate(buttonCode+"", buttonCode.toString(), Object.class, this, pageContext).toString();
           ChUser user = Sessions.getChUser();
           if(user != null){
               Map<String, List<ChRolebutton>> funcBtnMap = user.getFuncBtnMap();
               List<ChRolebutton> roleBtnList = funcBtnMap.get( funcId );
               if(CollectionUtils.isNotEmpty( roleBtnList )){
                   for(ChRolebutton roleBtn:roleBtnList){
                       if(roleBtn.getChButton().getChButtonCode().equals( buttonCode )){
                           hasPermission = true;
                           break;
                       }
                   }
               }
           }
	   }catch (JspException e) {
		   e.printStackTrace();
	   }
	   if(hasPermission){//具有权限 不输出disabled="disabled"
            return Tag.SKIP_BODY;  
       }  
       return Tag.EVAL_BODY_INCLUDE;  
    }
    
    public String getFuncId()
    {
        return funcId;
    }

    public void setFuncId( String funcId )
    {
        this.funcId = funcId;
    }

    public String getButtonCode()
    {
        return buttonCode;
    }

    public void setButtonCode( String buttonCode )
    {
        this.buttonCode = buttonCode;
    }
}
