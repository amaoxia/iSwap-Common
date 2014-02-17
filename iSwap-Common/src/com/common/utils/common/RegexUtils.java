package com.common.utils.common;

import java.util.regex.Pattern;

/**
 * 对正则表达式的处理方法
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午07:54:54
 *@Team 研发中心
 */
public class RegexUtils {
    
    /**
     * 是否包含数字
     *@author huwanshan
     *@date  2010-12-11 下午07:56:59
     *@param str
     *@return
     */
	public static boolean isInteger(String str) {
		return isMatch("^-?\\d+$", str);
	}

	/**
	 * 是否包含浮点数据
	 *@author huwanshan
	 *@date  2010-12-11 下午07:57:31
	 *@param str
	 *@return
	 */
	public static boolean isFloat(String str) {
		return isMatch("^(-?\\d+)(\\.\\d+)?$", str);
	}

	/**
	 * 指定的字符串是否为正确的日期时间格式
	 *@author huwanshan
	 *@date  2010-12-11 下午07:58:01
	 *@param str
	 *@return
	 */
	public static boolean isDateTime(String str) {
		String patten = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		return isMatch(patten, str);
	}

	public static boolean isDate(String str) {
		String patten = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))$";
		return isMatch(patten, str);
	}

	public static boolean isBoolean(String str) {
		String[] values = { "true", "false", "yes", "no", "on", "off", "1", "0" };
		for (String value : values) {
			if (value.equalsIgnoreCase(str))
				return true;
		}
		return false;
	}
	
	private static boolean isMatch(String ms, String str) {
		Pattern p = Pattern.compile(ms);
		return p.matcher(str).matches();
	}
}

