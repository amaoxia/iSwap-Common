package com.common.utils.common;

/**
 * 对数据的类型转换的基础方法
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午07:53:12
 *@Team 研发中心
 */
public class ConvertUtil {

	/**
	 * 将对象转化为long型
	 *@author huwanshan
	 *@date  2010-12-11 下午07:53:52
	 *@param obj
	 *@param defaultValue
	 *@return
	 */
	public static long asLong(Object obj, long defaultValue) {
		if(obj == null) {
			return defaultValue;
		}
		if(obj instanceof Number) {
			return ((Number)obj).longValue();
		}
		try {
			return Long.parseLong(String.valueOf(obj));
		} catch(Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 将对象转化为int型
	 *@author huwanshan
	 *@date  2010-12-11 下午07:54:03
	 *@param obj
	 *@param defaultValue
	 *@return
	 */
	public static int asInt(Object obj, int defaultValue) {
		if(obj == null) {
			return defaultValue;
		}
		if(obj instanceof Number) {
			return ((Number)obj).intValue();
		}
		try {
			return Integer.parseInt(String.valueOf(obj));
		} catch(Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 将对象转化为double型
	 *@author huwanshan
	 *@date  2010-12-11 下午07:54:12
	 *@param obj
	 *@param defaultValue
	 *@return
	 */
	public static double asDouble(Object obj, double defaultValue) {
		if(obj == null) {
			return defaultValue;
		}
		if(obj instanceof Number) {
			return ((Number)obj).doubleValue();
		}
		try {
			return Double.parseDouble(String.valueOf(obj));
		} catch(Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 将对象转化为float型
	 *@author huwanshan
	 *@date  2010-12-11 下午07:54:20
	 *@param obj
	 *@param defaultValue
	 *@return
	 */
	public static float asFloat(Object obj, float defaultValue) {
		if(obj == null) {
			return defaultValue;
		}
		if(obj instanceof Number) {
			return ((Number)obj).floatValue();
		}
		try {
			return Float.parseFloat(String.valueOf(obj));
		} catch(Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 将对象转化为String型
	 *@author huwanshan
	 *@date  2010-12-11 下午07:54:28
	 *@param obj
	 *@param defaultValue
	 *@return
	 */
	public static String asString(Object obj, String defaultValue) {
		if(obj == null) {
			return defaultValue;
		}
		return obj.toString();
	}
}
