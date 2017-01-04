package com.changhong.system.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.changhong.base.constants.enums.Auth;
import com.changhong.base.dao.hibernate.BaseHibernateDao;
import com.changhong.base.service.impl.BaseServiceImpl;
import com.changhong.base.utils.FastMap;
import com.changhong.system.entity.ChFunction;
import com.changhong.system.entity.ChRole;
import com.changhong.system.entity.ChRolefunc;
import com.changhong.system.service.RoleService;
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<ChRole> implements RoleService {
	
	@Autowired
	private BaseHibernateDao<ChRolefunc> chRoleFuncDao;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int saveOrUpdate(ChRole role,String checkIds) throws Exception{
		
		if(StringUtils.isEmpty(role.getId())){
			save(role);
			saveOrUpdateChRoleFuncTemp(role, checkIds);
		}else{
			role.setChFuncs(null);
			update(role);
		}
		return 0;
	}
	/**
	 * 更新角色权限，重新开启一个事务；这里不知道会不会导致异常
	 * @param role
	 * @param menuIds
	 * @return
	 * @throws Exception 
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public int saveOrUpdateChRoleFunc(ChRole role,String checkIds) throws Exception{
		saveOrUpdateChRoleFuncTemp(role, checkIds);
		return 0;
	}
	
	private void saveOrUpdateChRoleFuncTemp(ChRole role,String checkIds) throws Exception{
		ChRolefunc roleFunc = null;
		ChFunction func = null;
		if(StringUtils.isNotBlank(checkIds)){
			String[] arr = checkIds.split(",");
			//插入新的
			if(checkIds!=null && arr.length>0){
				for(int i=0;i<arr.length;i++){
					if(StringUtils.isNotBlank(arr[i])){
						roleFunc = new ChRolefunc();
						func = new ChFunction();
						func.setId(arr[i]);
						
						roleFunc.setChRole(role);
						roleFunc.setChFunc(func);
						chRoleFuncDao.save(roleFunc);
					}
				}
				
			}
		}
	}
	
//	@SuppressWarnings("unchecked")
//	public List<ChRolefunc> findSysRoleMenuByRoleId(String roleId) throws Exception{
//		return chRoleFuncDao.findList("system.findRoleFuncs",new FastMap().newHashMap().set("roleId", roleId));
//	}
	
	@SuppressWarnings("unchecked")
	public List<ChRolefunc> findChRoleFuncByRoleId(String roleId) throws Exception{
		return chRoleFuncDao.findList("system.findRoleFuncs",new FastMap().newHashMap().set("roleId", roleId));
	}
	
	
	@SuppressWarnings("unchecked")
	public Auth findAuthByRoleId(String roleId, String funcPath) throws Exception{
		Map<String,Object> paramMap = new FastMap().newHashMap().set("roleId", roleId).set("funcPath", funcPath);
		ChRolefunc rm = chRoleFuncDao.findOne("system.findRoleFuncs",paramMap);
		if(rm!=null){
			return Auth.W;
		}else{
			return Auth.N;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ChRole> findRoles(Map<String,?> paramMap) throws Exception{
		return findList("system.findRoles", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public int getCountSubRoleNumByParentId(String parentId) throws Exception{
		Map<String,String> paramMap = new FastMap().newHashMap().set("parentId", parentId);
		return findCount("system.getCountSubChRoleNumByParentId",paramMap);
	}
	
	/**
	 * 改变用户状态
	 * 2013-07-10
	 * @throws Exception
	 */
	public void changeUserState(ChRole role, String state) throws Exception {
		if (state.equals("Y"))
			role.setChRoleFlag("N");
		else
			role.setChRoleFlag("Y");
		update(role);
    }
}
