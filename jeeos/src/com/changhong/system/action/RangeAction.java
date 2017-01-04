package com.changhong.system.action;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.changhong.base.annotation.Menu;
import com.changhong.base.constants.Constants;
import com.changhong.base.dao.utils.Querys;
import com.changhong.base.entity.Page;
import com.changhong.base.utils.FastMap;
import com.changhong.base.web.Servlets;
import com.changhong.base.web.render.Renders;
import com.changhong.base.web.struts2.action.BaseCrudAction;
import com.changhong.system.entity.ChRange;
import com.changhong.system.entity.ChRole;
import com.changhong.system.service.RangeService;
import com.changhong.system.service.RoleService;
/**
 * 角色管理action
 * @author wanghao
 * 2013-08-14 
 */
@Controller("system.action.RangeAction")
@Scope("prototype")
public class RangeAction extends BaseCrudAction<ChRange>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private ChRange chRange;
	private String roleId="";
	private String chRangDesc="";
	private String chRangMemo="";
	private String chRoleName="";
	private String chDepaName="";
	private ChRole chRole;
	private List<ChRole> roleList;
	private Page page = new Page(Constants.PAGE_SIZE);
	private List<ChRange> rangeList;
//	private List<ChDepartment> deptList;
	@Autowired
	private RangeService rangeService;
	@Autowired
	private RoleService roleService;
//	@Autowired
//	private DeptServiceCh  deptService;
	
	
	
	public ChRange getModel() {
		return chRange;
	}

	@Override
	public void prepareModel() throws Exception {
		if(StringUtils.isBlank(id)){
			chRange = new ChRange();
		}else{
			chRange=rangeService.get(id);
			roleList = roleService.findRoles(new FastMap().newHashMap().set(
					Querys.PREFIX + "existedParentRole", true));
		}
	}
	
	 /**
	  *根据角色ID获取权限范围
	 * 
	  * @throws Exception
	  */
   @SuppressWarnings("unchecked")
   public String getRangeByRoleId() throws Exception {
		rangeList = rangeService.findList("system.findRanges", new FastMap()
				.newHashMap().set("roleId", roleId));
		 List<ChRange> jsonList = new ArrayList<ChRange>();
			if(rangeList!=null && rangeList.size()>0){
				for(ChRange r : rangeList){
					ChRange range = new ChRange();
					range.setId(r.getId());
					range.setChRangDesc(r.getChRangDesc());
					jsonList.add(range);
				}
			}
		Renders.renderJson(jsonList);
		 return NONE;
	 }
   
   
   @SuppressWarnings("unchecked")
   	@Menu
	public String list() throws Exception {
		try{
			setId(id);
			page = rangeService.findPage(page,"system.findRanges",new FastMap().newHashMap().set("parentId", id).set("roleId", roleId));
		}catch (Exception e) {
			addActionMessage("不能获得权限范围列表，请检查查询条件");
		}
		return LIST;
	}
	@SuppressWarnings("unchecked")
	public String add()throws Exception{
		try{
			roleList = roleService.findRoles(new FastMap().newHashMap().set(
					Querys.PREFIX + "existedParentRole", true));
			//rangeList=rangeService.findList("system.findRanges",new FastMap().newHashMap().set("parentId", id).set("roleId", roleList.get(0).getId()));
			Map<String,String> paramMap = new FastMap().newHashMap().set("chDepaName",chDepaName);
//			deptList=deptService.findList("system.findChDepts", paramMap);
			
		}catch (Exception e) {
			addActionMessage("不能获得角色列表，请检查查询条件");
		}
		return ADD;
	}
	@Override
	public String detail() throws Exception {
		return DETAIL;
	}
	
	@Override
	public String save() throws Exception {
		String checkId = Servlets.getRequest().getParameter("checkId");
		if (StringUtils.isNotBlank(checkId) && StringUtils.isNotEmpty(checkId)) {
				if(StringUtils.isBlank(id)){
							rangeList= rangeService.findList("system.findRanges",new FastMap().newHashMap().set("roleId", roleId));
							if(rangeList!=null){
								for(ChRange range:rangeList){
									rangeService.deleteById(range.getId());
								}
							}
							String[] ids = checkId.split(",");
							for (String id : ids){
									ChRange range=new ChRange();
//									chDepaName=deptService.get(id).getChDepaName();
									range.setChRangDesc(chDepaName);
									range.setChRangMemo(chRangMemo);
									range.setChRole(roleService.get(roleId));
									rangeService.save(range);
							}
					Renders.renderJson(Renders.SAVE_SUCCESS);
				}else{
					//编辑时候先查找勾选的 权限范围是否已经存在，是则删除原来的。
//					chDepaName=deptService.get(checkId).getChDepaName();
					ChRange range=new ChRange();
					range=rangeService.findOne("system.findRanges", new FastMap().newHashMap().set("chRangDesc", chDepaName).set("roleId", roleId));
					if(range!=null){
						rangeService.deleteById(range.getId());
					}
					chRange.setChRangDesc(chDepaName);
					chRange.setChRangMemo(chRangMemo);
					chRange.setChRole(roleService.get(roleId));
					rangeService.saveOrUpdate(chRange);
					Renders.renderJson(Renders.UPDATE_SUCCESS);
				} 
		}
		return NONE;
	}
	
	public String delete() throws Exception {
		String ids = Servlets.getRequest().getParameter("ids");
		rangeService.deleteByIds(ids.split(","));
		Renders.renderJson(Renders.DELETE_SUCCESS);
		return NONE;
	}
	@Override
	public String update() throws Exception {
		Map<String,String> paramMap = new FastMap().newHashMap().set("chDepaName",chDepaName);
//		deptList=deptService.findList("system.findChDepts", paramMap);
		return UPDATE;
	}
	
	public String showSearch() throws Exception {
		try{
			roleList = roleService.findRoles(new FastMap().newHashMap().set(
					Querys.PREFIX + "existedParentRole", true));
		}catch (Exception e) {
			addActionMessage("不能获得角色列表，请检查查询条件");
		}
		return "showSearch";
	}
	
	
	
	/******get()/set()方法************/
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ChRange getChRange() {
		return chRange;
	}
	public void setChRange(ChRange chRange) {
		this.chRange = chRange;
	}
	public List<ChRange> getRangeList() {
		return rangeList;
	}
	public void setRangeList(List<ChRange> rangeList) {
		this.rangeList = rangeList;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}


	public List<ChRole> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<ChRole> roleList) {
		this.roleList = roleList;
	}

	public ChRole getChRole() {
		return chRole;
	}

	public void setChRole(ChRole chRole) {
		this.chRole = chRole;
	}

	public String getChRangDesc() {
		return chRangDesc;
	}

	public void setChRangDesc(String chRangDesc) {
		this.chRangDesc = chRangDesc;
	}

	public String getChRangMemo() {
		return chRangMemo;
	}

	public void setChRangMemo(String chRangMemo) {
		this.chRangMemo = chRangMemo;
	}

	public String getChRoleName() {
		return chRoleName;
	}

	public void setChRoleName(String chRoleName) {
		this.chRoleName = chRoleName;
	}

//	public List<ChDepartment> getDeptList() {
//		return deptList;
//	}
//
//	public void setDeptList(List<ChDepartment> deptList) {
//		this.deptList = deptList;
//	}

	public String getChDepaName() {
		return chDepaName;
	}

	public void setChDepaName(String chDepaName) {
		this.chDepaName = chDepaName;
	}

}
