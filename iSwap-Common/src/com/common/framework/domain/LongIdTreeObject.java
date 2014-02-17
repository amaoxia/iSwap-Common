package com.common.framework.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 树型实体基类　使用String作为主键
 *@Company 北京光码软件有限公司
 *@author hudaowan
 *@version  iSwap BI V1.0
 *@date  2010-10-1 下午06:14:50
 *@Team 研发中心
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class LongIdTreeObject<E extends TreeEntity<E>> extends TreeEntity<E> {

	protected Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
