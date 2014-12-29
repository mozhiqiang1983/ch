package com.ch.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 字符集编码拦截器
 * 
 */
public class EncodingInterceptor extends HandlerInterceptorAdapter  {

	private static final Logger logger = Logger.getLogger(EncodingInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		return true;
	}
	
	

}
