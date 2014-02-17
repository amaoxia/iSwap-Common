package com.common.utils.email;

import com.common.utils.properties.PropertiesSupport;



public class EmailConfig extends PropertiesSupport {

	static {
		init("config/framework/email/mail.properties");
	}

	public static final String MAIL_HOSTNAME = getProperty("mailHostName", "mail.zhongguancun.com.cn");
	public static final String MAIL_HOSTUSERNAME = getProperty("mailHostUserName", "tanhc");
	public static final String MAIL_HOSTUSERPASSWORD = getProperty("mailHostPassWord", "123ABC");
	public static final String MAIL_QUEUENAME = getProperty("mailQueueName", "tfwMailQueue");

}
