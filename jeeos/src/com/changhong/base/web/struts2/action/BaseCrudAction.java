package com.changhong.base.web.struts2.action;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.changhong.base.web.Sessions;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * Struts2 CRUD 基类
 * 
 * 如果没有涉及到CRUD操作，action可继承ActionSupport或BaseAuthAction
 * 
 * @author wanghao
 */
public abstract class BaseCrudAction<T> extends BaseAuthAction implements ModelDriven<T>, Preparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final Log log = LogFactory.getLog(BaseCrudAction.class);
	
	//=============action 方法返回值=================================
	public static final String ADD = "add";
	public static final String UPDATE = "update";
	public static final String DETAIL = "detail";
	public static final String LIST = "list";
	public static final String TREE = "tree";
	
	private String currentFuncId = "";
	

	//=============prepare 方法=======================================
	/**
	 * prepare 方法，在执行save、update、detail方法前执行
	 * @throws Exception
	 */
	public abstract void prepareModel() throws Exception;
	
	public void prepare() throws Exception {
	}

	public void prepareSave() throws Exception {
		prepareModel();
	}
	public void prepareUpdate() throws Exception {
		prepareModel();
	}
	public void prepareDetail() throws Exception {
		prepareModel();
	}
	
	//=============CRUD方法============================================
	/**
	 * 进入新增
	 */
	public abstract String add() throws Exception;
	
	/**
	 * 进入修改
	 */
	public abstract String update() throws Exception;
	
	/**
	 * 进入查看详细
	 */
	public abstract String detail() throws Exception;

	/**
	 * 新增、修改
	 */
	public abstract String save() throws Exception;
	
	/**
	 * 显示列表
	 */
	public abstract String list() throws Exception;
	
	/**
	 * 删除
	 */
	public abstract String delete() throws Exception;

    public String getCurrentFuncId()
    {
        return currentFuncId;
    }

    public void setCurrentFuncId( String currentFuncId )
    {
        this.currentFuncId = currentFuncId;
    }
    
	
	public void resetCurrentFuncId() {
		if (StringUtils.isNotBlank(this.getCurrentFuncId())) {
			Sessions.getSession().setAttribute("currentFuncId",this.getCurrentFuncId());
		} else if (Sessions.getSession().getAttribute("currentFuncId") != null) {
			this.setCurrentFuncId(String.valueOf(Sessions.getSession().getAttribute("currentFuncId")));
		}
	}
}   
