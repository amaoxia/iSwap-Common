package com.common.jaxbutil;

import java.io.File;

/**
 * 对象和Xml绑定的操作封装
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-9 上午11:27:06
 *@Team 研发中心
 */
public interface IObjectBindXml {

	/**
	 * 将Xml绑定到对象 并把数据写给对象
	 * 
	 * @author hudaowan
	 * @date 2008-4-10 下午06:12:04
	 * @param xmlPath
	 *            xml文件的地址
	 * @param t
	 * @return
	 */
	public Object xmlToObj(String xmlPath,String classPath);

	/**
	 * 将Xml绑定到对象 并把数据写给对象
	 * 
	 * @author hudaowan
	 * @date 2008-4-11 上午10:44:41
	 * @param file
	 *            xml
	 * @param obj
	 * @return
	 */
	public Object xmlToObj(File file,  String classPath);

	/**
	 * 将xml的字符串转换为对象
	 * 
	 * @author hudaowan
	 * @date 2008-4-11 上午11:00:45
	 * @param xmlStr
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object xmlStrToObj(String xmlStr, Class obj);

	/**
	 * 将对象转换成xml
	 * 
	 * @author hudaowan
	 * @date 2008-4-10 下午06:12:57
	 * @param xmlPath
	 * @param t
	 */
	@SuppressWarnings("unchecked")
	public void objToXml(String xmlPath, Class clazz);

	/**
	 * 将对象转换成xml 并且以字符串返回
	 * 
	 * @author hudaowan
	 * @date 2008-4-11 上午10:39:50
	 * @param clazz
	 * @return
	 */
	public String objToXml(String classPath ,Object obj,String filePath);
	
    

}
