/**
 * 
 */
package com.ch.base.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.base.service.InitServiceI;

/**
 * @author mozhiqiang
 * 初始化基础数据
 */
@Controller
@RequestMapping("/init")
public class InitController {

	@Resource
	private InitServiceI initService;
	
	@RequestMapping(value="")
	public String init()
	{
		initService.initDb();
		return "redirect:/";
	}
}
