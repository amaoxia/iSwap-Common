package com.common.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertiesSupport {

	protected static Logger logger = LoggerFactory.getLogger(PropertiesSupport.class);
    protected static Properties p = new Properties();
    
    protected static void init(String propertyFileName) {
        InputStream in = null;
        try {
        	//从应用的classpath中找配置文件
        	in = PropertiesSupport.class.getClassLoader().getResourceAsStream(propertyFileName);
        	if(in!=null){
        		p.load(in);
        	}else{
        		logger.error("load " + propertyFileName + " file error!");
        	}
        } catch (IOException e) {
            logger.error("load " + propertyFileName + " file error!");
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close " + propertyFileName + " file error!");
                }
            }
        }
    }

    protected static String getProperty(String key){
    	return p.getProperty(key);
    }
    
    protected static String getProperty(String key, String defaultValue) {
        return p.getProperty(key, defaultValue);
    }
    
}
