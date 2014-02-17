package com.common.utils.email;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.utils.queue.QueueHolder;


public class MailServiceImpl implements MailService {

	protected static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	/* (non-Javadoc)
	 * @see tfw.utils.email.MailService#sendHtmlEmail(java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	public void sendHtmlEmail(String subject, String contents, String[] userEmailAddress, String fromEmailAddress) {
		HtmlEmail email = new HtmlEmail();
		setEmailCommon(email, userEmailAddress, fromEmailAddress);
		email.setSubject(subject);
		try {
			email.setHtmlMsg(contents);
			email.setTextMsg(contents);
		} catch (EmailException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		putMailInQueue(email);
	}

	public void sendHtmlEmail(HtmlEmail htmlEmail, String[] userEmailAddress, String fromEmailAddress) {
		setEmailCommon(htmlEmail, userEmailAddress, fromEmailAddress);
		putMailInQueue(htmlEmail);
	}

	public void sendMultiPartEmail(String subject, String contents, String[] userEmailAddress, String fromEmailAddress,
			String[] multiPaths) {
		List<EmailAttachment> list = new ArrayList<EmailAttachment>();
		for (int j = 0; j < multiPaths.length; j++) {
			EmailAttachment attachment = new EmailAttachment();
			if (multiPaths[j].indexOf("http") == -1) {
				// 判断当前这个文件路径是否在本地,如果是：setPath 否则setURL;
				attachment.setPath(multiPaths[j]);
			} else {
				try {
					attachment.setURL(new URL(multiPaths[j]));
				} catch (MalformedURLException e) {
					logger.error(e.getMessage(), e);
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("tfw attachment description");
			list.add(attachment);
		}
		// 发送邮件信息
		MultiPartEmail email = new MultiPartEmail();
		setEmailCommon(email, userEmailAddress, fromEmailAddress);
		email.setSubject(subject);
		try {
			email.setMsg(contents);
			//注意这个不要使用setContent这个方法 setMsg不会出现乱码
		} catch (EmailException e1) {
			logger.error(e1.getMessage(), e1);
			throw new RuntimeException(e1.getMessage(), e1);
		}
		for (EmailAttachment attachment : list) // 添加多个附件
		{
			try {
				email.attach(attachment);
			} catch (EmailException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		putMailInQueue(email);
	}

	public void sendMultiPartEmail(MultiPartEmail multiPartEmail, String[] userEmailAddress, String fromEmailAddress) {
		setEmailCommon(multiPartEmail, userEmailAddress, fromEmailAddress);
		putMailInQueue(multiPartEmail);
	}

	public void sendSimpleEmail(String subject, String contents, String[] userEmailAddress, String fromEmailAddress) {
		SimpleEmail email = new SimpleEmail();
		setEmailCommon(email, userEmailAddress, fromEmailAddress);
		email.setSubject(subject);
		email.setContent(contents, "text/plain;charset=GBK");
		putMailInQueue(email);
	}

	public void sendSimpleEmail(SimpleEmail simpleEmail, String[] userEmailAddress, String fromEmailAddress) {
		setEmailCommon(simpleEmail, userEmailAddress, fromEmailAddress);
		putMailInQueue(simpleEmail);
	}

	protected void setEmailCommon(Email email, String[] userEmailAddress, String fromEmailAddress) {
		email.setHostName(EmailConfig.MAIL_HOSTNAME);
		email.setAuthentication(EmailConfig.MAIL_HOSTUSERNAME, EmailConfig.MAIL_HOSTUSERPASSWORD);
		// 发送给多个人
		for (int i = 0; i < userEmailAddress.length; i++) {
			try {
				email.addTo(userEmailAddress[i], userEmailAddress[i]);
			} catch (EmailException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		try {
			email.setFrom(fromEmailAddress, fromEmailAddress);
		} catch (EmailException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected void putMailInQueue(Email email) {
		if (queue == null) {
			queue = QueueHolder.getQueue(EmailConfig.MAIL_QUEUENAME);
		}

		boolean sucess = queue.offer(email);

		if (sucess) {
			if (logger.isDebugEnabled()) {
				logger.debug("put mail ,{}", "主题：" + email.getSubject() + " From: " + email.getFromAddress());
			}
		} else {
			logger.error("Put mail to queue fail ,{}", "主题：" + email.getSubject() + " From: " + email.getFromAddress());
		}
	}

	protected BlockingQueue<Email> queue;
}
