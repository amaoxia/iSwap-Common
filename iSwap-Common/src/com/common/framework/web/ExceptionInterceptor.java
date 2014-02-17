/*
 * @(#)ExceptionInterceptor.java
 *
 * Copyright (C) 2005, zgcworld All right reserved.
 * see the site: http://www.zgcworld.com
 */

package com.common.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * @author lifh
 * @mail wslfh2005@163.com
 * @since Mar 4, 2011 2:55:08 PM
 * @name com.common.framework.web.ExceptionInterceptor.java
 * @version 1.0
 */

public class ExceptionInterceptor implements Interceptor {

    /**
     * LOG used to output the Log info..
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result = null;
        try{
            result = invocation.invoke();
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e.getMessage());
//            result = "error"; 
        }
        return result;
    }
}
