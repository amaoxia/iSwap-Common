package com.common.jmstool;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JMSConntect implements java.io.Serializable{
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7714911687647721653L;
	private final Log log = LogFactory.getLog(this.getClass());
	private Session session;
	private MessageProducer queueSender;
	private Connection conn;
	private MessageConsumer queueReceiver;
	public MessageProducer getQueueSender() {
		return queueSender;
	}
	public void setQueueSender(MessageProducer queueSender) {
		this.queueSender = queueSender;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Connection getConn() {
		return conn;
	}
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	public MessageConsumer getQueueReceiver() {
		return queueReceiver;
	}
	public void setQueueReceiver(MessageConsumer queueReceiver) {
		this.queueReceiver = queueReceiver;
	}
	public void close(){
		try {
			if(this.conn!=null){
				this.session.close();
				this.conn.close();
			}
		} catch (JMSException e) {
			log.error("JMS的连接关闭失败！", e);
		}
	
	}
	
}
