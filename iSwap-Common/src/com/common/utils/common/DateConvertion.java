/*
 * @(#)DateConvertion.java
 *
 * Copyright (C) 2005, zgcworld All right reserved.
 * see the site: http://www.zgcworld.com
 */

package com.common.utils.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 时间转化类
 * @author  lifh
 * @mail    wslfh2005@163.com
 * @since   Feb 28, 2011 5:56:03 PM
 * @name    com.common.utils.common.DateConvertion.java
 * @version 1.0
 */

public class DateConvertion {

    public static final String PATTERN_CN="yyyy-MM-dd";
    
    /**
     * LOG used to output the Log info..
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    public static String dateToString(Date date,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}

