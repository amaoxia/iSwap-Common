package com.common.jmstool;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * jms的工具
 *@Company 北京光码软件有限公司
 *@author hudaowan
 *@version  iSwap V5.0
 *@date  Aug 20, 2008 1:47:11 PM
 *@Team 数据交换平台研发小组
 */
public class JMSTool {
	private final Log log = LogFactory.getLog(this.getClass());
	private InitialContext ctx;
	private Session session;
	private MessageProducer queueSender;
	private MessageConsumer queueReceiver;
	private static JMSTool jmsTool = null;
	
	public synchronized static JMSTool init(){
		if(jmsTool==null){
			jmsTool = new JMSTool();
		}
		return  jmsTool;
	}
	
	
	/**
	 * 测试JMSConnect连接是否成功
	 *@author hudaowan
	 *@date  Aug 25, 2008 5:34:32 PM
	 *@param attr
	 *@return
	 */
	public synchronized boolean isJMSConnect(JMSAttr attr){
		boolean flag = false;
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY, attr.getInitFactory());
		prop.put(Context.PROVIDER_URL, attr.getUrl());
		prop.put(Context.REFERRAL, "throw");
		try {
			ctx = new InitialContext(prop);
			ConnectionFactory qcf = (ConnectionFactory) ctx.lookup(attr.getQueFactory());
			ctx.close();
			if(attr.getUserName()!=null){
				qcf.createConnection(attr.getUserName(),attr.getPassWord());
			}else{
				qcf.createConnection();
			}
			flag = true;
		} catch (NamingException e) {
		    flag = false;
			log.error("创建连接失败！", e);
		} catch (JMSException e) {
			 flag = false;
			log.error("创建连接失败！", e);
		}
		return flag;
	}
	
	/**
	 * 创建JMS的连接
	 *@author hudaowan
	 *@date  Aug 20, 2008 9:19:07 PM
	 *@param queFactory
	 *@param queName
	 *@return
	 */
	public synchronized JMSConntect createSendConnect(JMSAttr attr){
		JMSConntect conn = new JMSConntect();
		Queue sendQueue = null;
		Queue reQueue= null;
		Connection qc = null;
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY, attr.getInitFactory());
		prop.put(Context.PROVIDER_URL, attr.getUrl());
		prop.put(Context.REFERRAL, "throw");
		try {
			ctx = new InitialContext(prop);
			ConnectionFactory qcf = (ConnectionFactory) ctx.lookup(attr.getQueFactory());
			sendQueue = (Queue) ctx.lookup(attr.getSqueName());
			if(attr.getRqueName()!=null){
				 reQueue= (Queue) ctx.lookup(attr.getRqueName());
			}
			
			ctx.close();
			if(attr.getUserName()!=null){
				qc = qcf.createConnection(attr.getUserName(),attr.getPassWord());
			}else{
				qc = qcf.createConnection();
			}
			
		    session = qc.createSession(false,Session.AUTO_ACKNOWLEDGE);
		    queueSender = session.createProducer(sendQueue);
		    if(reQueue!=null){
		    	queueReceiver = session.createConsumer(reQueue);
		    	qc.start();
		    }
		    
		    conn.setQueueSender(queueSender);
		    conn.setSession(session);
		    conn.setQueueReceiver(queueReceiver);
		    conn.setConn(qc);
		} catch (NamingException e) {
			log.error("创建连接失败！", e);
		} catch (JMSException e) {
			log.error("创建连接失败！", e);
		}
		return conn;
	}
	
	/**
	 * 发送同步消息 并有返回值
	 *@author hudaowan
	 *@date  Aug 20, 2008 1:50:46 PM
	 *@param messge
	 *@return
	 * @throws InterruptedException 
	 */
	public synchronized String sendSynch(JMSConntect conn,String messge) {
		String message = null;
		MessageProducer msgProducer = conn.getQueueSender();
		Session Session = conn.getSession();
		MessageConsumer queueReceiver = conn.getQueueReceiver();
		TextMessage msg;
		try {
			msg = Session.createTextMessage();
			msg.setText(messge);
			msgProducer.send(msg);
			Thread.sleep(2000);
			msg = (TextMessage)queueReceiver.receive(2000);
			if(msg==null){
				message = "";
			}else{
				message = msg.getText();
			}
		} catch (JMSException e) {
			message = "error";
			log.error("发生同步消息失败！",e);
		} catch (InterruptedException e) {
			message = "error";
			log.error("发生同步消息失败！",e);
		}
		return message;
	}
	
	/**
	 * 发送异步消息   
	 *@author hudaowan
	 *@date  Aug 20, 2008 1:52:15 PM
	 *@param message
	 *@return
	 */
	public synchronized boolean  sendAsyn(JMSConntect conn,String messge) throws  Exception{
		boolean flag = false;
		MessageProducer msgProducer = conn.getQueueSender();
		Session Session = conn.getSession();
		TextMessage msg;
		try {
			msg = Session.createTextMessage();
			msg.setText(messge);
			msgProducer.send(msg);
			flag = true;
		} catch (JMSException e) {
			flag = false;
			log.error("发送消息失败！");
			throw new Exception(e);
		}
		return flag;
	}
	
	/**
	 * 创建接受队列的连接
	 *@author hudaowan
	 *@date  Aug 20, 2008 10:08:10 PM
	 *@param attr
	 *@return
	 */
	public synchronized JMSConntect createReceiveConntect(JMSAttr attr){
		JMSConntect conn = new JMSConntect();
		Queue reQueue= null;
		Connection qc = null;
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY, attr.getInitFactory());
		prop.put(Context.PROVIDER_URL, attr.getUrl());
		prop.put(Context.REFERRAL, "throw");
		try {
			ctx = new InitialContext(prop);
			ConnectionFactory qcf = (ConnectionFactory) ctx.lookup(attr.getQueFactory());
			reQueue= (Queue) ctx.lookup(attr.getRqueName());
			ctx.close();
			if(attr.getUserName()!=null){
				qc = qcf.createConnection(attr.getUserName(),attr.getPassWord());
			}else{
				qc = qcf.createConnection();
			}
			
		    session = qc.createSession(false,Session.AUTO_ACKNOWLEDGE);
		    queueReceiver = session.createConsumer(reQueue);
		    qc.start();
		    
		    conn.setQueueSender(queueSender);
		    conn.setSession(session);
		    conn.setQueueReceiver(queueReceiver);
		    conn.setConn(qc);
		} catch (NamingException e) {
			log.error("创建连接失败！", e);
		} catch (JMSException e) {
			log.error("创建连接失败！", e);
		}
		return conn;
	}
	
	/**
	 * 开始接受信息
	 *@author hudaowan
	 *@date  Aug 20, 2008 10:08:36 PM
	 *@param queName
	 *@return
	 */
	public synchronized String receiver(JMSConntect conn,long timeout) throws Exception{
		String message = null;
		TextMessage msg = null;
		MessageConsumer queueReceiver = conn.getQueueReceiver();
		try {
			if (timeout !=0) {
				msg = (TextMessage)queueReceiver.receive(timeout*100);
			} else {
				msg = (TextMessage)queueReceiver.receive();
			}
			if(msg==null){
				message = "";
			}else{
				message = msg.getText();
			}
		} catch (JMSException e) {
			log.error("接受消息失败！",e);
			throw new Exception(e);
		}
		return message;
	}
}
