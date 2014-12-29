package com.ch.sys.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ch.base.controller.BaseController;
import com.ch.base.model.SessionInfo;
import com.ch.base.model.easyui.Grid;
import com.ch.base.model.easyui.Json;
import com.ch.base.util.ConfigUtil;
import com.ch.base.util.HqlFilter;
import com.ch.base.util.JsonUtil;
import com.ch.sys.model.Role;
import com.ch.sys.model.User;
import com.ch.sys.service.RoleServiceI;
import com.ch.sys.service.UserServiceI;

@Controller
@RequestMapping("/auth/role")
public class RoleController extends BaseController<Role>{

	@Resource
	public void setService(RoleServiceI service) {
		this.service = service;
	}
	
	@Resource
	private UserServiceI userService;
	
	/**
	 * 角色grid
	 */
	@Override
	@RequestMapping(value="/grid")
	public void grid(HttpServletRequest request, HttpServletResponse response,HttpSession session,Integer page,Integer rows,PrintWriter pw) {
		
		Grid grid = new Grid();
		HqlFilter hqlFilter = new HqlFilter(request);
//		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
//		hqlFilter.addFilter("QUERY_user#id_S_EQ", sessionInfo.getUser().getId());
		grid.setTotal(((RoleServiceI) service).countRoleByFilter(hqlFilter));
		grid.setRows(((RoleServiceI) service).findRoleByFilter(hqlFilter, page, rows));
		JsonUtil.writeJson(grid,pw);
	}

	/**
	 * 保存一个角色
	 */
	@Override
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public void save(Role data,HttpServletRequest request, HttpServletResponse response,HttpSession session,PrintWriter pw){
		Json json = new Json();
		if (data != null) {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
			((RoleServiceI) service).saveRole(data, sessionInfo.getUser().getId());
			json.setSuccess(true);
		}

		JsonUtil.writeJson(json,pw);
	}
	

	/**
	 * 角色授权
	 */
	@RequestMapping(value="/grant")
	public void grant(String id,String ids,PrintWriter pw) {
		Json json = new Json();
		((RoleServiceI) service).grant(id, ids);
		json.setSuccess(true);
		JsonUtil.writeJson(json,pw);
	}

	/**
	 * 获得当前用户能看到的所有角色树
	 */
	@RequestMapping(value="/getRolesTree_doNotNeedSecurity")
	public void getRolesTree(HttpSession session,PrintWriter pw) {
//		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
//		User user = userService.getById(sessionInfo.getUser().getId());
//		Set<Role> roles = user.getRoles();
//		List<Role> l = new ArrayList<Role>(roles);
		List<Role> l = service.find();
		Collections.sort(l, new Comparator<Role>() {// 排序
					@Override
					public int compare(Role o1, Role o2) {
						if (o1.getSeq() == null) {
							o1.setSeq(1000);
						}
						if (o2.getSeq() == null) {
							o2.setSeq(1000);
						}
						return o1.getSeq().compareTo(o2.getSeq());
					}
				});
		JsonUtil.writeJson(l,pw);
	}

	/**
	 * 获得当前用户的角色
	 */
	@RequestMapping(value="/getRoleByUserId_doNotNeedSecurity")
	public void getRoleByUserId(String id,HttpServletRequest request,PrintWriter pw) {
		HqlFilter hqlFilter = new HqlFilter(request);
		hqlFilter.addFilter("QUERY_user#id_S_EQ", id);
		List<Role> roles = ((RoleServiceI) service).findRoleByFilter(hqlFilter);
		JsonUtil.writeJson(roles,pw);
	}

	/**
	 * 用户角色分布报表
	 */
	@RequestMapping(value="/userRoleChart_doNotNeedSecurity")
	public void userRoleChart(PrintWriter pw) {
		List<Role> roles = service.find();
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (Role role : roles) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("name", role.getName());
			m.put("y", userService.countUserByRoleId(role.getId()));
			m.put("sliced", false);
			m.put("selected", false);
			l.add(m);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("name", "无");
		m.put("y", userService.countUserByNotRoleId());
		m.put("sliced", true);
		m.put("selected", true);
		l.add(m);
		JsonUtil.writeJson(l,pw);
	}
}
