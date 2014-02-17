package com.common.framework.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 树型实体基类 使用String作为主键
 *@Company 北京光码软件有限公司
 *@author hudaowan
 *@version  iSwap BI V1.0
 *@date  2010-10-1 下午06:12:55
 *@Team 研发中心
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class StringIdTreeObject<E extends TreeEntity<E>> extends TreeEntity<E> {

	protected String id; // hibernate的uuid机制,生成32为字符串

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length =32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
