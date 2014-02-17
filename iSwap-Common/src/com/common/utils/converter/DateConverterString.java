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
 * @since   Feb 18, 2011 11:25:47 AM
 * @name    com.common.utils.converter.DateConverterString.java
 * @version 1.0
 */
public class DateConverterString implements Converter {
    private static Log logger = LogFactory.getLog(DateConverterString.class);

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    public String convert(String value, String paramter) {
        if(value.indexOf("/")!=-1){
            value=value.replaceAll("/", "-");
        } 
        try {
            Date dt = sdf2.parse(value);            
            String str = loadDateFormat(paramter).format(dt); 
            return str;
        } catch (ParseException e) {
            logger.error("日期转化字符串失败！:"+value);
            throw new RuntimeException("日期转化字符串失败!",e);
        }    
    }
    private DateFormat loadDateFormat(String param){
        if(!StringUtils.isBlank(param))
            return new SimpleDateFormat(param);
        return sdf; 
    }
        
    public static void main(String [] args){
        DateConverterString dd=new DateConverterString();
        String d=dd.convert("2001/01/1", "yyyyMMdd");
        System.out.println(d);
    }
}
