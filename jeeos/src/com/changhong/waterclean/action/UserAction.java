package com.changhong.waterclean.action;

import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.changhong.base.annotation.Menu;
import com.changhong.base.constants.Constants;
import com.changhong.base.entity.Page;
import com.changhong.base.json.JsonUtil;
import com.changhong.base.utils.http.HttpRequestUtils;
import com.changhong.base.web.Servlets;
import com.changhong.base.web.Sessions;
import com.changhong.base.web.struts2.action.BaseAuthAction;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.changhong.waterclean.model.Device;
import com.changhong.waterclean.model.JsonResults;
import com.changhong.waterclean.model.Userdata;
import com.changhong.base.utils.ReadProperties;
/**
 * 设备管理action
 * @author zhangyanni
 * 2015-07-03 
 */
@Controller("waterclean.action.UserAction")
@Scope("prototype")
public class UserAction extends BaseAuthAction implements ModelDriven<Userdata>, Preparable{
	/**
	 * @author zhangyanni
	 */
private static final long serialVersionUID = 1L;
	

    private static String serveradress=ReadProperties.read("waterclean_server").trim();
	private Userdata user;
    private String currentFuncId;
	private Page page = new Page(Constants.PAGE_SIZE);
	private String username;
	public Userdata getModel() {
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Menu
     public String list() throws Exception{
		 try{
			 currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
			 int currentPage = page.getCurrentPage();
				if(currentPage==0){page.setCurrentPage(1);currentPage=1;}
			 JsonObject param = new JsonObject();
			 param.addProperty("currentPage", page.getCurrentPage());
			 param.addProperty("pageSize", page.getPageSize());
			 JsonObject parampage = new JsonObject();
			 parampage.add("page", param);
			 String url=serveradress;
			 if(username==null||username.equals(""))
			 {
				  url += "/user/getall?json="+URLEncoder.encode(parampage.toString());
			 }else
			 {
				 parampage.addProperty("userName", username);
				 url += "/user/getbyusername?json="+URLEncoder.encode(parampage.toString());
			 }
			 String result = HttpRequestUtils.doGet(url);
			 System.out.println(result);
			 JsonResults resultobj=JsonUtil.fromJson(result, JsonResults.class);
			 if(resultobj.getCode().equals("1000")){
				 List<Userdata> list=(List<Userdata>) resultobj.getData();
				 page=new Page(list,page.getCurrentPage(),resultobj.getPage().getTotalRecords(),page.getPageSize(),page.getOrderattr(),page.getOrdertype());
			 }
			 else{
				 page.setCurrentPage(1);
				 page.setTotalRecords(0);
			 }
			// page=new Page(query.list(),currentPage,totalRecords,pageSize,page.getOrderattr(),page.getOrdertype());
			 
		}catch (Exception e) {
		    addActionMessage("查询异常");
		}
		return "list";
	}
	  
    
	//------------------------------getter/setter方法----------------------------------

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	public String getCurrentFuncId() {
		return currentFuncId;
	}

	public void setCurrentFuncId(String currentFuncId) {
		this.currentFuncId = currentFuncId;
	}

	public static String getServeradress() {
		return serveradress;
	}

	public static void setServeradress(String serveradress) {
		UserAction.serveradress = serveradress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void resetCurrentFuncId() {
		if (StringUtils.isNotBlank(this.getCurrentFuncId())) {
			Sessions.getSession().setAttribute("currentFuncId",this.getCurrentFuncId());
		} else if (Sessions.getSession().getAttribute("currentFuncId") != null) {
			this.setCurrentFuncId(String.valueOf(Sessions.getSession().getAttribute("currentFuncId")));
		}
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public Userdata getUser() {
		return user;
	}

	public void setUser(Userdata user) {
		this.user = user;
	}
}
