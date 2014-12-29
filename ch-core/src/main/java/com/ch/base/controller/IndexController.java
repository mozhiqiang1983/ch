/**
 * 
 */
package com.ch.base.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.base.model.SessionInfo;
import com.ch.base.util.ConfigUtil;

/**
 * @author zhiqiang
 *
 */

@RequestMapping("")
public class IndexController {
	
	@RequestMapping("/{page}.html")
	public String index(@PathVariable String page,HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model) throws ServletException, IOException
	{
		model.addAttribute("contextPath", request.getContextPath());
		model.addAttribute("request", request);
		model.addAttribute("response", response);
		model.addAttribute("session", session);
		
		if(session.getAttribute("sessionInfo")!=null)
		{
			model.addAttribute("user", ((SessionInfo)session.getAttribute(ConfigUtil.getSessionInfoName())).getUser());
		}
		
		return page; 
	}

}
