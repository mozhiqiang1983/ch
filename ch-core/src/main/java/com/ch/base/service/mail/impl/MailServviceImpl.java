package com.ch.base.service.mail.impl;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ch.base.service.mail.MailServiceI;
import com.ch.base.util.ConfigUtil;

@Service
public class MailServviceImpl implements MailServiceI {

	private static final Logger logger = Logger.getLogger(MailServviceImpl.class);
	
	private static final String host = ConfigUtil.get("commons-mail.smtp.host");
	private static final Integer port = Integer.valueOf(ConfigUtil.get("commons-mail.smtp.port"));
	private static final String username = ConfigUtil.get("commons-mail.username");
	private static final String password = ConfigUtil.get("commons-mail.password");
	private static final String form = ConfigUtil.get("commons-mail.from");
	private static final String personalName = ConfigUtil.get("commons-mail.personalName");
	private static final Boolean reallySend = Boolean.valueOf(ConfigUtil.get("commons-mail.reallySend"));
	private static final Boolean testEnabled = Boolean.valueOf(ConfigUtil.get("commons-mail.test.enabled"));
	private static final String testAddress = ConfigUtil.get("commons-mail.test.toAddress");

	@Override
	public void sendMail(String to, String subject, String content) throws EmailException{
		
		Email email = new SimpleEmail();
		
		email.setHostName(host);
		email.setSmtpPort(port);
		email.setAuthenticator(new DefaultAuthenticator(username, password));
		email.setSSLOnConnect(true);
		email.setFrom(form,personalName);
		email.setSubject(subject);
		email.setMsg(content);
		email.addTo(testEnabled?testAddress:to);
		
		if(reallySend)
		{
			email.send();
		}
		
		logger.info("发送邮件...........start.................");
		logger.info("发送人:"+form+","+personalName);
		logger.info("发送到:"+to);
		logger.info("主题:"+subject);
		logger.info("内容:"+content);
		logger.info("真实发送:"+(reallySend?"是":"否"));
		logger.info("测试模式:"+(testEnabled?"是":"否"));
		logger.info("真实发送地址:"+(testEnabled?testAddress:to));
		logger.info("发送邮件............end.................");

	}

}
