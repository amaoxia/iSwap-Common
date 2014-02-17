package com.common.utils.filter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.josql.Query;
import org.josql.QueryExecutionException;
import org.josql.QueryParseException;
import org.josql.QueryResults;
import org.springframework.util.Assert;

/**
 * 用于对对象过虑，得到符合条件的对象
 *@Company 北京光码软件有限公司
 *@author hudaowan
 *@version  iSwap BI V1.0
 *@date  2010-10-3 下午04:46:48
 *@Team 研发中心
 */
public class JosqlUtil {

	/**
	 * 从集合中过滤出符合条件的子集，
	 * @param <E> 数据类型
	 * @param datas 待过滤的数据
	 * @param josql 过滤语句 例如
	 * <p>“select * from org.TestBean where age>:myage and sex=:mysex and name like :myname order by age asc”</p>
	 * @param params 过滤条件 例如
	 * <p>Map<String,Object> params = new HashMap<String,Object>();
	 * params.put("myage",10);params.put("mysex",true);params.put("myname","tom%");</p>
	 * @return 符合条件的子集
	 */
	@SuppressWarnings("unchecked")
	public static <E> List filter(List<E> datas,String josql, Map<String,Object> params){
		Assert.hasText(josql);
		Query query = new Query();
		try {
			query.parse(josql);
			putValueInQuery(query,params);
			QueryResults qr = query.execute(datas);
			List r = qr.getResults();
			return r;
		} catch (QueryParseException e) {
			e.printStackTrace();
			return null;
		} catch (QueryExecutionException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static void putValueInQuery(Query query,Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			Set<Entry<String, Object>> entries = params.entrySet();
			for (Entry<String, Object> entry : entries) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (StringUtils.isNotBlank(value.toString())){
					query.setVariable(key, value);
				}
			}
		}
	}
}
