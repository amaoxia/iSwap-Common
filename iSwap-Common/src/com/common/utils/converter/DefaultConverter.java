package com.common.utils.converter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * @author  lifh
 * @mail    wslfh2005@163.com
 * @since   Feb 18, 2011 11:24:06 AM
 * @name    com.common.utils.converter.DefaultConverter.java
 * @version 1.0
 */
public class DefaultConverter implements Converter {
    private static Log logger = LogFactory.getLog(DefaultConverter.class);

    public String convert(String value, String paramter) {
        logger.debug("使用默认转化器,直接返回值："+value);
        return value;
    }
}
