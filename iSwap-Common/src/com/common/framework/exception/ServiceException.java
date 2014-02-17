
package com.common.framework.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 获取service的异常
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午09:25:33
 *@Team 研发中心
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -8582869876478864758L;
	private Throwable nestedThrowable = null;

    public ServiceException() {
        super();
    }
    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Throwable nestedThrowable) {
        this.nestedThrowable = nestedThrowable;
    }

    public ServiceException(String msg, Throwable nestedThrowable) {
        super(msg);
        this.nestedThrowable = nestedThrowable;
    }

    public void printStackTrace() {
        super.printStackTrace();
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace();
        }
    }

    public void printStackTrace(PrintStream ps) {
        super.printStackTrace(ps);
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace(ps);
        }
    }

    public void printStackTrace(PrintWriter pw) {
        super.printStackTrace(pw);
        if (nestedThrowable != null) {
            nestedThrowable.printStackTrace(pw);
        }
    }

}
