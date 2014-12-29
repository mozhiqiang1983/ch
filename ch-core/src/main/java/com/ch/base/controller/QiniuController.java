/**
 * 
 */
package com.ch.base.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ch.base.util.JsonUtil;
import com.ch.base.util.QiniuUtil;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.net.CallRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;

/**
 * @author mozhiqiang
 *
 */
@Controller
@RequestMapping("/qiniu")
public class QiniuController {
	
	/*
	 * 获取上传凭证
	 */
	@RequestMapping(value="/uptoken")
	public void getUptoken(PrintWriter pw)  throws Exception
	{
		Config.ACCESS_KEY = QiniuUtil.getAccessKey();
		Config.SECRET_KEY = QiniuUtil.getSecretKey();
		String bucketName = QiniuUtil.getBucketName();
		Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		PutPolicy putPolicy = new PutPolicy(bucketName);
		String uptoken = putPolicy.token(mac);
		
		Map<String,String> json = new HashMap<String,String>();
		json.put("uptoken", uptoken);
		JsonUtil.writeJson(json,pw);
	}
	
	/*
	 * 删除文件
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public void delete(String key,PrintWriter pw)
	{
		Config.ACCESS_KEY = QiniuUtil.getAccessKey();
		Config.SECRET_KEY = QiniuUtil.getSecretKey();
	    Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
	    RSClient client = new RSClient(mac);
	    CallRet result = client.delete(QiniuUtil.getBucketName(), key);
	    Map<String,String> json = new HashMap<String,String>();
	    if(result.getStatusCode()==200)
	    {
	    	json.put("success", "true");
	    	json.put("msg", "ok");
	    }
	    else
	    {
	    	json.put("success", "false");
	    	json.put("msg", result.getResponse());
	    }
		
		JsonUtil.writeJson(json,pw);
	}
	
	 

}
