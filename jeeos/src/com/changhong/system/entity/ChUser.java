package com.changhong.system.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.changhong.base.dao.cache.CacheConstants;
import com.changhong.base.dao.cache.EhCacheManager;
import com.changhong.base.entity.IDEntity;

/**
 * ChUser entity. @author wanghao
 */
@Entity
@Table(name = "ch_user")
public class ChUser extends IDEntity implements java.io.Serializable,HttpSessionBindingListener{

	private static final long serialVersionUID = 9208842134270414255L;
	// Fields
	private String chUserLogname;
	private String chUserPassword;
	private String chUserSortno;
	private String chUsername;
	private String chUserState;
	private String chUserRoleids;
	private String chUserMaketime;
	private String chUserFuncs;
	private String chUserMemo;
	private String chUserIp;
	private String chUserDesk;
	private Set<ChRecord> chRecords;

	//--------------------下面几个 是瞬时变量-------------
	private ChRole chRole;//当前访问系统的角色
	private String roleId;
	private String loginTime;//登录时间
	private List<String> funcPaths = new ArrayList<String>();//可访问的权限路径
	
	private List<ChRole> chRoles = new ArrayList<ChRole>(0);
	private Map<String,List<ChRolebutton>> funcBtnMap;

	// Constructors

	/** default constructor */
	public ChUser() {
	}

	/** minimal constructor */
	public ChUser(String id) {
		super(id);
	}

	/** full constructor */
	public ChUser(String id, String chUserLogname, String chUserPassword, String chUserSortno,
	        String chUserUsername, String chUserState, String chUserRoleids, String chUserMaketime, String chUserFuncs,
	        String chUserMemo, ChRole chRole, String roleId, String loginTime, List<String> funcPaths, List<ChRole> chRoles, Set<ChRecord> chRecords,String chUserIp,String chUserDesk) {
		super(id);
		this.chUserLogname = chUserLogname;
		this.chUserPassword = chUserPassword;
		this.chUserSortno = chUserSortno;
		this.chUsername = chUserUsername;
		this.chUserState = chUserState;
		this.chUserRoleids = chUserRoleids;
		this.chUserMaketime = chUserMaketime;
		this.chUserFuncs = chUserFuncs;
		this.chUserMemo = chUserMemo;
		this.loginTime = loginTime;
		this.chRole = chRole;
		this.roleId = roleId;
		this.funcPaths = funcPaths;
		this.chRoles = chRoles;
		this.chRecords=chRecords;
		this.chUserIp=chUserIp;
		this.chUserDesk=chUserDesk;
	}

	// Property accessors
	@Column(name = "ch_user_logname", length = 20)
	public String getChUserLogname() {
		return this.chUserLogname;
	}

	public void setChUserLogname(String chUserLogname) {
		this.chUserLogname = chUserLogname;
	}

	@Column(name = "ch_user_password", length = 10)
	public String getChUserPassword() {
		return this.chUserPassword;
	}

	public void setChUserPassword(String chUserPassword) {
		this.chUserPassword = chUserPassword;
	}

	@Column(name = "ch_user_sortno", length = 10)
	public String getChUserSortno() {
		return this.chUserSortno;
	}

	public void setChUserSortno(String chUserSortno) {
		this.chUserSortno = chUserSortno;
	}

	@Column(name = "ch_user_username", length = 20)
	public String getChUsername() {
		return this.chUsername;
	}

	public void setChUsername(String chUsername) {
		this.chUsername = chUsername;
	}

	@Column(name = "ch_user_state", length = 10)
	public String getChUserState() {
		return this.chUserState;
	}

	public void setChUserState(String chUserState) {
		this.chUserState = chUserState;
	}

	@Column(name = "ch_user_roleids", length = 100)
	public String getChUserRoleids() {
		return this.chUserRoleids;
	}

	public void setChUserRoleids(String chUserRoleids) {
		this.chUserRoleids = chUserRoleids;
	}

	@Column(name = "ch_user_maketime", length = 20)
	public String getChUserMaketime() {
		return this.chUserMaketime;
	}

	public void setChUserMaketime(String chUserMaketime) {
		this.chUserMaketime = chUserMaketime;
	}

	@Column(name = "ch_user_funcs", length = 200)
	public String getChUserFuncs() {
		return this.chUserFuncs;
	}

	public void setChUserFuncs(String chUserFuncs) {
		this.chUserFuncs = chUserFuncs;
	}

	@Column(name = "ch_user_memo", length = 200)
	public String getChUserMemo() {
		return this.chUserMemo;
	}

	public void setChUserMemo(String chUserMemo) {
		this.chUserMemo = chUserMemo;
	}
	
	@Column(name = "ch_user_ip", length = 15)
	public String getChUserIp() {
		return chUserIp;
	}
	
	public void setChUserIp(String chUserIp) {
		this.chUserIp = chUserIp;
	}

	@Transient
	public ChRole getChRole() {
    	return chRole;
    }

	public void setChRole(ChRole chRole) {
    	this.chRole = chRole;
    }

	@Transient
	public String getRoleId() {
    	return roleId;
    }

	public void setRoleId(String roleId) {
    	this.roleId = roleId;
    }

	@Transient
	public String getLoginTime() {
    	return loginTime;
    }

	public void setLoginTime(String loginTime) {
    	this.loginTime = loginTime;
    }

	@Transient
	public List<String> getFuncPaths() {
    	return funcPaths;
    }

	public void setFuncPaths(List<String> funcPaths) {
    	this.funcPaths = funcPaths;
    }
	
	@Transient
	public List<ChRole> getChRoles() {
    	return chRoles;
    }

	public void setChRoles(List<ChRole> chRoles) {
    	this.chRoles = chRoles;
    }
	
	@OneToMany(cascade={CascadeType.REMOVE}, mappedBy="chUser")
	public Set<ChRecord> getChRecords() {
    	return chRecords;
    }

	public void setChRecords(Set<ChRecord> chRecords) {
    	this.chRecords = chRecords;
    }

	@Column(name = "ch_user_desk", length = 100)
	public String getChUserDesk() {
		return chUserDesk;
	}

	public void setChUserDesk(String chUserDesk) {
		this.chUserDesk = chUserDesk;
	}

	public void valueBound(HttpSessionBindingEvent event) {
		//放入ehcache
		EhCacheManager.put(CacheConstants.HTTP_SESSION_CACHE, event.getSession().getId(), this);
	}

    /**
     * 用户退出时，从ehcache缓存中删除该用户的信息
     */
	public void valueUnbound(HttpSessionBindingEvent event) {
		//从ehcache中移除
		EhCacheManager.remove(CacheConstants.HTTP_SESSION_CACHE, event.getSession().getId());
	}

	@Transient
    public Map<String, List<ChRolebutton>> getFuncBtnMap()
    {
        return funcBtnMap;
    }

    public void setFuncBtnMap( Map<String, List<ChRolebutton>> funcBtnMap )
    {
        this.funcBtnMap = funcBtnMap;
    }

}