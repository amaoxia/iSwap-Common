package com.common.framework.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * 对操作符进行封装
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午09:28:25
 *@Team 研发中心
 */
public class Constants {

	public final static String OP_EQ = "eq";				//"field = value" 等于
	public final static String OP_GE = "ge";				//"field >= value" 大于等于
	public final static String OP_GT = "gt";				//"field > value" 大于
	public final static String OP_LE = "le";				//"field <= value" 小于等于
	public final static String OP_LT = "lt";				//"field < value" 小于
	public final static String OP_NE = "ne";                 //"field <> value" 不等于
	
	public final static String OP_LIKE = "like";			//"field like '%value%'" 
	public final static String OP_LLIKE = "llike";		//"field like 'value%'"
	public final static String OP_RLIKE = "rlike";		//"field like '%value'"
	
	public final static String OP_IN = "in";				//"field in (value1, value2, value3, ...)"
	
	public final static String OP_EQ_VALUE = "=";				
	public final static String OP_GE_VALUE = ">=";				
	public final static String OP_GT_VALUE = ">";				
	public final static String OP_LE_VALUE = "<=";				
	public final static String OP_LT_VALUE = "<";				
	public final static String OP_NE_VALUE = "<>";              
	
	
	public final static String TYPE_STRING = "string";	//参数类型－字符串
	public final static String TYPE_INTEGER = "int";	//参数类型－int数值
	public final static String TYPE_LONG = "long";	    //参数类型－long数值
	public final static String TYPE_FLOAT = "float";	//参数类型－float值
	public final static String TYPE_DOUBLE = "double";	//参数类型－double值
	public final static String TYPE_DATE = "date";		//参数类型－日期
	public final static String TYPE_DATETIME = "datetime";//参数类型－日期时间
	public final static String TYPE_BOOLEAN = "boolean";//参数类型－布尔类型
	
	public final static String ASC = "asc";
    public final static String DESC = "desc";
	
	private static Map<String,String> initOps(){
    	Map<String,String> ops = new HashMap<String,String>();
    	ops.put(OP_EQ, "=");
    	ops.put(OP_GE, ">=");
    	ops.put(OP_GT, ">");
    	ops.put(OP_LE, "<=");
    	ops.put(OP_LT, "<");
    	ops.put(OP_NE, "<>");
    	ops.put(OP_LIKE, " like ");
    	ops.put(OP_LLIKE, " like ");
    	ops.put(OP_RLIKE, " like ");
    	ops.put(OP_IN, " in ");
    	return ops;
    }
	
	private static Map<String,String> initTypes(){
    	Map<String,String> types = new HashMap<String,String>();
    	types.put(TYPE_STRING, "string");
    	types.put(TYPE_INTEGER, "int");
    	types.put(TYPE_LONG, "long");
    	types.put(TYPE_FLOAT, "float");
    	types.put(TYPE_DOUBLE, "double");
    	types.put(TYPE_DATE, "date");
    	types.put(TYPE_DATETIME, "datetime");
    	types.put(TYPE_BOOLEAN, "boolean");
    	return types;
    }
	
	private static Map<String,String> initSort(){ 
    	Map<String,String> sortStr = new HashMap<String,String>();
    	sortStr.put(ASC, "asc");
    	sortStr.put(DESC, "desc");
    	return sortStr;
    }
	
	private static Map<String,Boolean> initBoolean(){
		Map<String,Boolean> booleanMap = new HashMap<String,Boolean>();
		booleanMap.put("true", Boolean.TRUE);
		booleanMap.put("yes", Boolean.TRUE);
		booleanMap.put("on", Boolean.TRUE);
		booleanMap.put("1", Boolean.TRUE);
		booleanMap.put("false", Boolean.FALSE);
		booleanMap.put("no", Boolean.FALSE);
		booleanMap.put("off", Boolean.FALSE);
		booleanMap.put("0", Boolean.FALSE);
    	return booleanMap;
	}
	
	private static String getByKey(Map<String,String> map,String str){
    	for(Map.Entry<String,String> entry : map.entrySet()){
			if(str.equalsIgnoreCase(entry.getKey()))
				return entry.getValue();
		}
		return null;
    }
	
    /**
     * 将操作符号得到其实际的值
     * @param op 操作符名
     * @return 操作符值
     */
    public static String getOpStr(String op) {
		Map<String,String> ops = initOps();
		return getByKey(ops,op);
	}
    
    /**
     * @param type 参数类型名
     * @return 参数类型符值
     */
    public static String getTypeStr(String type){
    	Map<String,String> types = initTypes();
		return getByKey(types,type);
    }
    
    /**
     * @param sort
     * @return 排序类型符值
     */
    public static String getSortStr(String sort){
    	Map<String,String> sorts = initSort();
		return getByKey(sorts,sort);
    }
    
    public static Boolean getBooleanObject(String str){
    	Map<String,Boolean> booleanMap = initBoolean();
    	for(Map.Entry<String,Boolean> entry : booleanMap.entrySet()){
			if(str.equalsIgnoreCase(entry.getKey()))
				return entry.getValue();
		}
		return Boolean.FALSE;
    }
}
