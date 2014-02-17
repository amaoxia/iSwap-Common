package com.common.utils.json;

import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class JsonHelper {

	/**
	 * 转换对象到json
	 * @param o 待转换对象
	 * @param excludeProperty 要排除的属性
	 * @return
	 */
	private static JSON jsonExclude(Object o, String dateFormat, List<String> excludeProperty) {
		JsonConfig config = new MyJsonConfig(dateFormat);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		// config.setExcludes(excludeProperty);
		
		NamedPropertyFilter filter = new NamedPropertyFilter(excludeProperty);
		filter.setExclude(true);
		// config.setJavaPropertyFilter(filter);
		config.setJsonPropertyFilter(filter);
		JSON json = JSONSerializer.toJSON(o, config);
		return json;
	}

	/**
	 * 转换对象到JOSN
	 * 
	 * @param o
	 * @param includeProperty,要包含的属性
	 * @return
	 */
	private static JSON jsonInclude(Object o, String dateFormat, List<String> includePropertys) {
		JsonConfig config = new MyJsonConfig(dateFormat);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		// config.setExcludes(excludeProperty);
		NamedPropertyFilter filter = new NamedPropertyFilter(includePropertys);// 按名称过滤属性
		filter.setExclude(false);
		// config.setJavaPropertyFilter(filter);
		config.setJsonPropertyFilter(filter);
		JSON json = JSONSerializer.toJSON(o, config);
		return json;
	}
	
	/**
	 * Object => JSON  指定的属性进行JSON转换
	 * @param object 待转换对象
	 * @param flag 标识 true 进行排除操作 false 进行包含操作
	 * @param attributes 需进行转换的属性
	 * @return
	 */
	public static String toJsonString(Object object, String dateFormat, boolean excludeFlag, List<String> attributes){
		if(excludeFlag){
			JSON json = jsonExclude(object, dateFormat, attributes);
			return json.toString();
		}else{
			JSON json = jsonInclude(object, dateFormat, attributes);
			return json.toString();
		}
	}
	
	public static String toJsonString(Object object, boolean excludeFlag, List<String> attributes){
		return toJsonString(object,"",excludeFlag,attributes);
	}
	
	public static String toJsonString(Object object, String dateFormat){
		return toJsonString(object,dateFormat,true,null);
	}
	
	public static String toJsonString(Object object){
		return toJsonString(object,"",true,null);
	}
	
}
