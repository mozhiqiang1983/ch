package com.ch.base.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.ch.base.model.SessionInfo;
import com.ch.sys.model.Resource;
import com.ch.sys.model.Role;
import com.ch.sys.model.User;

/**
 * 用于前台页面判断是否有权限的工具类
 * 
 * 
 */
public class SecurityUtil {
	private static HttpSession session;

	public SecurityUtil(HttpSession session) {
		this.session = session;
	}

	/**
	 * 判断当前用户是否可以访问某资源
	 * 
	 * @param url
	 *            资源地址
	 * @return
	 */
	public static boolean havePermission(String url) {
		Object obj = session.getAttribute(ConfigUtil.getSessionInfoName());
		SessionInfo sessionInfo = (SessionInfo)obj;
		return havePermission(sessionInfo.getUser(),url);
	}
	
	public static boolean havePermission(User user,String url) {
		List<Resource> resources = new ArrayList<Resource>();
		for (Role role : user.getRoles()) {
			resources.addAll(role.getResources());
		}
//		for (Organization organization : user.getOrganizations()) {
//			resources.addAll(organization.getResources());
//		}
		resources = new ArrayList<Resource>(new HashSet<Resource>(resources));// 去重(这里包含了当前用户可访问的所有资源)
		for (Resource resource : resources) {
			if (StringUtils.equals(resource.getUrl(), url)) {// 如果有相同的，则代表当前用户可以访问这个资源
				return true;
			}
		}
		return false;
	}
	
}
