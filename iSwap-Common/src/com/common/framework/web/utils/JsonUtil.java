package com.common.framework.web.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.utils.json.JsonHelper;

import net.sf.json.JSONObject;
/**
 * 对页面json表达式输出
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-9 上午11:31:21
 *@Team 研发中心
 */
public class JsonUtil {

	/**
	 * 输出包含实体对象列表的JSON字符,为grid提供数据
	 * @param msg 消息串
	 * @param datas 记录列表
	 * @param totalSize 总记录数
	 * @param dateFormat 日期格式
	 * @param exculdeFlag 为排除还是包含
	 * @param attributes 属性列表
	 * @return
	 */
	public static String jsonList(String msg, Object datas,long totalSize, String dateFormat, boolean exculdeFlag, List<String> attributes){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("total", totalSize);
		jsonObject.put("datas", JsonHelper.toJsonString(datas, dateFormat, exculdeFlag, attributes));
		return jsonObject.toString();
	}
	
	public static String jsonList(String msg, Object datas,long totalSize, boolean exculdeFlag, List<String> attributes){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("total", totalSize);
		jsonObject.put("datas", JsonHelper.toJsonString(datas, "", exculdeFlag, attributes));
		return jsonObject.toString();
	}
	
	public static String jsonList(String msg, Object datas,long totalSize, String dateFormat){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("total", totalSize);
		jsonObject.put("datas", JsonHelper.toJsonString(datas, dateFormat));
		return jsonObject.toString();
	}
	
    public static String jsonList(String msg, Object datas,long totalSize){
    	JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("total", totalSize);
		jsonObject.put("datas", JsonHelper.toJsonString(datas));
		return jsonObject.toString();
	}
	
    /**
	 * 输出包含实体对象的JSON字符
	 * @param msg 消息串 
	 * @param object 实体对象
	 * @param dateFormat 日期格式
	 * @param exculdeFlag 为排除还是包含
	 * @param attributes 属性列表
	 * @return
	 */
	public static String jsonObject(String msg, Object object, String dateFormat, boolean exculdeFlag, List<String> attributes){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("item", JsonHelper.toJsonString(JsonHelper.toJsonString(object, dateFormat, exculdeFlag, attributes)));
		return jsonObject.toString();
	}

	public static String jsonObject(String msg, Object object, boolean exculdeFlag, List<String> attributes){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("item", JsonHelper.toJsonString(JsonHelper.toJsonString(object, exculdeFlag, attributes)));
		return jsonObject.toString();
	}
	
	public static String jsonObject(String msg, Object object,String dateFormat){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("item", JsonHelper.toJsonString(JsonHelper.toJsonString(object,dateFormat)));
		return jsonObject.toString();	
	}
	
	public static String jsonObject(String msg, Object object){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", true);
		jsonObject.put("msg", msg);
		jsonObject.put("item", JsonHelper.toJsonString(object));
		return jsonObject.toString();	
	}
	
	/**
	 * 输出extjs树形JSON字符 即包含"id","leaf","title"等字段的数组
	 * @param object
	 * @return [{},{},{}]
	 */
	public static String jsonTree(Object object){
		return JsonHelper.toJsonString(object);
	}
	
	public static String jsonObject(Object object){
		return JsonHelper.toJsonString(object);
	}
	
	/**
	 * 输出正确或错误及消息JSON字符
	 * @param msg 消息串
	 * @param success 响应正确或错误标识
	 * @return
	 */
	public static String jsonMsg(String msg, boolean success){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", success);
		map.put("msg", msg);
		return JsonHelper.toJsonString(map);
	}
	
}
