package com.common.hibernate;
import org.hibernate.SessionFactory;

import com.common.framework.help.SpringContextHolder;

/**
 * 
 * @author  lifh
 * @mail    wslfh2005@163.com
 * @since   Jan 13, 2011 11:08:25 AM
 * @name    com.common.utils.hibernate.SessionFactoryUtil.java
 * @version 1.0
 */
public class SessionFactoryUtil {
	/**
	 * 重新构建sessionfactory
	 * @param springContextHolder
	 * @author lifh
	 */
	public synchronized static void reload(SpringContextHolder springContextHolder) {
		CustomerSessionFactoryBean lsfb = (CustomerSessionFactoryBean) springContextHolder.getApplicationContext().getBean("&sessionFactory");
		try {
			lsfb.afterPropertiesSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SessionFactory getSessionFactory(SpringContextHolder springContextHolder){
	    CustomerSessionFactoryBean lsfb = (CustomerSessionFactoryBean) springContextHolder.getApplicationContext().getBean("&sessionFactory");
	    SessionFactory sessionFactory = lsfb.getObject();
	    return sessionFactory;
	}
	
	
}

