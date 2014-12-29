/**
 * 
 */
package com.ch.base.util;

import java.io.PrintWriter;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author zhiqiang
 *
 */
public class JsonUtil {

	private static final Logger logger = Logger.getLogger(JsonUtil.class);

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public static void writeJsonByFilter(Object object, String[] includesProperties,
			String[] excludesProperties, PrintWriter pw) {

		FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
		if (excludesProperties != null && excludesProperties.length > 0) {
			filter.getExcludes().addAll(
					Arrays.<String> asList(excludesProperties));
		}
		if (includesProperties != null && includesProperties.length > 0) {
			filter.getIncludes().addAll(
					Arrays.<String> asList(includesProperties));
		}
		logger.info("对象转JSON：要排除的属性[" + excludesProperties + "]要包含的属性["
				+ includesProperties + "]");
		String json;
		// String User_Agent = getRequest().getHeader("User-Agent");
		// if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1) {
		// //
		// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
		// json = JSON.toJSONString(object, filter,
		// SerializerFeature.WriteDateUseDateFormat,
		// SerializerFeature.DisableCircularReferenceDetect,
		// SerializerFeature.BrowserCompatible);
		// } else {
		// // 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd
		// hh24:mi:ss
		// // 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
		// json = JSON.toJSONString(object, filter,
		// SerializerFeature.WriteDateUseDateFormat,
		// SerializerFeature.DisableCircularReferenceDetect);
		// }
		json = JSON.toJSONString(object, filter,
				SerializerFeature.WriteDateUseDateFormat,
				SerializerFeature.DisableCircularReferenceDetect);
		logger.info("转换后的JSON字符串：" + json);
		// getResponse().setContentType("text/html;charset=utf-8");
		pw.write(json);
		pw.flush();
		pw.close();
	}

	public static void writeJson(Object object, PrintWriter pw) {
		writeJsonByFilter(object, null, null, pw);
	}
	
	public static void writeJsonByIncludesProperties(Object object, String[] includesProperties,PrintWriter pw) {
		writeJsonByFilter(object, includesProperties, null,pw);
	}


}
