package com.common.framework.dao;

import java.util.List;

import javax.persistence.Query;

import com.common.framework.dao.support.QueryParaUtils;

/**
 * 基于hibernate的hql进行查询
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午09:29:20
 *@Team 研发中心
 */
public class JpaQueryHelper extends BaseQueryHelper {

	public static void putValueInQuery(Query query, List<QueryPara> queryParas) {
		if (queryParas != null && queryParas.size() > 0) {
			for (QueryPara queryPara : queryParas) {
				if (queryPara.getOp().equalsIgnoreCase(Constants.OP_IN)) {//do nothing with jpa1.0 not support in
					//
				} else {
					query.setParameter(QueryParaUtils
							.getQueryParaNamedStr(queryPara), QueryParaUtils
							.getQueryParaValueObject(queryPara));
				}
			}
		}
	}
}
