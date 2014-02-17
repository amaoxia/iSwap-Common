package com.common.framework.dao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.common.framework.dao.support.QueryParaUtils;
import com.common.framework.dao.support.SortParaUtil;


/**
 * 对Hql语句进行处理
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-12 下午04:49:36
 *@Team 研发中心
 */
public class BaseQueryHelper {

	/**
	 * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
	 */
	public static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除hql的orderby 子句，用于pagedQuery.
	 */
	public static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 获取条件语句部分
	 * @param queryParas
	 * @return
	 */
	public static String getQueryWhereStr(List<QueryPara> queryParas) {
		if (queryParas != null && queryParas.size() > 0) {
			StringBuilder sb = new StringBuilder("");
			for (QueryPara queryPara : queryParas) {
				String hql = QueryParaUtils.getConditionStr(queryPara);
				if(StringUtils.isNotBlank(hql)){
					sb.append(hql).append(" and ");
				}
			}
			if(sb.toString().contains("and")){
			   return sb.substring(0, sb.length() - 4);
			}
		}
		return "";
	}

	/**
	 * 获取排序语句部分
	 * @param sortParas
	 * @return
	 */
	public static String getQueryOrderStr(List<SortPara> sortParas) {
		if (sortParas != null && sortParas.size() > 0) {
			StringBuilder sb = new StringBuilder("");
			for (SortPara sortPara : sortParas) {
				String orderStr = SortParaUtil.getOrderStr(sortPara);
				if(StringUtils.isNotBlank(orderStr))
					sb.append(orderStr).append(" , ");
			}
			if(sb.toString().contains(" , ")){
				return sb.substring(0, sb.length() - 2);
			}
		}
		return "";
	}
}
