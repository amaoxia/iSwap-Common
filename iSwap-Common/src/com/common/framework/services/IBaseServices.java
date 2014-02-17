package com.common.framework.services;

import java.io.Serializable;
import java.util.List;

import com.common.framework.dao.QueryPara;
import com.common.framework.dao.SortPara;
import com.common.framework.exception.ServiceException;
import com.common.framework.web.pager.PageBean;

/**
 * 
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午10:05:36
 *@Team 研发中心
 */
public interface IBaseServices<E> {

	/**
	 * 根据id查找该对象
	 *@author huwanshan
	 *@date  2010-12-11 下午10:14:12
	 *@param id
	 *@return
	 */
	public E findById(Serializable id) throws ServiceException;
	
	/**
	 * 删除指定的对象数据
	 *@author huwanshan
	 *@date  2010-12-11 下午10:14:59
	 *@param entity
	 */
	public void delete(E entity) throws ServiceException;
	
	/**
	 * 
	 *@author huwanshan
	 *@date  2010-12-11 下午10:15:34
	 *@param id
	 */
	public void deleteById(Serializable id) throws ServiceException;
	
	/**
	 *  删除多个对象的数据
	 *@author huwanshan
	 *@date  2010-12-11 下午10:16:49
	 *@param ids
	 */
	public void deleteAllByIds(Serializable[] ids) throws ServiceException;
	
	/**
	 * 添加一个对象数据
	 *@author huwanshan
	 *@date  2010-12-11 下午10:17:34
	 *@param entity
	 */
	public void insert(E entity) throws ServiceException;
	
	/**
	 * 更新
	 *@author huwanshan
	 *@date  2010-12-11 下午10:18:59
	 *@param entity
	 */
	public void update(E entity) throws ServiceException;
	/**
	 * 保存或更改
	 * @param entity
	 * @throws ServiceException
	 * @author lifh
	 */
	public void saveOrUpdate(E entity) throws ServiceException;
	
	/**
	 * 分页显示数据
	 *@author huwanshan
	 *@date  2010-12-12 下午05:17:32
	 *@param mainHql
	 *@param queryParas
	 *@param sortParas
	 *@param page
	 *@return
	 *@throws ServiceException
	 */
	public List<E> findAllByPage(String mainHql,List<QueryPara> queryParas,List<SortPara> sortParas,PageBean page)throws ServiceException;
	
	/**
	 * 得到所有数据
	 * @return
	 * @author lifh
	 */
	public List<E> findAll();
	
	/**
	 * 根据指定属性和值得到符合条件的结果
	 * @param propertyName
	 * @param value
	 * @return
	 * @throws ServiceException 
	 * @author  hudaowan
	 * @date 2011-9-23 上午10:58:56
	 */
	public List<E>  findByProperty(String propertyName, String value) throws ServiceException;
	
	/**
	 * 根据指定一组属性和值得到符合条件的结果
	 * @param names
	 * @param values
	 * @return
	 * @throws ServiceException 
	 * @author  hudaowan
	 * @date 2011-9-23 上午10:59:38
	 */
	public List<E>  findByProperty(String[] names,String[] values) throws ServiceException;
	
	/**
	 * 判断一个对象属性值在数据库中是否是唯一的
	 *@author huwanshan
	 *@date  2010-12-12 下午04:31:57
	 *@param entity
	 *@param uniquePropertyNames
	 *@return
	 *@throws ServiceException
	 */
	public boolean isUnique(Object entity, String uniquePropertyNames) throws ServiceException;
	
	/**
	 *  根据 一组属性和值查询符合条件的唯一对象
	 *@author huwanshan
	 *@date  2010-12-12 下午04:57:53
	 *@param names
	 *@param values
	 *@return
	 *@throws ServiceException
	 */
	public E findUniqueByProperty(String[] names,Object[] values)  throws ServiceException;
	
	/**
	 * 根据属性和值得到符合条件的对象
	 *@author huwanshan
	 *@date  2010-12-12 下午05:02:29
	 *@param propertyName
	 *@param value
	 *@return
	 *@throws ServiceException
	 */
	public E findUniqueByProperty(String propertyName, Object value)throws ServiceException;
	
	 public E loadById(Serializable id) throws ServiceException;
}
