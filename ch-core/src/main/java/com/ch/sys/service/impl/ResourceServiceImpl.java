package com.ch.sys.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ch.base.dao.BaseDaoI;
import com.ch.base.service.impl.BaseServiceImpl;
import com.ch.base.util.BeanUtils;
import com.ch.base.util.HqlFilter;
import com.ch.sys.model.Resource;
import com.ch.sys.model.Role;
import com.ch.sys.model.User;
import com.ch.sys.service.ResourceServiceI;

/**
 * 资源业务逻辑
 * 
 * 
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceServiceI {

	@Autowired
	private BaseDaoI<User> userDao;

	/**
	 * 由于角色与资源关联，机构也与资源关联，所以查询用户能看到的资源菜单应该查询两次，最后合并到一起。
	 */
	@Override
	public List<Resource> getMainMenu(HqlFilter hqlFilter) {
		List<Resource> l = new ArrayList<Resource>();
		String hql = "select distinct t from Resource t join t.roles role join role.users user";
		List<Resource> resource_role = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_role);
//		hql = "select distinct t from Resource t join t.organizations organization join organization.users user";
//		List<Resource> resource_organization = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
//		l.addAll(resource_organization);
		l = new ArrayList<Resource>(new HashSet<Resource>(l));// 去重
		Collections.sort(l, new Comparator<Resource>() {// 排序
					@Override
					public int compare(Resource o1, Resource o2) {
						if (o1.getSeq() == null) {
							o1.setSeq(1000);
						}
						if (o2.getSeq() == null) {
							o2.setSeq(1000);
						}
						return o1.getSeq().compareTo(o2.getSeq());
					}
				});
		return l;
	}

	@Override
	public List<Resource> resourceTreeGrid(HqlFilter hqlFilter) {
		List<Resource> l = new ArrayList<Resource>();
		String hql = "select distinct t from Resource t join t.roles role join role.users user";
		List<Resource> resource_role = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		l.addAll(resource_role);
//		hql = "select distinct t from Resource t join t.organizations organization join organization.users user";
//		List<Resource> resource_organization = find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
//		l.addAll(resource_organization);
		l = new ArrayList<Resource>(new HashSet<Resource>(l));// 去重
		Collections.sort(l, new Comparator<Resource>() {// 排序
					@Override
					public int compare(Resource o1, Resource o2) {
						if (o1.getSeq() == null) {
							o1.setSeq(1000);
						}
						if (o2.getSeq() == null) {
							o2.setSeq(1000);
						}
						return o1.getSeq().compareTo(o2.getSeq());
					}
				});
		return l;
	}

	@Override
	public void updateResource(Resource resource) {
		if (!StringUtils.isBlank(resource.getId())) {
			Resource t = getById(resource.getId());
			Resource oldParent = t.getResource();
			BeanUtils.copyNotNullProperties(resource, t, new String[] { "createdatetime" });
			if (resource.getResource() != null) {// 说明要修改的节点选中了上级节点
				Resource pt = getById(resource.getResource().getId());// 上级节点
				isParentToChild(t, pt, oldParent);// 说明要将当前节点修改到当前节点的子或者孙子下
				t.setResource(pt);
			} else {
				t.setResource(null);
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点下
	 * 
	 * @param t
	 *            当前节点
	 * @param pt
	 *            要修改到的节点
	 * 
	 * @param oldParent
	 *            原上级节点
	 * @return
	 */
	private boolean isParentToChild(Resource t, Resource pt, Resource oldParent) {
		if (pt != null && pt.getResource() != null) {
			if (StringUtils.equals(pt.getResource().getId(), t.getId())) {
				pt.setResource(oldParent);
				return true;
			} else {
				return isParentToChild(t, pt.getResource(), oldParent);
			}
		}
		return false;
	}

	/**
	 * 由于新添加的资源，当前用户的角色或者机构并没有访问此资源的权限，所以这个地方重写save方法，将新添加的资源放到用户的第一个角色里面
	 */
	@Override
	public void saveResource(Resource resource, String userId) {
		save(resource);

		User user = userDao.getById(User.class, userId);
		Set<Role> roles = user.getRoles();
		if (roles != null && !roles.isEmpty()) {// 如果用户有角色，就将新资源放到用户的第一个角色里面
			List<Role> l = new ArrayList<Role>();
			l.addAll(roles);
			Collections.sort(l, new Comparator<Role>() {
				@Override
				public int compare(Role o1, Role o2) {
					if (o1.getCreatedatetime().getTime() > o2.getCreatedatetime().getTime()) {
						return 1;
					}
					if (o1.getCreatedatetime().getTime() < o2.getCreatedatetime().getTime()) {
						return -1;
					}
					return 0;
				}
			});
			l.get(0).getResources().add(resource);
		} else {// 如果用户没有角色
			
		}
	}

	@Override
	public List<Resource> findResourceByFilter(HqlFilter hqlFilter) {
		String hql = "select distinct t from Resource t left join t.roles role";
		return find(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
	}

}
