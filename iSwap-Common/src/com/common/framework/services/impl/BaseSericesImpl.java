package com.common.framework.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.common.framework.dao.QueryPara;
import com.common.framework.dao.SortPara;
import com.common.framework.dao.hibernate.EntityHibernateDao;
import com.common.framework.exception.ServiceException;
import com.common.framework.services.IBaseServices;
import com.common.framework.web.pager.PageBean;

/**
 * 领域对象业务管理类基类.
 * @Company 北京光码软件有限公司
 * @author hudaowan
 * @version iSwap BI V1.0
 * @date 2010-10-3 下午05:42:09
 * @Team 研发中心
 */
@Transactional
public abstract class BaseSericesImpl<E> implements IBaseServices<E> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据id查找该对象
     * @author huwanshan
     * @date 2010-12-11 下午04:35:00
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public E findById(Serializable id) throws ServiceException {
        return getEntityDao().findById(id);
    }
    
    public E loadById(Serializable id) throws ServiceException {
        return getEntityDao().loadById(id);
    }

    /**
     * 删除指定的对象数据
     * @author huwanshan
     * @date 2010-12-11 下午04:35:48
     * @param entity
     */
    public void delete(E entity) throws ServiceException {
        onDeleteBefore(entity);
        getEntityDao().remove(entity);
        onDeleteAfter(entity);
    }

    /**
     * 根据id删除对象数据
     * @author huwanshan
     * @date 2010-12-11 下午04:36:36
     * @param id
     */
    public void deleteById(Serializable id) throws ServiceException {
        delete(findById(id));
    }

    /**
     * 删除多个对象的数据
     * @author huwanshan
     * @date 2010-12-11 下午04:37:16
     * @param ids
     */
    public void deleteAllByIds(Serializable[] ids) throws ServiceException {
        getEntityDao().removeAllByIds(ids);
    }

    /**
     * 添加一个对象数据
     * @author huwanshan
     * @date 2010-12-11 下午04:37:58
     * @param entity
     */
    public void insert(E entity) throws ServiceException {
        onInsertBefore(entity);
        getEntityDao().save(entity);
        onInsertAfter(entity);
    }

    /**
     * 修改对象数据
     * @author huwanshan
     * @date 2010-12-11 下午04:39:39
     * @param entity
     */
    public void update(E entity) throws ServiceException {
        onUpdateBefore(entity);
        getEntityDao().update(entity);
        onUpdateAfter(entity);
    }

    @Override
    public void saveOrUpdate(E entity) throws ServiceException {
        getEntityDao().saveOrUpdate(entity);
    }

    /**
     * 分页显示所有的数据
     * @author huwanshan
     * @date 2010-12-11 下午09:19:00
     * @param first
     * @param max
     * @return
     */
    @Transactional(readOnly = true)
    public List<E> findAllByPage(String mainHql, List<QueryPara> queryParas, List<SortPara> sortParas, PageBean page) throws ServiceException {
        List<E> list = new ArrayList<E>();
        String hqlCount = "select count(*) " + mainHql;
        int total = getEntityDao().getTotalByQueryPara(hqlCount, queryParas);
        page.setTotal(total);
        list = getEntityDao().findListByQueryPara(mainHql, queryParas, sortParas, page.getStart(), page.getPerPage());
        return list;
    }

    public List<E> findAll() {
        return getEntityDao().findAll(0, Integer.MAX_VALUE);
    }

	public List<E>  findByProperty(String propertyName, String value) throws ServiceException{
		  List<E> list = new ArrayList<E>();
		  String[] names = new String[1];
		  String[] values = new String[1];
		  names[0] = propertyName;
		  values[0] = value;
		  list = getEntityDao().findListByProperty(names, values, 0, Integer.MAX_VALUE);
		  return list;
	}
	
	public List<E>  findByProperty(String[] names,String[] values) throws ServiceException{
		  return  getEntityDao().findListByProperty(names, values, 0, Integer.MAX_VALUE);
	}
	
    /**
     * 判断一个对象属性值在数据库中是否是唯一的
     * @author huwanshan
     * @date 2010-12-11 下午09:22:00
     * @param entity
     * @param uniquePropertyNames
     * @return
     */
    @Transactional(readOnly = true)
    public boolean isUnique(Object entity, String uniquePropertyNames) throws ServiceException {
        return getEntityDao().isUnique(entity, uniquePropertyNames);
    }

    @Transactional(readOnly = true)
    public E findUniqueByProperty(String[] names, Object[] values) {
        return getEntityDao().findUniqueByProperty(names, values);
    }

    @Transactional(readOnly = true)
    public E findUniqueByProperty(String propertyName, Object value) {
        return getEntityDao().findUniqueByProperty(propertyName, value);
    }
    // =================================可以重新的方法================================================================
    protected void onInsertBefore(E entity) {
    }
    protected void onInsertAfter(E entity) {
    }
    protected void onUpdateBefore(E entity) {
    }
    protected void onUpdateAfter(E entity) {
    }
    protected void onDeleteBefore(E entity) {
    }
    protected void onDeleteAfter(E entity) {
    }
    // =================================================需要实现的抽象方法============================================
    public abstract EntityHibernateDao<E> getEntityDao();
}
