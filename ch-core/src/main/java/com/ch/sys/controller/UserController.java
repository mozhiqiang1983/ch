/**
 * 
 */
package com.ch.sys.controller;

import java.io.PrintWriter;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ch.base.controller.BaseController;
import com.ch.base.model.SessionInfo;
import com.ch.base.model.easyui.Json;
import com.ch.base.util.BeanUtils;
import com.ch.base.util.ConfigUtil;
import com.ch.base.util.HqlFilter;
import com.ch.base.util.IpUtil;
import com.ch.base.util.JsonUtil;
import com.ch.base.util.MD5Util;
import com.ch.sys.model.Role;
import com.ch.sys.model.User;
import com.ch.sys.service.UserServiceI;

/**
 * @author zhiqiang
 *
 */
@Controller
@RequestMapping("/auth/user")
public class UserController extends BaseController<User>{
	
	@Resource
	public void setService(UserServiceI service) {
		this.service = service;
	}
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 注销系统
	 */
	@RequestMapping(value="/logout_doNotNeedSessionAndSecurity")
	public void logout(HttpSession session,PrintWriter pw) {
		if (session != null) {
			session.invalidate();
		}
		Json j = new Json();
		j.setSuccess(true);
		JsonUtil.writeJson(j,pw);
	}

	/**
	 * 注册
	 */
	@RequestMapping(value="/reg_doNotNeedSessionAndSecurity")
	synchronized public void reg(User data,HttpServletRequest request,HttpSession session,PrintWriter pw) {
		Json json = new Json();
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
		User user = service.getByFilter(hqlFilter);
		if (user != null) {
			json.setMsg("用户名已存在！");
			JsonUtil.writeJson(json,pw);
		} else {
			User u = new User();
			u.setLoginname(data.getLoginname());
			u.setPwd(MD5Util.md5(data.getPwd()));
			service.save(u);
			login(user,request,session,pw);
		}
	}

	/**
	 * 登录
	 */
	@RequestMapping(value="/login_doNotNeedSessionAndSecurity")
	public void login(User data,HttpServletRequest request,HttpSession session,PrintWriter pw) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
		hqlFilter.addFilter("QUERY_t#pwd_S_EQ", MD5Util.md5(data.getPwd()));
		User user = service.getByFilter(hqlFilter);
		Json json = new Json();
		if (user != null) {
			json.setSuccess(true);

			SessionInfo sessionInfo = new SessionInfo();
			Hibernate.initialize(user.getRoles());
			
			for (Role role : user.getRoles()) {
				Hibernate.initialize(role.getResources());
			}

			user.setIp(IpUtil.getIpAddr(request));
			sessionInfo.setUser(user);
			session.setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
		} else {
			json.setMsg("用户名或密码错误！");
		}
		JsonUtil.writeJson(json,pw);
	}

	/**
	 * 修改自己的密码
	 */
	@RequestMapping(value="/updateCurrentPwd_doNotNeedSessionAndSecurity", method=RequestMethod.POST)
	public void updateCurrentPwd(User data,HttpSession session,PrintWriter pw) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		Json json = new Json();
		User user = service.getById(sessionInfo.getUser().getId());
		user.setPwd(MD5Util.md5(data.getPwd()));
		user.setUpdatedatetime(new Date());
		service.update(user);
		json.setSuccess(true);
		JsonUtil.writeJson(json,pw);
	}

	/**
	 * 修改用户角色
	 */
	@RequestMapping(value="/grantRole", method=RequestMethod.POST)
	public void grantRole(String id,String ids,PrintWriter pw) {
		Json json = new Json();
		((UserServiceI) service).grantRole(id, ids);
		json.setSuccess(true);
		JsonUtil.writeJson(json,pw);
	}


	/**
	 * 新建一个用户
	 */
	@Override
	@RequestMapping(value="/save", method=RequestMethod.POST)
	synchronized public void save(User data, HttpServletRequest request,HttpServletResponse response, HttpSession session,PrintWriter pw) {
		Json json = new Json();
		if (data != null) {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
			User user = service.getByFilter(hqlFilter);
			if (user != null) {
				json.setMsg("新建用户失败，用户名已存在！");
			} else {
				data.setPwd(MD5Util.md5("123456"));
				service.save(data);
				json.setMsg("新建用户成功！默认密码：123456");
				json.setSuccess(true);
			}
		}
		JsonUtil.writeJson(json,pw);
	}

	/**
	 * 更新一个用户
	 */
	@Override
	@RequestMapping(value="/update", method=RequestMethod.POST)
	synchronized public void update(User data, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, PrintWriter pw) {
		
		Json json = new Json();
		json.setMsg("更新失败！");
		if (data != null && !StringUtils.isBlank(data.getId())) {
			HqlFilter hqlFilter = new HqlFilter();
			hqlFilter.addFilter("QUERY_t#id_S_NE", data.getId());
			hqlFilter.addFilter("QUERY_t#loginname_S_EQ", data.getLoginname());
			User user = service.getByFilter(hqlFilter);
			if (user != null) {
				json.setMsg("更新用户失败，用户名已存在！");
			} else {
				User t = service.getById(data.getId());
				BeanUtils.copyNotNullProperties(data, t, new String[] { "createdatetime" });
				service.update(t);
				json.setSuccess(true);
				json.setMsg("更新成功！");
			}
		}
		JsonUtil.writeJson(json,pw);
	}

//	@Override
//	public void find(HttpServletRequest request, HttpServletResponse response,
//			HttpSession session, Integer page, Integer rows, PrintWriter pw) {
//		
//		List<Map<String,Object>> l = jdbcTemplate.queryForList("select t.* from user t");
//		List<User> users = jdbcTemplate.query("select t.* from user t", new BeanPropertyRowMapper(User.class));
//
//		JsonUtil.writeJson(users,pw);
//	}

	

}
