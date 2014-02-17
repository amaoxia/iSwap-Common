package com.common.framework.web.utils;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

public class DozerUtil {

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
