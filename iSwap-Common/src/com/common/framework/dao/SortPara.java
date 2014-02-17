package com.common.framework.dao;

/**
 * 排序参数对象,代表一个排序条件
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-12 下午04:47:19
 *@Team 研发中心
 */
public class SortPara {
	
	private String property;//排序字段名 统一为“字段名” 或 “对象名.字段名” 或“对象名.别名.字段名”
    private String order;//排序方向 asc或desc
    
	public SortPara() {
	}
	
	public SortPara(String property, String order) {
		this.property = property;
		this.order = order;
	}

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
}
