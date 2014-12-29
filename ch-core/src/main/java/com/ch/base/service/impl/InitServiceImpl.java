package com.ch.base.service.impl;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ch.base.dao.BaseDaoI;
import com.ch.base.service.InitServiceI;
import com.ch.base.util.MD5Util;
import com.ch.sys.model.Resource;
import com.ch.sys.model.Resourcetype;
import com.ch.sys.model.Role;
import com.ch.sys.model.User;

/**
 * 初始化数据库
 * 
 */
@Service
public class InitServiceImpl implements InitServiceI {

	private static final Logger logger = Logger.getLogger(InitServiceImpl.class);
	
	private static final String FILEPATH = "initDataBase.xml";

	@Autowired
	private BaseDaoI baseDao;

	@Override
	synchronized public void initDb() {
		try {
			Document document = new SAXReader().read(Thread.currentThread().getContextClassLoader().getResourceAsStream(FILEPATH));

			initResourcetype(document);// 初始化资源类型
			logger.info("资源类型初始化完成。");
			initResource(document);// 初始化资源
			logger.info("资源初始化完成。");
			initRole(document);// 初始化角色
			logger.info("角色初始化完成。");
			initRoleResource(document);// 初始化角色和资源的关系
			logger.info("角色和资源关系初始化完成。");
			initUser(document);// 初始化用户
			logger.info("用户初始化完成。");
			initUserRole(document);// 初始化用户和角色的关系
			logger.info("用户和角色关系初始化完成。");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private void initResourcetype(Document document) {
		List childNodes = document.selectNodes("//resourcetypes/resourcetype");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			Resourcetype type = (Resourcetype) baseDao.getById(Resourcetype.class, node.valueOf("@id"));
			if (type == null) {
				type = new Resourcetype();
			}
			type.setId(node.valueOf("@id"));
			type.setName(node.valueOf("@name"));
			type.setDescription(node.valueOf("@description"));
			logger.info(JSON.toJSONStringWithDateFormat(type, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(type);
		}
	}

	private void initResource(Document document) {
		List childNodes = document.selectNodes("//resources/resource");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			Resource resource = (Resource) baseDao.getById(Resource.class, node.valueOf("@id"));
			if (resource == null) {
				resource = new Resource();
			}
			resource.setId(node.valueOf("@id"));
			resource.setName(node.valueOf("@name"));
			resource.setUrl(node.valueOf("@url"));
			resource.setDescription(node.valueOf("@description"));
			resource.setIconCls(node.valueOf("@iconCls"));
			resource.setSeq(Integer.parseInt(node.valueOf("@seq")));

			if (!StringUtils.isBlank(node.valueOf("@target"))) {
				resource.setTarget(node.valueOf("@target"));
			} else {
				resource.setTarget("");
			}

			if (!StringUtils.isBlank(node.valueOf("@pid"))) {
				resource.setResource((Resource) baseDao.getById(Resource.class, node.valueOf("@pid")));
			} else {
				resource.setResource(null);
			}

			Node n = node.selectSingleNode("resourcetype");
			Resourcetype type = (Resourcetype) baseDao.getById(Resourcetype.class, n.valueOf("@id"));
			if (type != null) {
				resource.setResourcetype(type);
			}

			logger.info(JSON.toJSONStringWithDateFormat(resource, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(resource);
		}
	}

	private void initRole(Document document) {
		List childNodes = document.selectNodes("//roles/role");
		for (Object obj : childNodes) {
			Node node = (Node) obj;
			Role role = (Role) baseDao.getById(Role.class, node.valueOf("@id"));
			if (role == null) {
				role = new Role();
			}
			role.setId(node.valueOf("@id"));
			role.setName(node.valueOf("@name"));
			role.setDescription(node.valueOf("@description"));
			role.setSeq(Integer.parseInt(node.valueOf("@seq")));
			logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(role);
		}
	}

	private void initRoleResource(Document document) {
		List<Node> childNodes = document.selectNodes("//roles_resources/role");
		for (Node node : childNodes) {
			Role role = (Role) baseDao.getById(Role.class, node.valueOf("@id"));
			if (role != null) {
				role.setResources(new HashSet());
				List<Node> cNodes = node.selectNodes("resource");
				for (Node n : cNodes) {
					Resource resource = (Resource) baseDao.getById(Resource.class, n.valueOf("@id"));
					if (resource != null) {
						role.getResources().add(resource);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(role);
			}
		}

		Role role = (Role) baseDao.getById(Role.class, "0");// 将角色为0的超级管理员角色，赋予所有权限
		if (role != null) {
			role.getResources().addAll(baseDao.find("from Resource t"));
			logger.info(JSON.toJSONStringWithDateFormat(role, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(role);
		}
	}



	private void initUser(Document document) {
		List<Node> childNodes = document.selectNodes("//users/user");
		for (Node node : childNodes) {
			User user = (User) baseDao.getById(User.class, node.valueOf("@id"));
			if (user == null) {
				user = new User();
			}
			user.setId(node.valueOf("@id"));
			user.setName(node.valueOf("@name"));
			user.setLoginname(node.valueOf("@loginname"));
			user.setPwd(MD5Util.md5(node.valueOf("@pwd")));
			user.setSex(node.valueOf("@sex"));
			user.setAge(Integer.valueOf(node.valueOf("@age")));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			List<User> ul = baseDao.find("from User u where u.loginname = '" + user.getLoginname() + "' and u.id != '" + user.getId() + "'");
			for (User u : ul) {
				baseDao.delete(u);
			}
			baseDao.saveOrUpdate(user);
		}
	}

	private void initUserRole(Document document) {
		List<Node> childNodes = document.selectNodes("//users_roles/user");
		for (Node node : childNodes) {
			User user = (User) baseDao.getById(User.class, node.valueOf("@id"));
			if (user != null) {
				user.setRoles(new HashSet());
				List<Node> cNodes = node.selectNodes("role");
				for (Node n : cNodes) {
					Role role = (Role) baseDao.getById(Role.class, n.valueOf("@id"));
					if (role != null) {
						user.getRoles().add(role);
					}
				}
				logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
				baseDao.saveOrUpdate(user);
			}
		}

		User user = (User) baseDao.getById(User.class, "0");// 用户为0的超级管理员，赋予所有角色
		if (user != null) {
			user.getRoles().addAll(baseDao.find("from Role"));
			logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
			baseDao.saveOrUpdate(user);
		}
	}


}
