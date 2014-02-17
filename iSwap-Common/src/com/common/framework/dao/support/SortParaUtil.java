package com.common.framework.dao.support;

import com.common.framework.dao.Constants;
import com.common.framework.dao.SortPara;


/**
 * 对排序的参数进行操作
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-12 下午04:53:10
 *@Team 研发中心
 */
public class SortParaUtil {

	public static boolean isAsc(SortPara sortPara){
		if(Constants.ASC.equalsIgnoreCase(sortPara.getOrder()))
			return true;
		return false;
	}
	
	/**
	 * 检验是否有排序的属性
	 *@author huwanshan
	 *@date  2010-12-12 下午04:54:07
	 *@param sortPara
	 *@return
	 */
	public static boolean validateSortPara(SortPara sortPara){
		String sort = sortPara.getOrder();
		if(Constants.ASC.equalsIgnoreCase(sort)||Constants.DESC.equalsIgnoreCase(sort))
			return true;
		return false;
	}
	
	 /**
	  * 得到排序的语句
	  *@author huwanshan
	  *@date  2010-12-12 下午04:55:03
	  *@param sortPara
	  *@return
	  */
	public static String getOrderStr(SortPara sortPara){
		if (SortParaUtil.validateSortPara(sortPara)) {
			StringBuilder sb = new StringBuilder("");
			sb.append(sortPara.getProperty())//属性
			  .append(" ")
			  .append(sortPara.getOrder());//order串
			 return sb.toString(); 
		}else{
			return "";
		}
	}

	
}
