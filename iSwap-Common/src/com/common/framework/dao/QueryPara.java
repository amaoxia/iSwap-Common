package com.common.framework.dao;

/**
 * 对hql的条件进行封装
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午09:29:57
 *@Team 研发中心
 */
public class QueryPara {

	/** 查询条件名 */
	protected String name = null; //待查询字段名 统一为“字段名” 或 “对象名.字段名” 或“对象名.别名.字段名”
	
	/** 查询操作 */
	protected String op = null;
	
	/** 查询值类型 */
	private String type = null;
	
	/** 查询值 */
	protected String value = null;

	public QueryPara() {
	}

	public QueryPara(String name, String type, String op, String value) {
		this.name = name;
		this.op = op;
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void setValue(Integer value) {
		this.value = value.toString();
	}
	
	public String toString() {
		return new StringBuilder("QueryPara#").append("name="+name).append("type="+type).append("op="+op).append(
						"value="+value).toString();
	}
}
