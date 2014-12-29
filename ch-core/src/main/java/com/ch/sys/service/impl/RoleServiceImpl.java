package com.ch.sys.service.impl;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ch.base.dao.BaseDaoI;
import com.ch.base.service.impl.BaseServiceImpl;
import com.ch.base.util.HqlFilter;
import com.ch.sys.model.Resource;
import com.ch.sys.model.Role;
import com.ch.sys.model.User;
import com.ch.sys.service.RoleServiceI;

/**
 * 角色业务逻辑
 * 
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleServiceI {

	@Autowired
	private BaseDaoI<User> userDao;
	@Autowired
	private BaseDaoI<Resource> resourceDao;

	@Override
	public List<Role> findRoleByFilter(HqlFilter hqlFilter, int page, int rows) {
		String hql = "select distinct t from Role t join t.users user";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams(), page, rows);
	}

	@Override
	public List<Role> findRoleByFilter(HqlFilter hqlFilter) {
		String hql = "select distinct t from Role t join t.users user";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	@Override
	public Long countRoleByFilter(HqlFilter hqlFilter) {
		String hql = "select count(distinct t) from Role t join t.users user";
		return count(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
	}

	@Override
	public void saveRole(Role role, String userId) {
		save(role);

		User user = userDao.getById(User.class, userId);
		user.getRoles().add(role);// 把新添加的角色与当前用户关联
	}

	@Override
	public void grant(String id, String resourceIds) {
		Role role = getById(id);
		if (role != null) {
			role.setResources(new HashSet<Resource>());
			for (String resourceId : resourceIds.split(",")) {
				if (!StringUtils.isBlank(resourceId)) {
					Resource resource = resourceDao.getById(Resource.class, resourceId);
					if (resource != null) {
						role.getResources().add(resource);
					}
				}
			}
		}
	}

}
