package com.ch.base.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ch.base.model.SessionInfo;
import com.ch.base.util.ConfigUtil;
import com.ch.sys.model.Resource;
import com.ch.sys.model.Role;

/**
 * 权限拦截器
 * 
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(SecurityInterceptor.class);

	
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ConfigUtil.getSessionInfoName());
		String servletPath = request.getServletPath();

		//servletPath = StringUtils.substringBeforeLast(servletPath, ".");// 去掉后面的后缀 *.do *.action之类的

		logger.info("进入权限拦截器->访问的资源为：[" + servletPath + "]");

		Set<Role> roles = sessionInfo.getUser().getRoles();
		for (Role role : roles) {
			for (Resource resource : role.getResources()) {
				if (resource != null && StringUtils.equals(resource.getUrl(), servletPath)) {
					return true;
				}
			}
		}
		

		String errMsg = "您没有访问此功能的权限！功能路径为[" + servletPath + "]请联系管理员给你赋予相应权限。";
		logger.info(errMsg);
		request.setAttribute("msg", errMsg);
		request.getRequestDispatcher("/error/noSecurity").forward(request, response);  
		return false;
		
	}


}
