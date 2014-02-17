package com.common.framework.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 
 *  功能：提供HQL及SQL的条件拼接功能
 *	目前只支持各个条件的条件"与"and
 *	
 *	在前台页面中支持2种方式的查询：
 *	1 
 *	【示例】
 *   <!--
 *	<input name="conditions[age,int,>=]" value="1">
 *	<input name="conditions[obj.age,int,>=]" value="1">
 *	<input name="conditions[obj.obj1.age,int,>=]" value="1">
 *  <input name="orders[obj.id,,]" value="desc">
 *   -->
 *	【说明】
 *	input框中name的格式写法为“conditions[名称,类型,操作符]”
 *	1）名称 为 “字段名” 或 “对象名.字段名” 或“对象名.别名.字段名”
 *	支持通过别名查询！！！
 *	2）操作符 为eq  ge  gt le  lt ne  like llike rlike in
 *	        或为=   >=  >  <=  <  <>  
 *	3) 类型  支持 string int long float double date boolean
 *	
 *	2【示例】
 *  <!--
 *	<input name="search_age_int_like" value="1">
 *	<input name="order_age" value="asc">
 *  -->
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-12 下午06:16:45
 *@Team 研发中心
 */
public class RequestHelper {

	public static final String CONDITION = "conditions";
	public static final String ORDER = "orders";
	
	public static final String SEARCHPREFIX = "search_";
	public static final String SEARCHSPLIT = "_";
	
	public static final String ORDERPREFIX ="order_";
	
	public static final String PREFIX = "[";
	public static final String SPLIT = ",";
	public static final String SUFFIX = "]";
	
	/**
	 * 获取特定前缀开头的请求参数 ，并且将参数和值转换成Map
	 *@author huwanshan
	 *@date  2010-12-12 下午06:29:28
	 *@param request
	 *@param prefix
	 *@return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getParametersForMap(ServletRequest request, String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map<String,String> params = new TreeMap<String,String>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null) {
					// Do nothing, no values found at all.
				}else if (values.length > 1) {
					params.put(unprefixed, StringArrayToString(values,","));
				}else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}
	
	/**
	 * [e.loginName,string,like]=1   转换成e_loginName=1
	 *@author huwanshan
	 *@date  2010-12-14 下午02:37:22
	 *@return
	 */
	public static Map<String,String> prefixedMapToMap(Map<String,String> prefixedMap){
		Map<String,String> map = new HashMap<String,String>();
		for(Map.Entry<String,String> entryMap:prefixedMap.entrySet()){
			String key = entryMap.getKey();
			String[] keyArry = (key.replace(PREFIX, "")).replace(SUFFIX, "").split(SPLIT);
			String keyStr = keyArry[0].replace(".","_");
			if(keyArry.length>=3){
			    if(null != keyArry[1] && Constants.TYPE_DATE.equals(keyArry[1])){
			        keyStr=keyStr+"_"+keyArry[2];
			    }
			}
			map.put(keyStr, entryMap.getValue());
		}
		return map;
	}
	/**
	 * 获取特定前缀开头的查询条件请求参数为List<QueryPara>，默认是‘search_’开头，以‘_’进行分隔
	 *@author huwanshan
	 *@date  2010-12-12 下午06:31:13
	 *@param request
	 *@return
	 */
	public static List<QueryPara> getParametersWithPrefix(ServletRequest request){
		return getParametersWithPrefix(request,null);
	}

	
	@SuppressWarnings("unchecked")
	private static List<QueryPara> getParametersWithPrefix(ServletRequest request, String[] separators) {
		List<QueryPara> params = new ArrayList<QueryPara>();
		Enumeration paramNames = request.getParameterNames();
		if (separators == null) {
			separators = new String[2];
			separators[0] = SEARCHPREFIX;
			separators[1] = SEARCHSPLIT;
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			String paramValue = StringArrayToString(paramValues,",");
			QueryPara para = convert2QueryParaWithPrefix(paramName,paramValue,separators);
			if(para!=null){
				params.add(para);
			}
		}
		return params;
	}
	
	
	/** 转换指定的参数名及值为QueryPara参数，可以指定【开头字符串】及【分隔符】*/
	private static QueryPara convert2QueryParaWithPrefix(String paramName,String paramValue,String[] separators){
		if(paramValue!=null && StringUtils.isNotBlank(paramValue) && !paramValue.equalsIgnoreCase("ALL")){
			paramValue = paramValue.trim();
			if (paramName.startsWith(separators[0])) {//以【开头字符串】开头
				String unprefixed = paramName.substring(separators[0].length());
				String[] names = StringUtils.split(unprefixed, separators[1]);
				if(names!=null){
			    	if(names.length==1){//仅设置名称
						QueryPara para = new QueryPara(names[0],Constants.TYPE_STRING,Constants.OP_EQ,paramValue);
						return para;
					}else if(names.length==2){//仅设置名称及操作符
						QueryPara para = new QueryPara(names[0],Constants.TYPE_STRING,names[1],paramValue);
						return para;
					}else if(names.length==3){//设置了名称，类型及操作符
						QueryPara para = new QueryPara(names[0],names[1],names[2],paramValue);
						return para;
					}
					return null;
			    }
				return null;
			}
			return null;
		}
		return null;
	}
	
	/**
	 * 获取特定前缀开头的查询条件请求参数为List<QueryPara>，默认是‘condition’开头，以‘,’进行分隔
	 *@author huwanshan
	 *@date  2010-12-12 下午06:31:42
	 *@param request
	 *@return
	 */
	public static List<QueryPara> getParametersWithCondition(ServletRequest request){
		return getParametersWithCondition(request,CONDITION,null);
	}
	
	
	@SuppressWarnings("unchecked")
	private static List<QueryPara> getParametersWithCondition(ServletRequest request,String filterStr,String[] separators){
		if(separators==null){
			separators = new String[3];
			separators[0] = PREFIX;
			separators[1] = SPLIT;
			separators[2] = SUFFIX;
		}
		if(StringUtils.isBlank(filterStr)){
			filterStr = CONDITION;
		}
		Enumeration paramNames = request.getParameterNames();
		List<QueryPara> params = new ArrayList<QueryPara>();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			String paramValue = StringArrayToString(paramValues,",");
			QueryPara para = convert2QueryParaWithCondition(paramName,paramValue,filterStr,separators);
			if(para!=null){
				params.add(para);
			}
		}
		return params;
	}

    private static QueryPara convert2QueryParaWithCondition(String paramName, String paramValue, String filterStr,
                    String[] separators) {
        if (paramName.startsWith(filterStr)) {
            if (StringUtils.isNotBlank(paramValue) && !paramValue.equalsIgnoreCase("ALL")) {
                int start = StringUtils.indexOf(paramName, separators[0]);
                int end = StringUtils.indexOf(paramName, separators[2]);
                if ((start != -1) && (end != -1)) {
                    String exactParamName = StringUtils.substring(paramName, start + 1, end);
                    String[] names = StringUtils.split(exactParamName, separators[1]);
                    if (names != null) {
                        if (names.length == 1) {// 名称，
                            QueryPara para = new QueryPara(names[0], Constants.TYPE_STRING, Constants.OP_EQ_VALUE,
                                            paramValue);
                            return para;
                        } else if (names.length == 2) {// 名称，操作符
                            QueryPara para = new QueryPara(names[0], names[1], Constants.TYPE_STRING, paramValue);
                            return para;
                        } else if (names.length == 3) {// 名称，类型，操作符
                            // 如果是查询景景截止的时间，那么时间加一天。
                            if (names[0].contains("_date") && names[1].equals(Constants.TYPE_DATE)) {
                                paramValue = getAddOneDay(paramValue);
                            }
                            QueryPara para = new QueryPara(names[0], names[1], names[2], paramValue);
                            return para;
                        } else {
                            return null;
                        }
                    }
                    return null;
                }
            }
            return null;
        }
        return null;
    }
    private static String getAddOneDay(String datestr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date nowdate = null;
        try {
            nowdate = sdf.parse(datestr);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(nowdate); // 设置当前日期
        c.add(Calendar.DATE, 1); // 日期加1
        Date date = c.getTime(); // 结果
        return sdf.format(date);

    }
	
	/**
	 * 获取特定前缀开头的查询条件请求参数为List<SortPara>，默认是‘order’开头
	 *@author huwanshan
	 *@date  2010-12-12 下午06:33:18
	 *@param request
	 *@return
	 */
	public static List<SortPara> getOrderParametersWithOrder(ServletRequest request){
		return getOrderParametersWithOrder(request,ORDER,null);
	}
		
	@SuppressWarnings("unchecked")
	private static List<SortPara> getOrderParametersWithOrder(ServletRequest request,String orderStr,String[] separators){
		if(separators==null){
			separators = new String[3];
			separators[0] = PREFIX;
			separators[1] = SPLIT;
			separators[2] = SUFFIX;
		}
		if(StringUtils.isBlank(orderStr)){
			orderStr=ORDER;
		}
		Enumeration paramNames = request.getParameterNames();
		List<SortPara> params = new ArrayList<SortPara>();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			String value = paramValues != null?paramValues[0]:null;
			SortPara para = convert2SortParaWithOrder(paramName,value,orderStr,separators);
			if(para!=null)
				params.add(para);
		}
		return params;
	}
	
	private static SortPara convert2SortParaWithOrder(String paramName,String paramValue,String orderStr, String[] separators){
		if (paramName.startsWith(orderStr)) {
			if (StringUtils.isNotBlank(paramValue)) {
				int start = StringUtils.indexOf(paramName, separators[0]);
				int end = StringUtils.indexOf(paramName, separators[1]);
				if ((start != -1) && (end != -1)) {
					String exactParamName = StringUtils.substring(
							paramName, start + 1, end);
					if (exactParamName != null) {
						SortPara para = new SortPara(exactParamName,paramValue);
						return para;
					}
					return null;
				}
				return null;
			}
			return null;
		}
		return null;
	}

	
	/**
	 * 获取特定前缀开头的查询条件请求参数为List<SortPara>，默认是‘order_’开头，以‘_’进行分隔
	 *@author huwanshan
	 *@date  2010-12-12 下午06:33:51
	 *@param request
	 *@return
	 */
	public static List<SortPara> getOrderParametersWithPrefix(ServletRequest request){
		return getOrderParametersWithPrefix(request,ORDERPREFIX);
	}
	
	@SuppressWarnings("unchecked")
	private static List<SortPara> getOrderParametersWithPrefix(ServletRequest request, String orderPrefix) {
		List<SortPara> params = new ArrayList<SortPara>();
		Enumeration paramNames = request.getParameterNames();
		if(orderPrefix==null ||StringUtils.isBlank(orderPrefix)){
			orderPrefix = ORDERPREFIX;
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			String value = paramValues != null?paramValues[0]:null;
			SortPara para = convert2SortParaWithPrifix(paramName,value,orderPrefix);
			if(para!=null)
				params.add(para);
		}
		return params;
	}
	
	private static SortPara convert2SortParaWithPrifix(String paramName,String paramValue,String orderPrefix){
    	if(StringUtils.isNotBlank(paramValue)){
			if (paramName.startsWith(orderPrefix)) {
				String unprefixed = paramName.substring(orderPrefix.length());
				if(StringUtils.isNotBlank(unprefixed)){
			    	SortPara para = new SortPara(unprefixed,paramValue);
			    	return para;
			    }
				return null;
			}
			return null;
		}
    	return null;
	}

    
	
	
	private static String StringArrayToString(String[] strArray, String splitStr){
		StringBuilder sb = new StringBuilder("");
		if(strArray!=null && strArray.length>0){
			for(String str:strArray){
				if(StringUtils.isNotBlank(str)){
					sb.append(str).append(splitStr);
				}
			}
			if(sb.toString().contains(splitStr))
				return sb.substring(0, sb.length() - splitStr.length());
			return "";
		}else{
			return "";
		}
	}

}
