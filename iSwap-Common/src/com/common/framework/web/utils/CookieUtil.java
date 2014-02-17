package com.common.framework.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	private CookieUtil(){}
	
	/**
	 * 根据cookie名称获取cookie值
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName){
		return getCookieValue(request,cookieName,null);
	}
	
    /**
     * 根据cookie名称获取cookie值，当cookie值不存在时，返回默认值
     * @param request
     * @param cookieName
     * @param defaultValue
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName,
            String defaultValue) {
        Cookie cookieList[] = request.getCookies();
        if (cookieList == null || cookieName == null)
            return defaultValue;
		try {
			String _cookieName = URLDecoder.decode(cookieName, "UTF8");
			for (int i = 0; i < cookieList.length; i++) {
				if (cookieList[i].getName().equals(_cookieName)) {
					if (cookieList[i].getValue() == null
							|| "null".equals(cookieList[i].getValue())) {
						return null;
					} else {
						return URLDecoder.decode(cookieList[i].getValue(),"UTF8");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return defaultValue;
    }

    /**
     * 往response端种cookie
     * @param response
     * @param cookieName
     * @param cookieValue
     * @throws UnsupportedEncodingException
     */
    public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue)
            throws UnsupportedEncodingException {
    	setCookie(response,cookieName,cookieValue,FIVE_YEAR_AGE,"/");
    }
    
	public static void setCookie(HttpServletResponse response,
			String cookieName, String cookieValue,String path)
			throws UnsupportedEncodingException {
		setCookie(response, cookieName, cookieValue, FIVE_YEAR_AGE, path);
	}
	
    public static void setCookie(HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage) throws UnsupportedEncodingException {
    	setCookie(response,cookieName,cookieValue,cookieMaxage,"/");
    }
    
    /**
     * 往response端种cookie 名称及值多用URLEncoder.encode进行编码
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param cookieMaxage
     * @param path
     * @throws UnsupportedEncodingException
     */
    public static void setCookie(HttpServletResponse response, String cookieName,
            String cookieValue, int cookieMaxage,String path) throws UnsupportedEncodingException {
    	Cookie theCookie = new Cookie(URLEncoder.encode(cookieName, "UTF8"),
                cookieValue==null?null:URLEncoder.encode(cookieValue, "UTF8"));
    	theCookie.setMaxAge(cookieMaxage);
    	theCookie.setPath(path);
    	response.addCookie(theCookie);
    }
    
    private static int FIVE_YEAR_AGE = 60 * 60 * 24 * 365 * 5;
 
}
