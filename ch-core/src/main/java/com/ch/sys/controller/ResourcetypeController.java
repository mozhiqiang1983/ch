/**
 * 
 */
package com.ch.sys.controller;

import java.io.PrintWriter;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ch.base.controller.BaseController;
import com.ch.base.util.JsonUtil;
import com.ch.sys.model.Resourcetype;
import com.ch.sys.service.ResourcetypeServiceI;

/**
 * @author zhiqiang
 *
 */
@Controller
@RequestMapping("/auth/resourcetype")
public class ResourcetypeController extends BaseController<Resourcetype> {

	@Resource
	public void setService(ResourcetypeServiceI service) {
		this.service = service;
	}
	
	@RequestMapping(value="/combobox_doNotNeedSecurity")
	public void combobox(PrintWriter pw) {
		JsonUtil.writeJson(((ResourcetypeServiceI) service).findResourcetype(),pw);
	}
	
}
