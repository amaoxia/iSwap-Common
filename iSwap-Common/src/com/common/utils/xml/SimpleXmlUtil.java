package com.common.utils.xml;

import java.io.InputStream;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleXmlUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SimpleXmlUtil.class);
	
	public static <E> E readModelFromClassPathXmlFile(Class<E> clazz,String classPathXmlFile){
		try {
			Serializer serializer = new Persister();
			InputStream is = SimpleXmlUtil.class.getClassLoader().getResourceAsStream(classPathXmlFile);
			System.out.println("loading file: "+classPathXmlFile);
			logger.info("loading file {0} from classpath",classPathXmlFile);
			return serializer.read(clazz, is);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
