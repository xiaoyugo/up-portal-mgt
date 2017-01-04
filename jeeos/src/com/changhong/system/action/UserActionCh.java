package com.changhong.system.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.changhong.base.annotation.Menu;
import com.changhong.base.constants.Constants;
import com.changhong.base.dao.utils.Querys;
import com.changhong.base.entity.Page;
import com.changhong.base.utils.DateUtils;
import com.changhong.base.utils.FastMap;
import com.changhong.base.utils.string.EncodeUtils;
import com.changhong.base.web.Servlets;
import com.changhong.base.web.Sessions;
import com.changhong.base.web.render.EasyUiResult;
import com.changhong.base.web.render.Renders;
import com.changhong.base.web.struts2.action.BaseCrudAction;
import com.changhong.system.entity.ChButton;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChRange;
import com.changhong.system.entity.ChRole;
import com.changhong.system.entity.ChRolebutton;
import com.changhong.system.entity.ChUser;
import com.changhong.system.entity.ChUserfunc;
import com.changhong.system.service.RoleButtonService;
import com.changhong.system.service.RangeService;
import com.changhong.system.service.RoleService;
import com.changhong.system.service.UserFuncService;
import com.changhong.system.service.UserServiceCh;

/**
 * 用户管理
 * 
 * @author wanghao
 * 2013-07-08
 */
@Controller("system.action.UserActionCh")
@Scope("prototype")
public class UserActionCh extends BaseCrudAction<ChUser>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ChUser user;
	//用户id
	private String id;
	private String userName;
	private String deptId;
	private String deptName;
	private String roleId;
	private String roleIds="";
	private String funcIds="";
	private String roleNames="";
	private String enRoleNames="";
	private String pictureOnly="";
	private String teacherId="";
	private String userState="";
	private String loginName="";
	private String parentId="";
	private String permissionIds="";
	private String funcId;
	
	//修改密码
	private String oldPassword;
	private String newPassword;
	
	private List<ChRole> roleList;
	private List<ChRange> rangeList;
	private List<ChRolebutton> chRolebuttons;
	private List<ChFunction> levelOneMenuList;
	
	@Autowired
	private UserServiceCh userService;
//	@Autowired
//	private TeacherService teacherService;
	@Autowired
	private RangeService rangeService;
	@Autowired
	private RoleButtonService buttonService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserFuncService userFuncService;
	
	private Page page = new Page(Constants.PAGE_SIZE);
	
	public ChUser getModel() {
		return user;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void prepareModel() throws Exception {
		if(StringUtils.isBlank(id)){
			user = new ChUser();
			user.setChUserMaketime(DateUtils.getCurrentDate());
			//user.setCreateUser(this.UserSession.getSysUser());
		}else{
			user = userService.get(id);
//			teacherList = teacherService.findTeachersall();
			roleList = roleService.findRoles(new FastMap().newHashMap().set(
					Querys.PREFIX + "existedParentRole", true));
			roleIds = user.getChUserRoleids();
			funcIds = user.getChUserFuncs();
		}
	}
	
	public String index() {
		return "index";
	}
	
	public String info(){
		return "moblieInfo";
	}
	
	/**
	 * 用户列表 2013-7-16:用户和用户拥有角色名称查询
	 * @author wanghao
	 * @return
	 * @throws Exception 
	 */
	 @SuppressWarnings("unchecked")
	 @Menu
     public String list() throws Exception{
		 try{
			setId(id);
			userState = URLDecoder.decode(userState, "utf-8"); //Very confused,when add this line,before decode,it already take effect
			@SuppressWarnings("rawtypes")
			Map<String, String> paramMap = new FastMap().newHashMap()
					.set("parentId", id).set("userName", userName)
					.set("loginName", loginName).set("userState", userState);
			/* 2014-08-08 wanghao 新页面使用jquery table页面自动分页 不需要分页page参数
			 * 如果查询数量比较大还是需要分页
			 */
			page = roleService.findPage(page,"system.findChUsers",paramMap);
			List<ChUser> userList = page.getList();
			for (ChUser usr : userList) {
				List<ChRole> chRoles = new ArrayList<ChRole>(0);
				String roleIds = usr.getChUserRoleids();
				if (roleIds != null) {
					String[] roleID = roleIds.split(", ");
					for (String roleid : roleID) {
						ChRole cR = roleService.get(roleid);
						chRoles.add(cR);
					}
					usr.setChRoles(chRoles);
				}
			}
			this.resetCurrentFuncId();
		}catch (Exception e) {
				addActionMessage("查询异常");
		}
		return LIST;
	}
	
	


	 
	 
	 /**
	  * 个人设置中 个人基本信息
	 * 
	  * @return
	 * @throws Exception 
	  * @throws Exception
	  */
	 @Menu
	 public String toUserBasicInfo() throws Exception{
		 user =  userService.get(Sessions.getChUser().getId());
		 return "userBasicInfo";
	 }

	 /**
	  * 在线用户中 查看用户信息
	 * 
	  * @return
	  * @throws Exception
	  */
	 public String showUserInfo() throws Exception{
		 user =  userService.get(getId());
		 return "showUserInfo";
	 }

	/**
	 * 更新用户个人信息
	 * 
	 * @throws Exception
	 */
	public void prepareUpdateUserBasicInfo() throws Exception {
		 user = userService.get(id);
	}

	public String toUpdatePassword() throws Exception {
		return "updatePassword";
	}
	
	public String updatePassword() throws Exception {
		user = Sessions.getChUser();
        if(!EncodeUtils.isValidMd5(user.getChUserPassword(),oldPassword)){
        	Renders.renderJson(new EasyUiResult("2","旧密码不正确！"));
        }else{
        	user.setChUserPassword(EncodeUtils.encodeMd5(newPassword));
        	userService.update(user);
        	Renders.renderJson(new EasyUiResult("密码修改成功，重新登录生效!"));
        }
		return NONE;
	}

	
	@Override
	public String detail() throws Exception {
		return DETAIL;
	}

	/**
	 * 进入新增用户页
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String add() throws Exception{
		roleList = roleService.findRoles(new FastMap().newHashMap().set(
				Querys.PREFIX + "existedParentRole", true));
		return ADD;
	}

	/**
	 * 查看用户详情
	 */
	public String update(){
		return UPDATE;
	}
	
	/*
	 *密码初始化 跳转页面
	 */
	public String setPwdRule(){
		return "setInitPwd";
	}
	
	/*
	 * 密码初始化实现 0：采用固定方式生成密码123456 1：采用身份证后8位 2：采用随机生成6位密码 3：以登录账户为密码
	 */
	public String initPassword() throws Exception {
		String ruleId=Servlets.getRequest().getParameter("setInitPwd");
		user = userService.get(id);
		if(ruleId.equals("0")){
			user.setChUserPassword(EncodeUtils.encodeMd5("123456"));	
		}else if(ruleId.equals("1")){
//			ChTeacher chTeacher=teacherService.get(user.getChTeacher().getId());
//			String teacherID=chTeacher.getChTeacPersonid();
//			user.setChUserPassword(EncodeUtils.encodeMd5(teacherID.substring(teacherID.length()-8,teacherID.length())));	
		}else if(ruleId.equals("2")){
			user.setChUserPassword(EncodeUtils.encodeMd5(genRandomNum(6)));
		}else{
			user.setChUserPassword(EncodeUtils.encodeMd5(user
					.getChUserLogname()));
		}
		userService.update(user);
		Renders.renderJson(new EasyUiResult("密码重置成功"));
		return NONE;
	}
	
	/**
	  * 生成随即密码
	 * 
	 * @param pwd_len
	 *            生成的密码的总长度
	  * @return  密码的字符串
	  */
	 public  String genRandomNum(int pwd_len){
	  //35是因为数组是从0开始的，26个字母+10个数字
	  final int  maxNum = 36;
	  int i;  //生成的随机数
	  int count = 0; //生成的密码的长度
	  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
	    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
	    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	  
	  StringBuffer pwd = new StringBuffer("");
	  Random r = new Random();
	  while(count < pwd_len){
	   //生成随机数，取绝对值，防止生成负数，
	   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
	   if (i >= 0 && i < str.length) {
		    pwd.append(str[i]);
		    count ++;
	   }
	  }
	  System.out.println("随机生成密码："+pwd.toString());
	  return pwd.toString();
	 }
	
	/**
	 * 改变用户状态
	 * 
	 * @throws Exception
	 */
    public String changeUserState() throws Exception {
		user = userService.get(id);
		userService.changeUserState(user);
		Renders.renderJson("");
		return NONE;
	}
	
	/**
	 * 新增用户
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String save() throws Exception{
		
		if(StringUtils.isBlank(id)){
			user.setChUserPassword(EncodeUtils.encodeMd5(user
					.getChUserPassword()));
			user = userService.saveOrUpdate(user, funcIds, roleIds);
			System.out.println(user.getId());
//			Renders.renderJson(JsonUtil.toJson(user));
			Renders.renderJsonWithoutDeepInto(user);
		}else{
			String userPWD = Servlets.getRequest().getParameter(
					"chUserPassword2");
			if(!userPWD.equals("")){//修改了密码
				user.setChUserPassword(EncodeUtils.encodeMd5(userPWD));
			}
			System.out.println(funcIds+" "+roleIds);
			userService.saveOrUpdate(user, funcIds, roleIds);
			Renders.renderJsonWithoutDeepInto(user);
		}
		return NONE;
	}
	
	/**
	 * 用户自定义
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String definePermission() throws Exception{
		roleList = roleService.findRoles(new FastMap().newHashMap().set(
				Querys.PREFIX + "existedParentRole", true));
		if(!CollectionUtils.isEmpty(roleList)){
			List<ChUser> tempUserList = null;
			for(ChRole role:roleList){
				tempUserList = userService.findUserByRoleId(role.getId());
				role.setUserList(tempUserList);
			}
		}
		levelOneMenuList = userService.findLevelOneMenu();
		return "definePermission";
	}
	
	public String getLevelTwoMenuByParentId() throws Exception{
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, String> paramMap = new FastMap().newHashMap().set("parentId", parentId);
		@SuppressWarnings("unchecked")
		List<ChFunction> menuList = roleService.findList("system.findFuncs",paramMap);
		Renders.renderJsonWithoutDeepInto(menuList);
		return NONE;
	}
	
	public String getFuncAndBtnByRoleId() throws Exception{
		ChRole chRole = roleService.get(roleId);
		List<ChFunction> funcList = new ArrayList<ChFunction>(chRole.getChFuncs());
		
		List<ChFunction> levelOneFuncList = new ArrayList<ChFunction>();
	    List<ChFunction> leafFuncList = new ArrayList<ChFunction>();
	    Map<String,List<ChFunction>> leafFuncMap = new HashMap<String,List<ChFunction>>();
	    Map<String,List<ChButton>> funcButtonMap = new HashMap<String,List<ChButton>>();
		if(CollectionUtils.isNotEmpty(funcList)){
			for(ChFunction func:funcList){
				//Find level one functions,here we think root function's id is a fixed value /1/ 
				if(StringUtils.isNotBlank(func.getParentFuncId()) && "1".equals(func.getParentFuncId())){
					levelOneFuncList.add(func);
					leafFuncList = getLeafFunc(funcList,leafFuncList,func.getId());
					List<ChButton> btnList = new ArrayList<ChButton>();
					for(ChFunction leafFunc:leafFuncList){
						btnList = buttonService.findButtonByFunc(leafFunc.getId());
						funcButtonMap.put(leafFunc.getId(), btnList);
					}
					leafFuncMap.put(func.getId(), leafFuncList);
					leafFuncList = new ArrayList<ChFunction>();
				}
			}
		}

		Map<String,Object> result = new HashMap<String,Object>();
		result.put("levelOneFuncList", levelOneFuncList);
		result.put("leafFuncMap", leafFuncMap);
		result.put("funcButtonMap", funcButtonMap);
		Renders.renderJsonWithoutDeepInto(result);
		return NONE;
	}
	
	public String getRemovedFuncFromRoleFunc() throws Exception{
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Map<String, String> paramMap = new FastMap().newHashMap().set(
				"roleId", roleId).set("userId", id);
		List<ChUserfunc> userFuncList = userService.findUserFunc(paramMap);
		Renders.renderJsonWithoutDeepInto(userFuncList);
		return NONE;
	}
	
	private List<ChFunction> getLeafFunc(List<ChFunction> funcList,List<ChFunction> leafFuncList,String funcId){
		for(ChFunction func:funcList){
			if(func.getChFunc()!=null && funcId.equals(func.getChFunc().getId())){
				if(StringUtils.isNotEmpty(func.getChFuncPath())){
					leafFuncList.add(func);
				}else{
					getLeafFunc(funcList,leafFuncList,func.getId());
				}
			}
		}
		return leafFuncList;
	}
	
	private boolean canBeDelete(ChFunction func){
		boolean result = false;
		if(func != null && funcId!=null){
			if(funcId.equals(func.getChFunc().getId())){
				result = true;
			}
			if(func.getChFunc().getChFunc() != null && funcId.equals(func.getChFunc().getChFunc())){
				result = true;
			}
		}
		return result;
	}
	
	
	public String saveUserPermission() throws Exception{
		//Find old data and remove all
		Map<String, String> paramMap = new FastMap().newHashMap().set(
				"roleId", roleId).set("userId", id);
		List<ChUserfunc> userFuncList = userService.findUserFunc(paramMap);
		if(CollectionUtils.isNotEmpty(userFuncList)){
			ChFunction func;
			for(ChUserfunc userfunc:userFuncList){
				func = userfunc.getChFunc();
				if(canBeDelete(func)){
					userFuncService.deleteById(userfunc.getId());
				}
			}
		}
		if(StringUtils.isNotBlank(permissionIds)){
			String[] permissionIdArray = permissionIds.split(",");
			for(String funcId:permissionIdArray){
				if (StringUtils.isNotBlank(funcId) && funcId.indexOf(",") == -1) {
					ChUserfunc userfunc = new ChUserfunc();
					ChFunction chFunction = new ChFunction();
					chFunction.setId(funcId);
					ChRole chRole = new ChRole();
					chRole.setId(roleId);
					ChUser chUser = new ChUser();
					chUser.setId(id);
					userfunc.setChFunc(chFunction);
					userfunc.setChUser(chUser);
					userfunc.setChRole(chRole);
					userFuncService.save(userfunc);
				}
			}
		}
		Renders.renderJson("");
		return NONE;
	}
	
	public String resetPassword() throws Exception{
		user = userService.get(id);
		user.setChUserPassword(EncodeUtils.encodeMd5("123456"));
		userService.update(user);
		Renders.renderJson(new EasyUiResult("密码重置成功"));
		return NONE;
	}
	
	public String delete() throws Exception {
		String ids = Servlets.getRequest().getParameter("ids");
		userService.deleteByIds(ids.split(","));
		Renders.renderJson(Renders.DELETE_SUCCESS);
		return NONE;
	}

	/**
	 * 选择用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String chooseUser() throws Exception {
//		deptList = deptService.findListByDeptLevel();
		roleList = roleService.findRoles(new FastMap().newHashMap().set(
				Querys.PREFIX + "existedParentRole", true));
		page = userService.findUsers(page,roleIds, userName,null);
		return "chooseUser";
	}
	

	 /**
	  * 有角色ID获取权限范围
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

	 /**
	  * 根据用户登录名查找用户
	 * 
	  * @throws Exception
	  */
   @SuppressWarnings("unchecked")
   public String findUserByLogName() throws Exception {
	   Map<String, String> paramMap = new FastMap().newHashMap().set(
				"loginName", userName);
		List<ChUser> userList = userService.findList("system.findChUsers",paramMap);
		//List<SysUser> userList = userService.findList("system.findUsers",	paramMap);
		//List<SysUser> jsonList = new ArrayList<SysUser>();
		List<ChUser> jsonList = new ArrayList<ChUser>();
		if(userList!=null){
			for(ChUser user:userList){
				jsonList.add(user);
			}
		}
		Renders.renderJson(jsonList);
		 return NONE;
	 }
   
   public String showSearch() throws Exception {
		return "showSearch";
	}
   
    
    
    
	//------------------------------getter/setter方法----------------------------------

	public String getDeptId() {
		return deptId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

//	public List<SysDept> getDeptList() {
//		return deptList;
//	}
//	public void setDeptList(List<SysDept> deptList) {
//		this.deptList = deptList;
//	}
	public List<ChRole> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<ChRole> roleList) {
		this.roleList = roleList;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public ChUser getUser() {
		return user;
	}

	public void setUser(ChUser user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getPictureOnly() {
		return pictureOnly;
	}

	public void setPictureOnly(String pictureOnly) {
		this.pictureOnly = pictureOnly;
	}

	public String getEnRoleNames() {
		return enRoleNames;
	}

	public void setEnRoleNames(String enRoleNames) {
		this.enRoleNames = enRoleNames;
	}
	
//	public List<ChTeacher> getTeacherList() {
//    	return teacherList;
//    }

//	public void setTeacherList(List<ChTeacher> teacherList) {
//    	this.teacherList = teacherList;
//    }
	
	public List<ChRange> getRangeList() {
    	return rangeList;
    }

	public void setRangeList(List<ChRange> rangeList) {
    	this.rangeList = rangeList;
    }

	public String getFuncIds() {
    	return funcIds;
    }

	public void setFuncIds(String funcIds) {
    	this.funcIds = funcIds;
    }

	public String getTeacherId() {
    	return teacherId;
    }

	public void setTeacherId(String teacherId) {
    	this.teacherId = teacherId;
    }
	
	public List<ChRolebutton> getChRolebuttons() {
    	return chRolebuttons;
    }

	public void setChRolebuttons(List<ChRolebutton> chRolebuttons) {
    	this.chRolebuttons = chRolebuttons;
    }

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<ChFunction> getLevelOneMenuList() {
		return levelOneMenuList;
	}

	public void setLevelOneMenuList(List<ChFunction> levelOneMenuList) {
		this.levelOneMenuList = levelOneMenuList;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
}
