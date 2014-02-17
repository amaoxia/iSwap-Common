package com.common.utils.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.utils.common.StringUtils;

/**
 * 
 * @author  lifh
 * @mail    wslfh2005@163.com
 * @since   Feb 18, 2011 11:22:26 AM
 * @name    com.common.utils.converter.DateConverter.java
 * @version 1.0
 */
public class DateConverter implements Converter {
    private static Log logger = LogFactory.getLog(DateConverter.class);

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    static SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
    
    public java.util.Date convert(String value, String paramter) {
    	
    	if(value == null||"".equals(value)) {
    		return null;
    	}
        if(value.indexOf("/")!=-1){
            value=value.replaceAll("/", "-");
        }
        try {
            Date dt = sdf.parse(value);
            return dt;
        } catch (ParseException e) {
            logger.error("日期转化失败！:"+value);
            throw new RuntimeException("日期转化失败!",e);
        }
    }
    private DateFormat loadDateFormat(String param){
        if(!StringUtils.isBlank(param))
            return new SimpleDateFormat(param);
        return sdf; 
    }
}
