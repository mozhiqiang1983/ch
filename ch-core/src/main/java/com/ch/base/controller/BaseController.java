/**
 * 
 */
package com.ch.base.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ch.base.model.easyui.Grid;
import com.ch.base.model.easyui.Json;
import com.ch.base.service.BaseServiceI;
import com.ch.base.util.BeanUtils;
import com.ch.base.util.HqlFilter;
import com.ch.base.util.JsonUtil;

/**
 * @author mozhiqiang
 *
 */
public class BaseController<T> {
	
	private static final Logger logger = Logger.getLogger(BaseController.class);
	
	protected BaseServiceI<T> service;
	
	/**
	 * 获取资源
	 * @param id
	 * @param pw
	 */
	@RequestMapping(value="/get")
	public void getById(String id, PrintWriter pw)
	{
		if (!StringUtils.isBlank(id)) {
			JsonUtil.writeJson(service.getById(id),pw);
		}
		else
		{
			Json json = new Json();
			json.setSuccess(false);
			json.setMsg("id不能为空");
			JsonUtil.writeJson(json,pw);
		}
		
	}
	
	/**
	 * 查找所有资源
	 * @param request
	 * @param response
	 * @param page
	 * @param rows
	 * @param pw
	 */
	@RequestMapping("/find")
	public void find(HttpServletRequest request, HttpServletResponse response,HttpSession session,Integer page,Integer rows,PrintWriter pw) {
		HqlFilter hqlFilter = new HqlFilter(request);
		if(page==null || rows==null)
		{
			JsonUtil.writeJson(service.findByFilter(hqlFilter), pw);
		}
		else
		{
			JsonUtil.writeJson(service.findByFilter(hqlFilter, page, rows),pw);
		}
		
	}
	
	/**
	 * 获取表格,转成easyui格式
	 * @param request
	 * @param response
	 * @param page
	 * @param rows
	 * @param pw
	 */
	@RequestMapping("/grid")
	public void grid(HttpServletRequest request, HttpServletResponse response,HttpSession session,Integer page,Integer rows,PrintWriter pw){
		HqlFilter hqlFilter = new HqlFilter(request);
		Grid grid = new Grid();
		if(page==null || rows==null)
		{
			grid.setRows(service.findByFilter(hqlFilter));
			grid.setTotal(service.countByFilter(hqlFilter));
			JsonUtil.writeJson(grid, pw);
		}
		else
		{
			grid.setRows(service.findByFilter(hqlFilter,page,rows));
			grid.setTotal(service.countByFilter(hqlFilter));
			JsonUtil.writeJson(grid, pw);
		}
		
	}
	
	/**
	 * 删除资源
	 * @param id
	 * @param pw
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public void delete(String id, PrintWriter pw) {
		Json json = new Json();
		if (!StringUtils.isBlank(id)) {
			T t = service.getById(id);
			service.delete(t);
			json.setSuccess(true);
			json.setMsg("删除成功！");
		}
		JsonUtil.writeJson(json, pw);
	}
	
	/**
	 * 新增资源
	 * @param data
	 * @param pw
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public void save(T data,HttpServletRequest request, HttpServletResponse response,HttpSession session,PrintWriter pw){
		Json json = new Json();
		if (data != null) {
			service.save(data);
			json.setSuccess(true);
			json.setMsg("新建成功！");
		}
		JsonUtil.writeJson(json, pw);
	}
	
	/**
	 * 更新资源
	 * @param data
	 * @param pw
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public void update(T data,HttpServletRequest request, HttpServletResponse response,HttpSession session,PrintWriter pw) {
		Json json = new Json();
		String reflectId = null;
		try {
			if (data != null) {
				reflectId = (String) FieldUtils.readField(data, "id", true);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isBlank(reflectId)) {
			T t = service.getById(reflectId);
			BeanUtils.copyNotNullProperties(data, t, new String[] { "createdatetime" });
			service.update(t);
			json.setSuccess(true);
			json.setMsg("更新成功！");
		}
		JsonUtil.writeJson(json, pw);
	}

	/**
	 * 获得treeGrid，treeGrid由于提供了pid的扩展，所以不分页
	 */
	@RequestMapping(value="/treeGrid")
	public void treeGrid(HttpServletRequest request, HttpServletResponse response,HttpSession session,PrintWriter pw) {
		HqlFilter hqlFilter = new HqlFilter(request);
		JsonUtil.writeJson(service.findByFilter(hqlFilter),pw);
	}
	
	@ExceptionHandler(Exception.class)
	public String operateExp(Exception ex,HttpServletRequest request){
//		model.addAttribute("msg", ex.getMessage());
		request.setAttribute("msg", ex.getMessage());
		return "/error/global";
	}
}
