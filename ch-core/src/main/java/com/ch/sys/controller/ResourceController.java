/**
 * 
 */
package com.ch.sys.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ch.base.controller.BaseController;
import com.ch.base.model.SessionInfo;
import com.ch.base.model.easyui.Json;
import com.ch.base.model.easyui.Tree;
import com.ch.base.util.BeanUtils;
import com.ch.base.util.ConfigUtil;
import com.ch.base.util.HqlFilter;
import com.ch.base.util.JsonUtil;
import com.ch.sys.model.Resource;
import com.ch.sys.service.ResourceServiceI;

/**
 * @author zhiqiang
 *
 */
@Controller
@RequestMapping("/auth/resource")
public class ResourceController extends BaseController<Resource> {

	@javax.annotation.Resource
	public void setService(ResourceServiceI service) {
		this.service = service;
	}
	
	/**
	 * 更新资源
	 */
	@Override
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public void update(Resource data,HttpServletRequest request,HttpServletResponse response,HttpSession session,PrintWriter pw) {
		Json json = new Json();
		if (!StringUtils.isBlank(data.getId())) {
			if (data.getResource() != null && StringUtils.equals(data.getId(), data.getResource().getId())) {
				json.setMsg("父资源不可以是自己！");
			} else {
				((ResourceServiceI) service).updateResource(data);
				json.setSuccess(true);
			}
		}
		JsonUtil.writeJson(json,pw);
	}

	
	/**
	 * 生成主菜单accordion
	 */
	@RequestMapping(value="/getMainMenu_doNotNeedSecurity")
	public void getMainMenu(HttpServletRequest request,HttpSession session,PrintWriter pw) {
		
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
//		String relam = getSession().getAttribute("relam").toString();
		/**  取所有权限 ,并取出父资源  **/
		HqlFilter hqlFilterAll = new HqlFilter(request);
		hqlFilterAll.addFilter("QUERY_user#id_S_EQ", sessionInfo.getUser().getId());
		hqlFilterAll.addFilter("QUERY_t#resourcetype#id_S_EQ", "0");// 0就是只查菜单
//		hqlFilterAll.addFilter("QUERY_t#relam#name_O_IN", "('"+relam+"','auth')");
		List<Resource> resourcesMenu = ((ResourceServiceI) service).getMainMenu(hqlFilterAll);//取出所有菜单资源
		
		Set<String> authSet = new HashSet<String>();
		
		List<Resource> resourcesRoot = new ArrayList<Resource>();//父菜单集合
		for(Resource resource:resourcesMenu)
		{
			authSet.add(resource.getId());
			if(resource.getResource()==null)
			{
				resourcesRoot.add(resource);
			}
		}
		
		List<Tree> tree = new ArrayList<Tree>();
		for (Resource resource : resourcesRoot) {
			Tree node = new Tree();
			BeanUtils.copyNotNullProperties(resource, node);
			node.setText(resource.getName());
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", resource.getUrl());
			attributes.put("target", resource.getTarget());
			node.setAttributes(attributes);
			tree.add(node);
			node.setChildren(addSubResource(resource,authSet));
		}
		JsonUtil.writeJson(tree,pw);
	}
	
	private List<Tree> addSubResource(Resource resource,Set<String> authSet)
	{
		List<Tree> returnList = new ArrayList<Tree>();
		for (Resource subResource : resource.getResources()){
			if(authSet.contains(subResource.getId()))
			{
				Tree subNode = new Tree();
				BeanUtils.copyNotNullProperties(subResource, subNode);
				subNode.setText(subResource.getName());
				Map<String, String> subAttributes = new HashMap<String, String>();
				subAttributes.put("url", subResource.getUrl());
				subAttributes.put("target", subResource.getTarget());
				subNode.setAttributes(subAttributes);
				returnList.add(subNode);
				returnList.addAll(addSubResource(subResource,authSet));
				
				Collections.sort(returnList, new Comparator<Tree>() {// 排序
					@Override
					public int compare(Tree o1, Tree o2) {
						if (o1.getSeq() == null) {
							o1.setSeq(1000);
						}
						if (o2.getSeq() == null) {
							o2.setSeq(1000);
						}
						return o1.getSeq().compareTo(o2.getSeq());
					}
				});
				
			}
		}
		return returnList;
	}

	
//	/**
//	 * 生成主菜单accordion
//	 */
//	@RequestMapping(value="/getMainMenu_doNotNeedSecurity")
//	public void getMainMenu2(HttpServletRequest request,HttpSession session,PrintWriter pw) {
//		HqlFilter hqlFilter = new HqlFilter(request);
//		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
//		hqlFilter.addFilter("QUERY_user#id_S_EQ", sessionInfo.getUser().getId());
//		hqlFilter.addFilter("QUERY_t#resourcetype#id_S_EQ", "0");// 0就是只查菜单
//		hqlFilter.addFilter("QUERY_t#resource_O_IS", "null");
//		List<Resource> resources = ((ResourceServiceI) service).getMainMenu(hqlFilter);
//		List<Tree> tree = new ArrayList<Tree>();
//		for (Resource resource : resources) {
//			Tree node = new Tree();
//			BeanUtils.copyNotNullProperties(resource, node);
//			node.setText(resource.getName());
//			Map<String, String> attributes = new HashMap<String, String>();
//			attributes.put("url", resource.getUrl());
//			attributes.put("target", resource.getTarget());
//			node.setAttributes(attributes);
//			tree.add(node);
//			node.setChildren(new ArrayList<Tree>());
//			for (Resource subResource : resource.getResources()){
//				Tree subNode = new Tree();
//				BeanUtils.copyNotNullProperties(subResource, subNode);
//				subNode.setText(subResource.getName());
//				Map<String, String> subAttributes = new HashMap<String, String>();
//				subAttributes.put("url", subResource.getUrl());
//				subAttributes.put("target", subResource.getTarget());
//				subNode.setAttributes(subAttributes);
//				node.getChildren().add(subNode);
//			}
//		}
//		JsonUtil.writeJson(tree,pw);
//	}

	/**
	 * 获得资源treeGrid
	 */
	@Override
	@RequestMapping(value="/treeGrid")
	public void treeGrid(HttpServletRequest request,HttpServletResponse response,HttpSession session,PrintWriter pw) {
		HqlFilter hqlFilter = new HqlFilter(request);
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		hqlFilter.addFilter("QUERY_user#id_S_EQ", sessionInfo.getUser().getId());
		JsonUtil.writeJson(((ResourceServiceI) service).resourceTreeGrid(hqlFilter),pw);
	}

	/**
	 * 获得角色的资源列表
	 */
	@RequestMapping(value="/getRoleResources_doNotNeedSecurity")
	public void getRoleResources(String id,HttpServletRequest request,HttpSession session,PrintWriter pw) {
		HqlFilter hqlFilter = new HqlFilter(request);
		hqlFilter.addFilter("QUERY_role#id_S_EQ", id);
		JsonUtil.writeJson(((ResourceServiceI) service).findResourceByFilter(hqlFilter),pw);
	}

	/**
	 * 获得机构的资源列表
	 */
	@RequestMapping(value="/getOrganizationResources_doNotNeedSecurity")
	public void getOrganizationResources(String id,HttpServletRequest request,PrintWriter pw) {
		HqlFilter hqlFilter = new HqlFilter(request);
		hqlFilter.addFilter("QUERY_organization#id_S_EQ", id);
		JsonUtil.writeJson(((ResourceServiceI) service).findResourceByFilter(hqlFilter),pw);
	}

	/**
	 * 获得资源树
	 */
	@RequestMapping(value="/getResourcesTree_doNotNeedSecurity")
	public void getResourcesTree(HttpServletRequest request,HttpServletResponse response,HttpSession session,PrintWriter pw) {
		treeGrid(request,response,session,pw);
	}

	/**
	 * 保存一个资源
	 */
	@Override
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public void save(Resource data,HttpServletRequest request,HttpServletResponse response,HttpSession session,PrintWriter pw) {
		Json json = new Json();
		if (data != null) {
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
			((ResourceServiceI) service).saveResource(data, sessionInfo.getUser().getId());
			json.setSuccess(true);
		}
		JsonUtil.writeJson(json,pw);
	}
}
