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
import com.changhong.waterclean.model.Agent;
import com.changhong.base.utils.ReadProperties;
/**
 * 设备管理action
 * @author zhangyanni
 * 2015-07-03 
 */
@Controller("waterclean.action.AgentAction")
@Scope("prototype")
public class AgentAction extends BaseAuthAction implements ModelDriven<Agent>, Preparable{
	/**
	 * @author zhangyanni
	 */
private static final long serialVersionUID = 1L;
	

    private static String serveradress=ReadProperties.read("waterclean_server").trim();
	private Agent agent;
    private String currentFuncId;
	private Page page = new Page(Constants.PAGE_SIZE);
	private String queryterm;
	private List<String> snlist;
	private String agentname;
	private String agentphone;
	private String agentadress;
	private String agentclass;
	private String sn;
	public Agent getModel() {
		return agent;
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
			 if(queryterm==null||queryterm.equals(""))
			 {
				  url += "/agent/getall?json="+URLEncoder.encode(parampage.toString());
			 }else
			 {
				 parampage.addProperty("queryterm", queryterm);
				 url += "/agent/getbyfcode?json="+URLEncoder.encode(parampage.toString(),"UTF-8");
			 }
			 String result = HttpRequestUtils.doGet(url);
			 System.out.println(result);
			 JsonResults resultobj=JsonUtil.fromJson(result, JsonResults.class);
			 if(resultobj.getCode().equals("1000")){
				 List<Agent> list=(List<Agent>) resultobj.getData();
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
		try{
			 currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
			 String url=serveradress+"/agent/getnoangentsn";
			 String result = HttpRequestUtils.doGet(url);
			 System.out.println(result);
			 JsonResults resultobj=JsonUtil.fromJson(result, JsonResults.class);
			 if(resultobj.getCode().equals("1000")){
				 snlist=(List<String>)resultobj.getData();
			 }
			 else{
				 addActionMessage("获取未代理设备失败!");
			 }
		}catch (Exception e) {
		    addActionMessage("获取未代理设备失败!");
		}
		return "add";
	}
	@SuppressWarnings("unchecked")
	public String save(){
		try{
			 currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
			 JsonObject param = new JsonObject();
			 param.addProperty("agentname", agentname);
			 param.addProperty("phone", agentphone);
			 param.addProperty("adress", agentadress);
			 param.addProperty("sn", sn);
			 param.addProperty("agentclass", agentclass);
			 //String url=serveradress+"/agent/newagent?json="+URLEncoder.encode(param.toString());
			 String url=serveradress+"/agent/newagent";
			 String result = HttpRequestUtils.doPost(url, param.toString());
			 System.out.println(result);
			 Renders.renderJson(Renders.SAVE_SUCCESS);
			// JsonResults resultobj=JsonUtil.fromJson(result, JsonResults.class);
			 
		}catch (Exception e) {
		    addActionMessage("保存失败!");
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
		AgentAction.serveradress = serveradress;
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

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getQueryterm() {
		return queryterm;
	}

	public void setQueryterm(String queryterm) {
		this.queryterm = queryterm;
	}

	public List<String> getSnlist() {
		return snlist;
	}

	public void setSnlist(List<String> snlist) {
		this.snlist = snlist;
	}

	public String getAgentname() {
		return agentname;
	}

	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getAgentphone() {
		return agentphone;
	}

	public void setAgentphone(String agentphone) {
		this.agentphone = agentphone;
	}

	public String getAgentadress() {
		return agentadress;
	}

	public void setAgentadress(String agentadress) {
		this.agentadress = agentadress;
	}

	public String getAgentclass() {
		return agentclass;
	}

	public void setAgentclass(String agentclass) {
		this.agentclass = agentclass;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

}
