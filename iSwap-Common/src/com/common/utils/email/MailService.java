package com.common.utils.email;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public interface MailService {

	/**
	 * 简单的发邮件方式( 支持多个用户批量发送)    邮件内容只有标题和邮件内容  
	 * @param subject   邮件标题
	 * @param contents  邮件内容
	 * @param userEmailAddress   收入人的邮件地址  为数组形式
	 * @param fromEmailAddress  发送人的邮箱地址
	 */
	public void sendSimpleEmail(String subject, String contents,
			String[] userEmailAddress, String fromEmailAddress);

	/**
	 * 简单的发邮件方式( 支持多个用户批量发送)
	 * @param simpleEmail   SimpleEmail 邮件内容只有标题和邮件内容
	 * @param userEmailAddress   收入人的邮件地址  为数组形式
	 * @param fromEmailAddress  发送人的邮箱地址
	 */
	public void sendSimpleEmail(SimpleEmail simpleEmail,
			String[] userEmailAddress, String fromEmailAddress);
	
	/**
	 * 发送带附件的邮件方式  邮件内容有标题和邮件内容和附件，附件可以是本地机器上的文本，也可以是web上的一个URL 文件， 
	 * 当为web上的一个URL文件时，此方法可以将WEB中的URL文件先下载到本地，再发送给收入用户
	 * @param subject   邮件标题
	 * @param contents  邮件内容
	 * @param userEmailAddress   收入人的邮件地址  为数组形式
	 * @param fromEmailAddress  发送人的邮箱地址
	 * @param multiPaths         附件地址         为数组形式
	 */
	public void sendMultiPartEmail(String subject, String contents,
			String[] userEmailAddress, String fromEmailAddress,
			String[] multiPaths);

	/**
	 * 发送带附件的邮件方式 
	 * @param multiPartEmail  带附件的邮件
	 * @param userEmailAddress  收入人的邮件地址  为数组形式
	 * @param fromEmailAddress  发送人的邮箱地址
	 */
	public void sendMultiPartEmail(MultiPartEmail multiPartEmail,
			String[] userEmailAddress, String fromEmailAddress);
	/**
	 * 发送Html格式的邮件
	 * @param subject   邮件标题
	 * @param contents  邮件内容
	 * @param userEmailAddress  接收用户的邮箱地址
	 * @param fromEmailAddress  发送人的邮箱地址
	 */
	public void sendHtmlEmail(String subject, String contents,
			String[] userEmailAddress, String fromEmailAddress);
	
	/**
	 * 发送Html格式的邮件
	 * @param htmlEmail html邮件
	 * @param userEmailAddress  接收用户的邮箱地址
	 * @param fromEmailAddress  发送人的邮箱地址
	 */
	public void sendHtmlEmail(HtmlEmail htmlEmail,
			String[] userEmailAddress, String fromEmailAddress);
}
