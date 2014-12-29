/**
 * 
 */
package com.ch.base.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.ch.base.model.SessionInfo;
import com.ch.base.util.ConfigUtil;
import com.ch.base.util.SecurityUtil;

import freemarker.ext.beans.BeansWrapper;

/**
 * @author mozhiqiang
 *
 */
@Controller
@RequestMapping(value="/page")
public class PageController {
	
	private static SecurityUtil securityUtil;
	
	@RequestMapping(value="/**")
	public String page(HttpServletResponse response,HttpServletRequest request,HttpSession session,Model model)
	{
		//由于参数不能带斜杠，所以用了一个非标准的方法获取参数
		String pattern = (String)request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);  
		String page = new AntPathMatcher().extractPathWithinPattern(pattern,request.getServletPath());
//		String page = searchTerm.replace(".p", "");
		
		if(securityUtil==null)
			securityUtil = new SecurityUtil(session);
		model.addAttribute("securityUtil", securityUtil);
		//获取session${session}
		model.addAttribute("session", session);
		model.addAttribute("request", request);
		model.addAttribute("response", response);
		//获取请求参数${tempvar["参数名"]}
		model.addAttribute("tempvar",requestParamToMap(request));
		//加载类的静态方法，前台通过${static["包名"]}获取类
		model.addAttribute("static",BeansWrapper.getDefaultInstance().getStaticModels());
		
		if(session.getAttribute("sessionInfo")!=null)
		{
			//获取登陆用户${loginUser}
			model.addAttribute("loginUser", ((SessionInfo)session.getAttribute(ConfigUtil.getSessionInfoName())).getUser());
		}
		
		return page;
	}
	
//	@RequestMapping(value="/{page}/html")
//	public String page(@PathVariable String module,@PathVariable String model,@PathVariable String page,HttpServletResponse response,HttpServletRequest request,HttpSession session,Model m)
//	{
//		if(securityUtil==null)
//			securityUtil = new SecurityUtil(session);
//		m.addAttribute("securityUtil", securityUtil);
//		m.addAttribute("session", session);
//		m.addAttribute("request", request);
//		m.addAttribute("response", response);
//		m.addAttribute("tempvar",requestParamToMap(request));
//		m.addAttribute("static",BeansWrapper.getDefaultInstance().getStaticModels());
//		
//		if(session.getAttribute("sessionInfo")!=null)
//		{
//			m.addAttribute("loginUser", ((SessionInfo)session.getAttribute(ConfigUtil.getSessionInfoName())).getUser());
//		}
//		
//		return module+"/"+model+"/"+page;
//	}
	
	private Map<String,String> requestParamToMap(HttpServletRequest request)
	{
		Map<String,String> returnMap = new HashMap<String,String>();
		for(Iterator<String> ite = request.getParameterMap().keySet().iterator();ite.hasNext();)
		{
			String key = ite.next();
			String[] values = (String[])request.getParameterMap().get(key);
			if(values!=null && values.length>0)
				returnMap.put(key, values[0]);
			else
				returnMap.put(key, "");
		}
		return returnMap;
	}
	

}
