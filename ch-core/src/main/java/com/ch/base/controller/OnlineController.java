/**
 * 
 */
package com.ch.base.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.base.model.Online;
import com.ch.sys.service.OnlineServiceI;

/**
 * @author zhiqiang
 *
 */
@Controller
@RequestMapping("/base/online")
public class OnlineController extends BaseController<Online>{

	@Resource
	public void setService(OnlineServiceI service) {
		this.service = service;
	}
}
