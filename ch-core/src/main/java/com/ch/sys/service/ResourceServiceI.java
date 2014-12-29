package com.ch.sys.service;

import java.util.List;

import com.ch.base.service.BaseServiceI;
import com.ch.base.util.HqlFilter;
import com.ch.sys.model.Resource;

/**
 * 资源业务逻辑
 * 
 * 
 */
public interface ResourceServiceI extends BaseServiceI<Resource> {

	/**
	 * 获得资源树，或者combotree(只查找菜单类型的节点)
	 * 
	 * @return
	 */
	public List<Resource> getMainMenu(HqlFilter hqlFilter);

	/**
	 * 获得资源treeGrid
	 * 
	 * @return
	 */
	public List<Resource> resourceTreeGrid(HqlFilter hqlFilter);

	/**
	 * 更新资源
	 */
	public void updateResource(Resource resource);

	/**
	 * 保存一个资源
	 * 
	 * @param resource
	 * @param userId
	 * @return
	 */
	public void saveResource(Resource resource, String userId);

	/**
	 * 查找符合条件的资源
	 */
	public List<Resource> findResourceByFilter(HqlFilter hqlFilter);

}
