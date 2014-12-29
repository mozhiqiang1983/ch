package com.ch.sys.service;

import java.util.List;

import com.ch.base.service.BaseServiceI;
import com.ch.sys.model.Resourcetype;

/**
 * 资源类型业务
 * 
 */
public interface ResourcetypeServiceI extends BaseServiceI<Resourcetype> {

	/**
	 * 获取所有资源类型
	 */
	public List<Resourcetype> findResourcetype();

}
