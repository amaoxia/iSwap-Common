package com.common.framework.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * id为 Long
 * 
 * @Company 北京光码软件有限公司
 *@author hudaowan
 *@version iSwap BI V1.0
 *@date 2010-10-1 下午06:15:31
 *@Team 研发中心
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class LongIdObject implements IBaseEntity {
	protected Long id;

	/**
	 * 主键策略
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
