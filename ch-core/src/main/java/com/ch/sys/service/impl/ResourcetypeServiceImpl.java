package com.ch.sys.service.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ch.base.service.impl.BaseServiceImpl;
import com.ch.sys.model.Resourcetype;
import com.ch.sys.service.ResourcetypeServiceI;

/**
 * 资源类型业务逻辑
 * 
 * 
 */
@Service
public class ResourcetypeServiceImpl extends BaseServiceImpl<Resourcetype> implements ResourcetypeServiceI {

	/**
	 * 为列表添加了缓存，查询一次过后，只要不重启服务，缓存一直存在，不需要再查询数据库了，节省了一些资源
	 * 
	 * 在ehcache.xml里面需要有对应的value
	 * 
	 * <cache name="ResourcetypeServiceCache"
	 * 
	 * key是自己设定的一个ID，用来标识缓存
	 */
	@Override
	@Cacheable(value = "ResourcetypeServiceCache", key = "'ResourcetypeList'")
	public List<Resourcetype> findResourcetype() {
		return find();
	}

}
