package com.ch.base.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * 如果上传的图片或者文件丢失的情况下，返回一个默认的图片，用于提示用户
 * 
 */
public class File404Filter extends HttpServlet implements Filter {

	private static final long serialVersionUID = -8171352302930234572L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		// String contextPath = req.getContextPath();
		// String requestURI = req.getRequestURI();
		String servletPath = req.getServletPath();

		if (servletPath.startsWith(ConfigUtil.get("uploadPath"))) {// 要访问上传的资源了
			String webParentPath = new File(req.getSession().getServletContext().getRealPath("/")).getParent();// 当前WEB环境的上层目录
			File f = new File(webParentPath + servletPath);// 要访问的文件在服务器中的绝对路径
			if (!f.exists()) {// 文件不存在了，返回给客户一个默认图片
				f = new File(webParentPath + "/style/images/blue_face/bluefaces_35.png");
				if (!f.exists()) {// 如果是mvn jetty:run启动的项目，则会走这里
					f = new File(webParentPath + "/webapp/style/images/blue_face/bluefaces_35.png");
				}
			}
			BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(f));
			byte[] buffer = new byte[1024];
			int length = 0;
			OutputStream out = response.getOutputStream();
			while ((length = bufferedInputStream.read(buffer)) > 0) {
				out.write(buffer, 0, length);
				out.flush();
			}
			bufferedInputStream.close();
			out.close();
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
