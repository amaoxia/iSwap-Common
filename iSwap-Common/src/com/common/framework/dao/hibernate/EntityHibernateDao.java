package com.common.framework.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.framework.dao.QueryPara;
import com.common.framework.dao.SortPara;
import com.common.framework.exception.ServiceException;
import com.common.framework.web.pager.PageBean;
import com.common.utils.common.ReflectionUtils;

/**
 * 对DAO操作进行封装
 * @Company 北京光码软件有限公司
 *@author huwanshan
 *@version iSwap V6.0 数据交换平台
 *@date 2010-12-11 下午04:41:22
 *@Team 研发中心
 */
@SuppressWarnings("unchecked")
public class EntityHibernateDao<E> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected PowerHibernateDao powerHibernateDao;
    protected Class<E> entityClass;

    public EntityHibernateDao() {
        this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    public EntityHibernateDao(PowerHibernateDao powerHibernateDao, Class<E> entityClass) {
        this.powerHibernateDao = powerHibernateDao;
        this.entityClass = entityClass;
    }

    public void setPowerHibernateDao(PowerHibernateDao powerHibernateDao) {
        this.powerHibernateDao = powerHibernateDao;
    }

    public void executeUpdate(String hql, Object... values) {
        this.powerHibernateDao.executeUpdate(hql, values);
    }

    public E findById(Serializable id) {
        return (E) powerHibernateDao.getById(entityClass, id);
    }

    public E loadById(Serializable id) {
        return (E) powerHibernateDao.loadById(entityClass, id);
    }
    
    public void save(Object entity) {
        this.powerHibernateDao.save(entity);
    }

    public void saveOrUpdate(Object entity) {
        this.powerHibernateDao.saveOrUpdate(entity);
    }
    public void merge(Object entity) {
        this.powerHibernateDao.merge(entity);
    }
    public void update(Object entity) {
        this.powerHibernateDao.update(entity);
    }

    public void remove(Object entity) {
        this.powerHibernateDao.remove(entity);
    }

    public void removeById(Serializable id) {
        this.powerHibernateDao.removeById(entityClass, id);
    }

    public void removeAllByIds(Serializable[] ids) {
        this.powerHibernateDao.removeAllByIds(entityClass, ids);
    }

    /**
     * 得到所有数据的总数
     * @author huwanshan
     *@date 2010-12-11 下午09:22:15
     *@return
     */
    public Integer getTotalAll() {
        return getTotalByHql("select count(obj.id) from " + entityClass.getSimpleName() + " obj");
    }

    public List<E> findAll(int start, int max) {
        return powerHibernateDao.findListByCriteria(entityClass, null, null, start, max);
    }

    public List findListByHql(String hql, int start, int max, Object... values) {
        return powerHibernateDao.findListByHql(hql, start, max, values);
    }

    public List findListByHql(String hql, Object... values) {
        return powerHibernateDao.findListByHql(hql, values);
    }

    public Object findUniqueByHql(String hql, Object... values) {
        return powerHibernateDao.findUniqueByHql(hql, values);
    }

    public Integer getTotalByHql(String queryString, Object... values) {
        return powerHibernateDao.getTotalByHql(queryString, values);
    }

    public Integer getTotalByCriteria(List<Criterion> criterion) {
        return powerHibernateDao.getTotalByCriteria(entityClass, criterion);
    }

    public List<E> findListByDetachedCriteria(DetachedCriteria criteria, List<Criterion> criterion, List<Order> orders, int start, int max) {
        return powerHibernateDao.findListByDetachedCriteria(criteria, criterion, orders, start, max);
    }

    public List<E> findListByCriteria(List<Criterion> criterion, List<Order> orders, int start, int max) {
        return powerHibernateDao.findListByCriteria(entityClass, criterion, orders, start, max);
    }

    public E findUniqueByDetachedCriteria(DetachedCriteria criteria, List<Criterion> criterion) {
        return (E) powerHibernateDao.findUniqueByDetachedCriteria(criteria, criterion);
    }

    public E findUniqueByCriteria(List<Criterion> criterion) {
        return (E) powerHibernateDao.findUniqueByCriteria(entityClass, criterion);
    }

    /**
     * 判断一个对象属性值在数据库中是否是唯一的
     * @author huwanshan
     *@date 2010-12-11 下午09:21:05
     *@param entity
     *@param uniquePropertyNames
     *@return
     */
    public boolean isUnique(Object entity, String uniquePropertyNames) {
        return powerHibernateDao.isUnique(entity, uniquePropertyNames);
    }

    public List<E> findListByQueryPara(String mainHql, List<QueryPara> queryParas, List<SortPara> sortParas, int start,
                    int max) {
        return powerHibernateDao.findListByQueryPara(mainHql, queryParas, sortParas, start, max);
    }

    public E findUniqueByProperty(String propertyName, Object value) {
        return (E) powerHibernateDao.findUniqueByProperty(entityClass, propertyName, value);
    }

    public Object findUniqueByQueryPara(String mainHql, List<QueryPara> queryParas) {
        return powerHibernateDao.findUniqueByQueryPara(mainHql, queryParas);
    }

    public Integer getTotalByQueryPara(String mainHql, List<QueryPara> queryParas) {
        return powerHibernateDao.getTotalByQueryPara(mainHql, queryParas);
    }

    public Object findUniqueByHql(String hql, Map<String, Object> values) {
        return powerHibernateDao.findUniqueByHql(hql, values);
    }

    public List<E> findListByHql(String hql, Map<String, Object> values, int start, int max) {
        return powerHibernateDao.findListByHql(hql, start, max, values);
    }

    public Integer getTotalByHql(String hql, Map<String, Object> values) {
        return powerHibernateDao.getTotalByHql(hql, values);
    }

    public E findUniqueByProperty(String[] names, Object[] values) {
        return (E) powerHibernateDao.findUniqueByProperty(entityClass, names, values);
    }

    public List<E> findListByProperty(String[] names, String[] values, int start, int max) {
        return powerHibernateDao.findListByProperty(entityClass, names, values, start, max);
    }

    public Integer getTotalByProperty(String[] names, String[] values) {
        return powerHibernateDao.getTotalByProperty(entityClass, names, values);
    }
    
    /**
     * 
     * @param mainHql
     * @param queryParas
     * @param sortParas
     * @param page
     * @return
     * @throws ServiceException
     * @author HuJun
     */
    public List<E> findListByPage(String mainHql, PageBean page, Object... values) {
        List<E> list = new ArrayList<E>();
        String hqlCount = "select count(*) " + mainHql;
        int total = getTotalByHql(hqlCount, values);
        page.setTotal(total);
        list = findListByHql(mainHql, page.getStart(), page.getPerPage(), values);
        return list;
    }
    
    // 得到当前 session
    public Session getSession() {
        return powerHibernateDao.getSession();
    }

}
