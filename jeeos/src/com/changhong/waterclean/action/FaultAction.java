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
import com.changhong.base.web.render.Renders;
import com.changhong.base.web.struts2.action.BaseAuthAction;
import com.google.gson.JsonObject;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.changhong.waterclean.model.JsonResults;
import com.changhong.waterclean.model.Fault;
import com.changhong.base.utils.ReadProperties;
/**
 * 设备管理action
 * @author zhangyanni
 * 2015-07-03 
 */
@Controller("waterclean.action.FaultAction")
@Scope("prototype")
public class FaultAction extends BaseAuthAction implements ModelDriven<Fault>, Preparable{
	/**
	 * @author zhangyanni
	 */
private static final long serialVersionUID = 1L;
	

    private static String serveradress=ReadProperties.read("waterclean_server").trim();
	private Fault fault;
    private String currentFuncId;
	private Page page = new Page(Constants.PAGE_SIZE);
	private String faultcode;
	private String faultvalue;
	private String faultdescribe;
	private String selectfault;
	public Fault getModel() {
		return fault;
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
			 if(selectfault==null||selectfault.equals(""))
			 {
				  url += "/fault/getall?json="+URLEncoder.encode(parampage.toString());
			 }else
			 {
				 if(selectfault.startsWith("0x")){
					 parampage.addProperty("faultcode", Integer.parseInt(selectfault.substring(2),16)); 
				 }else{
					 parampage.addProperty("faultcode", Integer.parseInt(selectfault,16));
				 }
				 url += "/fault/getbyfcode?json="+URLEncoder.encode(parampage.toString());
			 }
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
		return "list";
	}
	@SuppressWarnings("unchecked")
	public String add(){
		return "add";
	}
	@SuppressWarnings("unchecked")
	public String save(){
		try{
			 currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
			 JsonObject param = new JsonObject();
			 param.addProperty("faultcode", Integer.parseInt(faultcode,16));
			 param.addProperty("faultvalue", Integer.parseInt(faultvalue,16));
			 param.addProperty("faultdescribe", faultdescribe);
			 //String url=serveradress+"/agent/newagent?json="+URLEncoder.encode(param.toString());
			 String url=serveradress+"/fault/newfault";
			 String result = HttpRequestUtils.doPost(url, param.toString());
			 System.out.println(result);
			 Renders.renderJson(Renders.SAVE_SUCCESS);
			// JsonResults resultobj=JsonUtil.fromJson(result, JsonResults.class);
			 
		}catch (Exception e) {
		    addActionMessage("保存失败!");
		}
		return "none";
	}
    
	public String delete() throws Exception {
		currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
		String ids = Servlets.getRequest().getParameter("ids");
		JsonObject param = new JsonObject();
		param.addProperty("list", ids);
		String url=serveradress+"/fault/deletefault?json="+URLEncoder.encode(param.toString());
		String result = HttpRequestUtils.doGet(url);
		System.out.println(result);
		Renders.renderJson(Renders.DELETE_SUCCESS);
		return "none";
	}
    public String isFaultExist(){
    	try{
    		JsonObject param= new JsonObject();
    		param.addProperty("faultcode",Integer.parseInt(faultcode,16));
    		param.addProperty("faultvalue",Integer.parseInt(faultvalue,16));
    		String url=serveradress+"/fault/isfaultexist?json="+URLEncoder.encode(param.toString(),"UTF-8");
    		String result=HttpRequestUtils.doGet(url);
    		System.out.println(result);
    		 JsonResults resultobj=JsonUtil.fromJson(result, JsonResults.class);
    		Renders.renderJson(resultobj.getJson());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "none";
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
		FaultAction.serveradress = serveradress;
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

	
	public Fault getFault() {
		return fault;
	}

	public void setFault(Fault fault) {
		this.fault = fault;
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

	public String getFaultdescribe() {
		return faultdescribe;
	}

	public void setFaultdescribe(String faultdescribe) {
		this.faultdescribe = faultdescribe;
	}

	public String getSelectfault() {
		return selectfault;
	}

	public void setSelectfault(String selectfault) {
		this.selectfault = selectfault;
	}

}
