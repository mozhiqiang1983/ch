package com.ch.base.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.util.HtmlUtils;

/**
 * 用于防止XSS攻击
 * 
 */
public class EscapeConverter implements Converter<String,String> {

	
	@Override
	public String convert(String content) {
		if(!StringUtils.isBlank(content))
			return HtmlUtils.htmlEscape(content);
		else
			return "";
	}

}
