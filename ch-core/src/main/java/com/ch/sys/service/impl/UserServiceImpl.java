package com.ch.sys.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ch.base.dao.BaseDaoI;
import com.ch.base.service.impl.BaseServiceImpl;
import com.ch.sys.model.Role;
import com.ch.sys.model.User;
import com.ch.sys.service.UserServiceI;

/**
 * 用户业务逻辑
 * 
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserServiceI {

	@Autowired
	private BaseDaoI<Role> roleDao;


	@Override
	public void grantRole(String id, String roleIds) {
		User user = getById(id);
		if (user != null) {
			user.setRoles(new HashSet<Role>());
			for (String roleId : roleIds.split(",")) {
				if (!StringUtils.isBlank(roleId)) {
					Role role = roleDao.getById(Role.class, roleId);
					if (role != null) {
						user.getRoles().add(role);
					}
				}
			}
		}
	}



	@Override
	public List<Long> userCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("a", k);
			params.put("b", k + 2);
			k = k + 2;
			l.add(count("select count(*) from User t where HOUR(t.createdatetime)>=:a and HOUR(t.createdatetime)<:b", params));
		}
		return l;
	}

	@Override
	public Long countUserByRoleId(String roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		String hql = "select count(*) from User t join t.roles role where role.id = :roleId";
		return count(hql, params);
	}

	@Override
	public Long countUserByNotRoleId() {
		String hql = "select count(*) from User t left join t.roles role where role.id is null";
		return count(hql);
	}

	@Override
	@Cacheable(value = "user",key="caches[0].name +':'+ #id")
	public User getById(Serializable id) {
		return super.getById(id);
	}
	
	

}
