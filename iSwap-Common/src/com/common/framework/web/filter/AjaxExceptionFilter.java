package com.common.framework.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * 异常filter
 * @author tanhc
 *
 */
public class AjaxExceptionFilter extends TfwBaseFilter {
	
	// 在过滤器中检查当前是否在登录状态...
	public void doFilter(HttpServletRequest hreq, HttpServletResponse hres,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(hreq, hres);
		}catch (Exception ex) {//对异常进行分类，如逻辑层，web层校验等
			JSONObject json = new JSONObject();
			json.element("serverError", "服务器业务逻辑执行异常！");
			handleJson(hres,json);
		}
	}

}
