package com.common.framework.web.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对Web操作
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap BI V1.0
 *@date  2010-12-7 下午04:09:01
 *@Team 研发中心
 */
public class WebUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
	
	//header 常量定义
	private static final String ENCODING_PREFIX = "encoding:";
	private static final String NOCACHE_PREFIX = "no-cache:";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	/**
	 * 直接输出内容的简便函数.
	 * eg.
	 * render("text/plain", "hello", "encoding:GBK");
	 * render("text/plain", "hello", "no-cache:false");
	 * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
	 * 
	 * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.                 
	 */
	public static void render(HttpServletResponse response, String contentType, String content, String... headers) {
		try {
			//分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}

			//设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}

			response.getWriter().write(content);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 直接输出文本.
	 * @see #render(String, String, String...)
	 */
	public static void renderText(HttpServletResponse response, String text, String... headers) {
		render(response, "text/plain", text, headers);
	}

	/**
	 * 直接输出HTML.
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(HttpServletResponse response, String html, String... headers) {
		render(response, "text/html", html, headers);
	}
	
	/**
	 * 直接输出XML.
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(HttpServletResponse response, String xml, String... headers) {
		render(response, "text/xml", xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param string json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(HttpServletResponse response, String string, String... headers) {
		render(response, "application/json", string, headers);
	}
	
	/**
	 * 从request中获取参数Map
	 * @param request request上下文
	 * @param prefix 请求前缀
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getParamMap(HttpServletRequest request, String prefix){ 
		Enumeration paramNames = request.getParameterNames();
        Map<String,Object> params = new HashMap<String,Object>();
        if (prefix == null) {
			prefix = "";
		}
        while (paramNames != null && paramNames.hasMoreElements()) {
        	String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values != null && values.length != 0) {
					if (values.length > 1) {
						params.put(unprefixed, values);
					}else{
						params.put(unprefixed, values[0]);
					}
				}
			}
        }
		return params;
	}
	
	/**
	 * 获取servlet上下文路径中，指定路径的物理实际路径
	 * @param context servlet上下文
	 * @param _path 指定路径
	 * @return  物理实际路径
	 */
	public static String getRealPath(ServletContext context,String _path){
    	String _str = context.getRealPath(_path).replace('\\', '/');
    	return _str;
    }
	
	/**
	 * 根据servlet路径读取文件的内容
	 * @param context
	 * @param path
	 * @return
	 */
	public static String getFileContent(ServletContext context, String path){
        try {
			InputStream is = new BufferedInputStream(new FileInputStream(new File(getRealPath(context,path))));
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			StringBuffer lines = new StringBuffer("");
	        String line = null;
	        while((line = br.readLine()) != null){
	        	lines.append(line + "\n");
	        }
	        return lines.toString();
		} catch (IOException e) {
			logger.error(e.getMessage(), e.getCause());
			return null;
		}
    }

	/**
     * 取COOKIE中指定cookie名的值，从COOKIE中获取值
     */
	 public static String getValueByCookieName(HttpServletRequest request, String cookieName){
	        Cookie[] cookies = request.getCookies();
	        if (cookies != null){
	            for(Cookie cookie: cookies){
	                if (cookie.getName().equalsIgnoreCase(cookieName)){
	                    return cookie.getValue();
	                }
	            }
	        }
	        return null;
	 }
}
