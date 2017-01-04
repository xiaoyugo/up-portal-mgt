package com.changhong.system.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.dao.utils.Querys;
import com.changhong.base.entity.Page;
import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.base.utils.FastMap;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChUser;
import com.changhong.system.entity.ChUserfunc;
import com.changhong.system.service.UserServiceCh;

/**
 * 用户基本信息管理类
 * @author wanghao
 * 2013-07-08 
 */
@Service
@Transactional
@SuppressWarnings("unchecked")
public class UserServiceChImpl extends BaseServiceImpl<ChUser> implements UserServiceCh {
//	@Autowired
//	private BaseHibernateDao<SysDept> deptDao;
	
	public Page findUsers(Page page,String roleId,String userName,List<String> userIdList) throws Exception {
//		ChUser user = Sessions.getSysUser();
//		String parentDeptLevel = deptDao.get(SysDept.class, user.getSysDept().getId()).getDeptLevel();
		
		@SuppressWarnings("rawtypes")
		Map<String,String> paramMap = new FastMap().newHashMap()
		.set(Querys.PREFIX+"roleId", roleId)
//		.set("deptId", deptId)
		.set("roleId", roleId)
		.set("userName", "%"+userName+"%")
		.set("userIdList", userIdList);//userIdList !=null 时，是查看在线用户
//		.set("parentDeptLevel", (userIdList==null && user.getUserRoleType()==YN.N)?parentDeptLevel+"%":null);
		
		return this.findPage(page,"system.findChUsers",paramMap);
	}
	
	/**
	 * 查询流程执行人
	 * @throws Exception 
	 */
/*	public List<WfExecutor> findWfExecutor(String roleId,String wfCat,String operateType) throws Exception{
		Map<String,String> paramMap = new FastMap().newHashMap().set("roleId", roleId).set("wfCat", wfCat).set("operateType", operateType);
		return this.findList("workflow.findWfExecutor",paramMap);
	}*/
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int saveUserList(List<ChUser> userList) throws Exception{
		for(ChUser user : userList){
			this.save(user);
		}
		return 1;
	}


	public void changeUserState(ChUser user) throws Exception {
		if (user.getChUserState().equals("正常"))
			user.setChUserState("锁定");
		else 
			user.setChUserState("正常");
		update(user);
    }

	@Transactional(propagation=Propagation.REQUIRED)
	public ChUser saveOrUpdate(ChUser user, String funcIds, String roleIds) throws Exception {
		if(StringUtils.isEmpty(user.getId())){
			user.setChUserFuncs(funcIds);
			user.setChUserRoleids(roleIds);
			return save(user);
		}else{
			user.setChUserFuncs(funcIds);
			user.setChUserRoleids(roleIds);
			return update(user);
		}
    }

	@Override
	public List<ChUser> findUserByRoleId(String roleId) throws Exception {
		return this.findByNameQuery(" select user from ChUser as user where 1=1 and user.chUserRoleids like '%"+roleId+"%' order by user.id asc");
	}

	@Override
	public List<ChFunction> findLevelOneMenu() throws Exception {
		return this.findByNameQuery(" select menu from ChFunction as menu where 1=1 and menu.chFunc.id = '1' order by menu.id asc");
	}

	@Override
	public String getUserRoleNames(ChUser user) {
		return null;
	}

	@Override
	public List<ChUserfunc> findUserFunc(Map<String, String> paramMap) throws Exception {
		return this.findList("system.findUserFunc", paramMap);
	}
}
