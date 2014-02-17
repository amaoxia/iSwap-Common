package com.common.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *  对于进行了redirect的action，将消息保存在session中，通过该filter进行值的传递
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap BI V1.0
 *@date  2010-12-7 下午04:26:07
 *@Team 研发中心
 */
public class MessageFilter implements Filter {
	
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		// grab messages from the session and put them into request
		// this is so they're not lost in a redirect
		Object message = request.getSession().getAttribute("message");

		if (message != null) {
			request.setAttribute("message", message);
			request.getSession().removeAttribute("message");
		}

		// set the requestURL as a request attribute for templates
		// particularly freemarker, which doesn't allow request.getRequestURL()
		request.setAttribute("requestURL", request.getRequestURL());
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
