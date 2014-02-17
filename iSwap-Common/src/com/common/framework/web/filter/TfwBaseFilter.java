package com.common.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.framework.help.SpringContextHolder;


public abstract class TfwBaseFilter implements Filter {

	protected static final Logger log = LoggerFactory.getLogger(TfwBaseFilter.class);
	protected ServletContext context;

	protected Object getBean(String beanName) {
		return SpringContextHolder.getBean(beanName);
	}

	protected void handleJson(HttpServletResponse hres, JSONObject json) {
		hres.setCharacterEncoding("utf-8");
		hres.setContentType("application/json");
		try {
			hres.getWriter().write(json.toString());
			log.debug("response output:" + json);
		} catch (IOException e) {
			log.error("export JSON 出错：" + e.getMessage());
			if (log.isErrorEnabled()) {
				log.error("", e);
			}
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest hreq = (HttpServletRequest) req;
		HttpServletResponse hres = (HttpServletResponse) resp;

		doFilter(hreq, hres, chain);
	}

	
	public void destroy() {
	}

	
	public void init(FilterConfig config) throws ServletException {
		this.context = config.getServletContext();
	}

	protected abstract void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException;
}
