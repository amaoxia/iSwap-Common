package com.common.framework.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.framework.dao.support.QueryParaUtils;


/**
 * 基于hibernate的hql进行查询
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午09:29:01
 *@Team 研发中心
 */
public class JdbcQueryHelper extends BaseQueryHelper {

	/**
	 * 
	 * @param query
	 * @param queryParas
	 */
	public static Map<String,Object> getValueInMap(List<QueryPara> queryParas) {
		Map<String,Object> params = new HashMap<String,Object>();
		if (queryParas != null && queryParas.size() > 0) {
			for (QueryPara queryPara : queryParas) {
				if (queryPara.getOp().equalsIgnoreCase(Constants.OP_IN)) {
					params.put(QueryParaUtils
							.getQueryParaNamedStr(queryPara), QueryParaUtils
							.getQueryParaListValueObject(queryPara));
				} else {
					params.put(QueryParaUtils
							.getQueryParaNamedStr(queryPara), QueryParaUtils
							.getQueryParaValueObject(queryPara));
				}
			}
		}
		return params;
	}
	
}
