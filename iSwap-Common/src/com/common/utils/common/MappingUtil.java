package com.common.utils.common;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

/**
 * 主要是用两个对象之间的值拷贝
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午08:00:44
 *@Team 研发中心
 */
public class MappingUtil {

	public static Mapper getMapper(){
		return DozerBeanMapperSingletonWrapper.getInstance();
	}
	
	public static <E> E copyValue(Object source,Class<E> destinationClass){
		E entity = getMapper().map(source, destinationClass);
		return entity;
	}
	
	public static void copyValue(Object source, Object destination){
		getMapper().map(source, destination);
	}
	
}
