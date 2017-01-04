package com.changhong.system.service;

import java.util.List;
import java.util.Map;

import com.changhong.base.entity.Page;
import com.changhong.base.service.BaseService;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChUser;
import com.changhong.system.entity.ChUserfunc;

/**
 * @author wanghao
 * 2013-07-08 
 */
public interface UserServiceCh extends BaseService<ChUser> {
	
	/**
	 * 按条件查询用户
	 */
	public Page findUsers(Page page,String roleId,String userName,List<String> userIdList) throws Exception;

	/**
	 * 同时保存多个SysUser，在excel导入用户时使用
	 * @param userList
	 * @return
	 * @throws Exception
	 */
	public int saveUserList(List<ChUser> userList) throws Exception;

/*	*//**
	 * 查询流程执行人
	 * @param roleId
	 * @return
	 *//*
	public List<WfExecutor> findWfExecutor(String roleId,String wfCat,String operateType) throws Exception;*/

	/**
	 * 改变用户状态
	 * @param userId
	 * @param userState
	 * @throws Exception
	 */
	public void changeUserState(ChUser user) throws Exception;
	
	/**
	 * 保存或更新用户，包含了用户角色的权限范围
	 * @param user
	 * @param funcIds
	 * @param roleIds
	 * @throws Exception
	 */
	public ChUser saveOrUpdate(ChUser user, String funcIds, String roleIds) throws Exception;
	
	/**
	 * @param roleId
	 * @return
	 */
	public List<ChUser> findUserByRoleId(String roleId) throws Exception;
	
	public List<ChFunction> findLevelOneMenu() throws Exception;
	
	public String getUserRoleNames(ChUser user);
	
	public List<ChUserfunc> findUserFunc(Map<String, String> paramMap)throws Exception ;
}
