package com.common.hibernate;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 * 自定义LocalSessionFactoryBean
 * @author  lifh
 * @mail    wslfh2005@163.com
 * @since   Jan 13, 2011 10:51:12 AM
 * @name    com.common.utils.hibernate.CustomerSessionFactoryBean.java
 * @version 1.0
 */
public class CustomerSessionFactoryBean extends AnnotationSessionFactoryBean {
	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	protected SessionFactory newSessionFactory(Configuration config)
			throws HibernateException {
		try {
			logger.info("添加动态映射文件dynamic-entity.hbm.xml到Configuration中...");
			String path = this.getClass().getResource("/").getPath()+"dynadomain";
			File f = new File(path);
			logger.info(path);
			config.addDirectory(f);
		} catch (Exception e) {
		    logger.error(e);
			e.printStackTrace();
		}
		logger.info("重建buildSessionFactory...");
		return config.buildSessionFactory();
	}
}