package com.changhong.foodmanage.action;

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
import com.changhong.foodmanage.model.JsonResults;
import com.changhong.foodmanage.model.Foodno;
import com.changhong.system.service.RoleService;
import com.changhong.base.utils.ReadProperties;

/**
 * 设备管理action
 * 
 * @author 2015-08-04
 */
@Controller("foodmanage.action.FoodnoAction")
@Scope("prototype")
public class FoodnoAction extends BaseAuthAction implements
		ModelDriven<Foodno>, Preparable {
	/**
	 * @author zhangy
	 */
	private static final long serialVersionUID = 1L;
    private String id;
	private String itemno;
	private String itemname;
	private String itemsize;
	private String unitno;
	private String productarea;

	private static String serveradress = ReadProperties.read("foodmagage_server").trim();
	private Foodno foodno;
	private String currentFuncId;
	private Page page = new Page(Constants.PAGE_SIZE);
	private String queryitem;
	private RoleService roleService;

	public Foodno getModel() {
		return foodno;
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

			if (queryitem == null || queryitem.equals(""))

			{

				url += "/unrecognizedno/getall?json="
						+ URLEncoder.encode(parampage.toString());
			} else {
				parampage.addProperty("queryitem", queryitem);

				url += "/unrecognizedno/getbyno?json="
						+ URLEncoder.encode(parampage.toString());
			}
			//System.out.println(url);
			String result = HttpRequestUtils.doGet(url);
			//System.out.println(result);
			JsonResults resultobj = JsonUtil
					.fromJson(result, JsonResults.class);
			if (resultobj.getCode().equals("1000")) {
				List<Foodno> list = (List<Foodno>) resultobj.getData();
				page = new Page(list, page.getCurrentPage(),
						resultobj.getTotalRecords(), page.getPageSize(),
						page.getOrderattr(), page.getOrdertype());

				// page=new
				// Page(list,page.getCurrentPage(),resultobj.getPage().getTotalRecords(),page.getPageSize(),page.getOrderattr(),page.getOrdertype());
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

//	public String delete() throws Exception {
//		String ids = Servlets.getRequest().getParameter("ids");
//		roleService.deleteByIds(ids.split(","));
//		Renders.renderJson(Renders.DELETE_SUCCESS);
//		return NONE;
//	}
	
	public String delete() throws Exception {
		String id = Servlets.getRequest().getParameter("id");
		JsonObject param = new JsonObject();
		param.addProperty("itemno", id);
		String url = serveradress + "/unrecognizedno/deleteitemno";
		System.out.println(url);
		String result = HttpRequestUtils.doPost(url, param.toString());
		System.out.println(result);
		Renders.renderJson(Renders.DELETE_SUCCESS);
		return NONE;
	}

	public String update() {
		return "update";
	}

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
			// String
			// url=serveradress+"/agent/newagent?json="+URLEncoder.encode(param.toString());
			String url = serveradress + "/unrecognizedno/edititemno";
			System.out.println(url);
			String result = HttpRequestUtils.doPost(url, param.toString());
			System.out.println(result);
			
			Renders.renderJson(Renders.SAVE_SUCCESS);
			// JsonResults resultobj=JsonUtil.fromJson(result,
			// JsonResults.class);

		} catch (Exception e) {
			addActionMessage("保存失败!");
		}
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
		FoodnoAction.serveradress = serveradress;
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

	public Foodno getFoodno() {
		return foodno;
	}

	public void setFoodno(Foodno foodno) {
		this.foodno = foodno;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
