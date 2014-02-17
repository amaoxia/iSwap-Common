package com.common.jmstool;

/**
 * 封装JMS操作的属性
 *@Company 北京光码软件有限公司
 *@author hudaowan
 *@version  iSwap V5.0
 *@date  Aug 21, 2008 2:24:14 PM
 *@Team 数据交换平台研发小组
 */
public class JMSAttr implements java.io.Serializable{
	private String initFactory;//初始化工作
	private String url;//连接地址
	private String userName;
	private String passWord;
	private String queFactory;//连接工厂
	private String squeName;//发送消息的队列
	private String rqueName;//接受消息的队列
   
	public JMSAttr(){
		
	}
	public JMSAttr(String initFactory, String url,String userName,String passWord,String queFactory){
		this.initFactory = initFactory;
		this.url = url;
		this.userName = userName;
		this.passWord = passWord;
		this.queFactory = queFactory;
	}
	public String getInitFactory() {
		return initFactory;
	}

	public void setInitFactory(String initFactory) {
		this.initFactory = initFactory;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getQueFactory() {
		return queFactory;
	}

	public void setQueFactory(String queFactory) {
		this.queFactory = queFactory;
	}

	public String getRqueName() {
		return rqueName;
	}

	public void setRqueName(String rqueName) {
		this.rqueName = rqueName;
	}

	public String getSqueName() {
		return squeName;
	}

	public void setSqueName(String squeName) {
		this.squeName = squeName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
