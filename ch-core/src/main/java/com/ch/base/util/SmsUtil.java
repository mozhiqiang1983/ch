/**
 * 
 */
package com.ch.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * @author mozhiqiang
 *
 */
public class SmsUtil {
	
	public static void main(String[] args)  throws Exception
	{
//		SmsBaseModel m = new SmsModel();
//		m.getParams().put("MOBILE", "13570201461");
//		m.getParams().put("CONTENT", URLEncoder.encode("测试", "GB2312"));	
//
//		System.out.println(SmsUtil.sendSms(m,"gb2312"));
	}
	
	/**
	 * 发短信
	 * @param model 根据供应商的api重写发送类，继承SmsBaseModel
	 * @param resultEncoding 返回码的编码类型，默认UTF-8
	 * @return 返回结果
	 */
	public static String sendSms(SmsBaseModel model,String resultEncoding)
	{
		URL url = null;
	    HttpURLConnection httpurlconnection = null;
	    BufferedInputStream bis = null;
	    BufferedReader br = null;
	    try
	    {
			url = new URL(model.getUrl());
			httpurlconnection = (HttpURLConnection) url.openConnection();
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setRequestMethod("POST");
			httpurlconnection.getOutputStream().write("?a=1".getBytes());
			
			Map<String,String> params = model.getParams();
			for(Iterator<String> ite = params.keySet().iterator();ite.hasNext();)
			{
				String key = ite.next();
				String value = params.get(key);
				StringBuilder param = new StringBuilder("&").append(key).append("=").append(value);
				httpurlconnection.getOutputStream().write(param.toString().getBytes());
			}
			
			httpurlconnection.getOutputStream().flush();
			httpurlconnection.getOutputStream().close();
			
			return IOUtil.inputStreamToString(httpurlconnection.getInputStream(),resultEncoding);

	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	      return null;
	    }
	    finally
	    {
	      if(httpurlconnection!=null)
	        httpurlconnection.disconnect();
	      try{
		      if(bis!=null)
		    	  bis.close();
		      if(br!=null)
		    	  br.close();
	      }
	      catch(IOException ex)
	      {
	    	  ex.printStackTrace();
	    	  return null;
	      }
	    }
	}
	
	abstract public static class SmsBaseModel {
		
		/**
		 * API供应商提供的url地址
		 */
		private String url;
		/**
		 * 参数，键值对形式保存
		 */
		private Map<String,String> params;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
		public Map<String, String> getParams() {
			return params;
		}

		public void setParams(Map<String, String> params) {
			this.params = params;
		}

		/**
		 * 根据返回码获取的文本信息，要根据具体供应商的API文档重写该方法
		 * @param returnCode
		 * @return
		 */
		abstract public String getReturnInfo(Integer returnCode);

	}

}
