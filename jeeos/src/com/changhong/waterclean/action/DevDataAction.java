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
import com.changhong.waterclean.model.DeviceData;
import com.changhong.waterclean.model.Fault;
import com.changhong.waterclean.model.JsonResults;
import com.changhong.base.utils.ReadProperties;
/**
 * 设备管理action
 * @author zhangyanni
 * 2015-07-03 
 */
@Controller("waterclean.action.DevDataAction")
@Scope("prototype")
public class DevDataAction extends BaseAuthAction implements ModelDriven<DeviceData>, Preparable{
	/**
	 * @author zhangyanni
	 */
private static final long serialVersionUID = 1L;
	

    private static String serveradress=ReadProperties.read("waterclean_server").trim();
	private DeviceData devicedata;
    private String currentFuncId;
	private Page page = new Page(Constants.PAGE_SIZE);
	private String sn;
	private String faultcode;
	private String faultvalue;
	public DeviceData getModel() {
		return devicedata;
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
			 if(sn==null||sn.equals(""))
			 {
				  url += "/device/data/getall?json="+URLEncoder.encode(parampage.toString());
			 }else
			 {
				 parampage.addProperty("sn", sn);
				 url += "/device/data/getbysn?json="+URLEncoder.encode(parampage.toString());
			 }
			 String result = HttpRequestUtils.doGet(url);
			 System.out.println(result);
			 JsonResults resultobj=JsonUtil.fromJson(result, JsonResults.class);
			 if(resultobj.getCode().equals("1000")){
				 List<DeviceData> list=(List<DeviceData>) resultobj.getData();
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
	
	@SuppressWarnings("unchecked")
	@Menu
     public String fault() throws Exception{
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
			 if(faultcode.startsWith("0x")){
				 parampage.addProperty("faultcode", Integer.parseInt(faultcode.substring(2),16)); 
			 }else{
				 parampage.addProperty("faultcode", Integer.parseInt(faultcode,16));
			 }
			 url += "/fault/getbyfcode?json="+URLEncoder.encode(parampage.toString());
			 String result = HttpRequestUtils.doGet(url);
			 System.out.println(result);
			 JsonResults resultobj=JsonUtil.fromJson(result, JsonResults.class);
			 if(resultobj.getCode().equals("1000")){
				 List<Fault> list=(List<Fault>) resultobj.getData();
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
		return "fault";
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
		DevDataAction.serveradress = serveradress;
	}
	public DeviceData getDevicedata() {
		return devicedata;
	}

	public void setDevicedata(DeviceData devicedata) {
		this.devicedata = devicedata;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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


	public String getFaultcode() {
		return faultcode;
	}


	public void setFaultcode(String faultcode) {
		this.faultcode = faultcode;
	}


	public String getFaultvalue() {
		return faultvalue;
	}


	public void setFaultvalue(String faultvalue) {
		this.faultvalue = faultvalue;
	}

}
