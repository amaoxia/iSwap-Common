package com.common.utils.converter;

/**
 * 转化器接口
 * @author  lifh
 * @mail    wslfh2005@163.com
 * @since   Feb 18, 2011 11:17:20 AM
 * @name    com.common.utils.converter.Converter.java
 * @version 1.0
 */
public interface Converter {
	/**
	 * 转化原始值为结果值
	 * @param value	--原始值
	 * @param paramter	--参数
	 * @return	结果值
	 * @author lifh
	 */
    Object convert(String value,String paramter);
}
