package com.changhong.foodmanage.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;
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
import com.changhong.foodmanage.model.JsonResults;
import com.changhong.foodmanage.model.Goods;
import com.changhong.base.utils.ReadProperties;

//import com.changhong.foodmanage.service.GoodsService;
/**
 * 设备管理action
 * 
 * @author 2015-07-03
 */
@Controller("foodmanage.action.GoodsManageAction")
@Scope("prototype")
public class GoodsManageAction extends BaseAuthAction implements
		ModelDriven<Goods>, Preparable {
	/**
	 * @author
	 */
	private static final long serialVersionUID = 1L;

	private static String serveradress = ReadProperties.read("foodmagage_server").trim();
	private Goods goods;
	private String currentFuncId;
	private Page page = new Page(Constants.PAGE_SIZE);
	private List<String> snlist;
	private String queryitem;
	private String itemno;
	private String itemname;
	private String itemsize;
	private String unitno;
	private String productarea;

	public Goods getModel() {
		return goods;
	}

	@SuppressWarnings("unchecked")
	@Menu
	public String list() throws Exception {
		try {
			currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
			int currentPage = page.getCurrentPage();
			if (currentPage == 0) {
				page.setCurrentPage(1);
				currentPage = 1;
			}
			JsonObject param = new JsonObject();
			param.addProperty("currentPage", page.getCurrentPage());
			param.addProperty("pageSize", page.getPageSize());
			JsonObject parampage = new JsonObject();
			parampage.add("page", param);
			String url = serveradress;

			if (queryitem == null || queryitem.equals("")) {
				//System.out.println(queryitem);
				url += "/products/getall?json="
						+ URLEncoder.encode(parampage.toString());
			} else {
				parampage.addProperty("queryitem", queryitem);
				//System.out.println(parampage.toString());
				url += "/products/getbynoorname?json="
						+ URLEncoder.encode(parampage.toString(),"UTF-8");
			}
			//System.out.println(queryitem);
			String result = HttpRequestUtils.doGet(url);
			System.out.println(result);
			//System.out.println(url);
			JsonResults resultobj = JsonUtil
					.fromJson(result, JsonResults.class);
			if (resultobj.getCode().equals("1000")) {
				List<Goods> list = (List<Goods>) resultobj.getData();
				page = new Page(list, page.getCurrentPage(),
						resultobj.getTotalRecords(), page.getPageSize(),
						page.getOrderattr(), page.getOrdertype());
			} else {
				page.setCurrentPage(1);
				page.setTotalRecords(0);
			}

			// page=new
			// Page(query.list(),currentPage,totalRecords,pageSize,page.getOrderattr(),page.getOrdertype());

		} catch (Exception e) {
			addActionMessage("查询异常");
		}
		return "list";
	}

	public String update() {
		try {
			itemno=URLDecoder.decode(itemno,"UTF-8");
			itemname=URLDecoder.decode(itemname,"UTF-8");
			itemsize=URLDecoder.decode(itemsize,"UTF-8");
			unitno=URLDecoder.decode(unitno,"UTF-8");
			productarea=URLDecoder.decode(productarea,"UTF-8");
			return "update";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "update";
		}
		
	}
	@SuppressWarnings("unchecked")
	public String add(){
		try{
			 currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
			 String url=serveradress+"/products/addproduct";
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

	/*
	 * @SuppressWarnings("unchecked") public String add(){ return "add"; }
	 */
	@SuppressWarnings("unchecked")
	public String save() {
		try {
			currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
			JsonObject param = new JsonObject();

			param.addProperty("itemno", itemno);
			param.addProperty("itemname", itemname);
			param.addProperty("itemsize", itemsize);
			param.addProperty("unitno", unitno);
			param.addProperty("productarea", productarea);
			
			String url = serveradress + "/products/updatebyid";
			//System.out.println(url);
			String result = HttpRequestUtils.doPost(url, param.toString());
			//System.out.println(result);
			
		    JsonResults resultobj=JsonUtil.fromJson(result,JsonResults.class);
		    if (resultobj.getCode().equals("1000")) {
				List<Goods> list = (List<Goods>) resultobj.getData();
				page = new Page(list, page.getCurrentPage(),
						resultobj.getTotalRecords(), page.getPageSize(),
						page.getOrderattr(), page.getOrdertype());
		  Renders.renderJson(Renders.UPDATE_SUCCESS);
			} else {
				Renders.renderJson(resultobj.getMsg());
			}
//			Renders.renderJson(Renders.SAVE_SUCCESS);
		return "none";
		} catch (Exception e) {
			addActionMessage("保存失败!");
		}
		return "none";
	}
	@SuppressWarnings("unchecked")
	public String saveproduct(){
		try{
			 currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
			 JsonObject param = new JsonObject();
				param.addProperty("itemno", itemno);
				param.addProperty("itemname", itemname);
				param.addProperty("itemsize", itemsize);
				param.addProperty("unitno", unitno);
				param.addProperty("productarea", productarea);
			 //String url=serveradress+"/agent/newagent?json="+URLEncoder.encode(param.toString());
			 String url=serveradress+"/products/addproduct";
			 String result = HttpRequestUtils.doPost(url, param.toString());
			 System.out.println(url);
			    JsonResults resultobj=JsonUtil.fromJson(result,JsonResults.class);
			    if (resultobj.getCode().equals("1000")) {
					List<Goods> list = (List<Goods>) resultobj.getData();
					page = new Page(list, page.getCurrentPage(),
							resultobj.getTotalRecords(), page.getPageSize(),
							page.getOrderattr(), page.getOrdertype());
			  Renders.renderJson(Renders.UPDATE_SUCCESS);
				} else {
					Renders.renderJson(resultobj.getMsg());
				}
//				Renders.renderJson(Renders.SAVE_SUCCESS);
			return "none";
		}catch (Exception e) {
		    addActionMessage("保存失败!");
		}
		return "none";
	}

	public String delete() throws Exception {
	    //currentFuncId = Servlets.getRequest().getParameter("currentFuncId");
		String id = Servlets.getRequest().getParameter("id");
		JsonObject param = new JsonObject();
		param.addProperty("itemno", id);
		String url=serveradress+"/products/deleteproduct";
		String result = HttpRequestUtils.doPost(url, param.toString());
		System.out.println(result);
		Renders.renderJson(Renders.DELETE_SUCCESS);
		return "none";
	}

	// ------------------------------getter/setter方法----------------------------------

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
		GoodsManageAction.serveradress = serveradress;
	}

	public void resetCurrentFuncId() {
		if (StringUtils.isNotBlank(this.getCurrentFuncId())) {
			Sessions.getSession().setAttribute("currentFuncId",
					this.getCurrentFuncId());
		} else if (Sessions.getSession().getAttribute("currentFuncId") != null) {
			this.setCurrentFuncId(String.valueOf(Sessions.getSession()
					.getAttribute("currentFuncId")));
		}
	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub

	}

	public Goods getFault() {
		return goods;
	}

	public void setFault(Goods fault) {
		this.goods = fault;
	}

	public String getQueryitem() {
		return queryitem;
	}

	public void setQueryitem(String queryitem) {
		this.queryitem = queryitem;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemsize() {
		return itemsize;
	}

	public void setItemsize(String itemsize) {
		this.itemsize = itemsize;
	}

	public String getUnitno() {
		return unitno;
	}

	public void setUnitno(String unitno) {
		this.unitno = unitno;
	}

	public String getProductarea() {
		return productarea;
	}

	public void setProductarea(String productarea) {
		this.productarea = productarea;
	}

}
