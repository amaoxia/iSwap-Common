package com.common.utils.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LongConverter implements Converter {
    private static Log logger = LogFactory.getLog(DateConverter.class);

    
    public Long convert(String value, String paramter) {
    	
    	if(value == null||"".equals(value)) {
    		return null;
    	}
    	return Long.valueOf(value.trim());
       
    }
}
