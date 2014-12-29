package com.ch.base.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
	

	@RequestMapping("/404")
	public String error404(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model) throws ServletException, IOException
	{	
		return "error/404";
	}
	
	@RequestMapping("/500")
	public String error500(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model) throws ServletException, IOException
	{	
		return "error/500";
	}
	
	@RequestMapping("/noSession")
	public String noSession(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model) throws ServletException, IOException
	{	
		return "error/noSession";
	}
	
	@RequestMapping("/noSecurity")
	public String noSecurity(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model) throws ServletException, IOException
	{	
		return "error/noSecurity";
	}
	
	@RequestMapping("/global")
	public String global(HttpServletRequest request,HttpServletResponse response,HttpSession session,Model model) throws ServletException, IOException
	{	
		return "error/global";
	}
	
}
