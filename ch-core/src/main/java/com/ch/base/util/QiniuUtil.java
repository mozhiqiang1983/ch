/**
 * 
 */
package com.ch.base.util;

/**
 * @author mozhiqiang
 *
 */
public class QiniuUtil {
	
	public static String getDomain()
	{
		return ConfigUtil.get("qiniu.domain");
	}
	
	public static String getAccessKey()
	{
		return ConfigUtil.get("qiniu.access_key");
	}
	
	public static String getSecretKey()
	{
		return ConfigUtil.get("qiniu.secret_key");
	}
	
	public static String getBucketName()
	{
		return ConfigUtil.get("qiniu.bucket_name");
	}

}
