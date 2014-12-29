package com.ch.base.service.mail;

import org.apache.commons.mail.EmailException;

public interface MailServiceI {
	
	public void sendMail(String to,String subject,String context) throws EmailException;

}
