package com.common.framework.dao.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.common.framework.dao.HibernateQueryHelper;
import com.common.framework.dao.QueryPara;
import com.common.framework.dao.SortPara;

/**
 * 通用Dao类
 * @Company 北京光码软件有限公司
 * @author huwanshan
 * @version iSwap V6.0 数据交换平台
 * @date 2010-12-11 下午04:45:48
 * @Team 研发中心
 */
@Repository
@SuppressWarnings("unchecked")
public class PowerHibernateDao extends AbstractHibernateDao implements InitializingBean {

    /**
     * 修改数据
     * @param hql
     *            带占位符?的HQL语句
     * @param values
     * @return
     */
    public int executeUpdate(String hql, Object... values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 修改数据
     * @param hql
     *            带命名参数的HQL语句
     * @param values
     *            Map对象
     * @return
     */
    public int executeUpdate(String hql, Map<String, Object> values) {
        return createQuery(hql, values).executeUpdate();
    }

    /**
     * 根据指定的id 加载对象
     * @author huwanshan
     * @date 2010-12-11 下午05:19:16
     * @param <E>
     * @param entityClass
     * @param id
     * @return
     */
    public <E> E loadById(Class<E> entityClass, Serializable id) {
        return (E) getSession().load(entityClass, id);
    }

    /**
     * 根据指定的id 得到对象
     * @author huwanshan
     * @date 2010-12-11 下午05:20:05
     * @param <E>
     * @param entityClass
     * @param id
     * @return
     */
    public <E> E getById(Class<E> entityClass, Serializable id) {
        return (E) getSession().get(entityClass, id);
    }

    /**
     * 持久对象 save()
     * @author huwanshan
     * @date 2010-12-11 下午05:20:41
     * @param entity
     */
    public void save(Object entity) {
        Assert.notNull(entity);
        getSession().save(entity);
        logger.debug("save entity: {}", entity);
    }

    /**
     * 持久对象 saveOrUpdate
     * @author lifh
     * @param entity
     */
    public void saveOrUpdate(Object entity) {
        Assert.notNull(entity);
        getSession().saveOrUpdate(entity);
        logger.debug("save entity: {}", entity);
    }

    /**
     * 合并对象 merge
     * @param entity
     * @author zhangx
     * @2011-3-1 下午05:46:23
     */
    public void merge(Object entity) {
        Assert.notNull(entity);
        getSession().merge(entity);
        logger.debug("save entity: {}", entity);
    }
    /**
     * 更新对象 saveOrUpdate
     * @author lifh
     * @date 2010-12-11 下午05:20:41
     * @param entity
     */
    public void update(Object entity) {
        Assert.notNull(entity);
        getSession().clear();// 清空session缓存
        getSession().update(entity);
        logger.debug("update entity: {}", entity);
    }

    /**
     * 删除指定对象
     * @author huwanshan
     * @date 2010-12-11 下午05:22:02
     * @param entity
     */
    public void remove(Object entity) {
        Assert.notNull(entity);
        getSession().delete(entity);
        logger.debug("delete entity: {}", entity);
    }

    /**
     * 删除指定id的对象
     * @author huwanshan
     * @date 2010-12-11 下午05:22:28
     * @param <E>
     * @param entityClass
     * @param id
     */
    public <E> void removeById(Class<E> entityClass, Serializable id) {
        Assert.notNull(id);
        getSession().delete(getById(entityClass, id));
    }

    /**
     * 删除多个对象
     * @author huwanshan
     * @date 2010-12-11 下午05:23:02
     * @param <E>
     * @param entityClass
     * @param ids
     */
    public <E> void removeAllByIds(Class<E> entityClass, Serializable[] ids) {
        if (ids != null && ids.length > 0) {
            for (Serializable id : ids) {
                removeById(entityClass, id);
            }
        }
    }

    /**
     * 分页显示实体对象数据
     * @author huwanshan
     * @date 2010-12-11 下午05:18:58
     * @param <E>
     * @param entityClass
     * @param start
     * @param max
     * @return
     */
    public <E> List<E> findAll(Class<E> entityClass, int start, int max) {
        return findListByCriteria(entityClass, null, null, start, max);
    }

    /**
     * 分页显示实体对象数据 可以加上条件查询
     * @author huwanshan
     * @date 2010-12-11 下午09:08:58
     * @param hql
     * @param start
     * @param max
     * @param values
     * @return
     */
    public List findListByHql(String hql, int start, int max, Object... values) {
        Assert.hasText(hql);
        Query query = createQuery(hql, values);
        prepareLimit(query, start, max);
        return query.list();
    }

    /**
     * 根据 hql查询 不分页
     * @param hql
     * @param values
     * @return
     * @author lifh
     */
    public List findListByHql(String hql, Object... values) {
        Assert.hasText(hql);
        Query query = createQuery(hql, values);
        return query.list();
    }

    /**
     * 分页显示实体对象数据 可以加上条件查询
     * @author huwanshan
     * @date 2010-12-11 下午09:09:42
     * @param hql
     * @param start
     * @param max
     * @param values
     * @return
     */
    public List findListByHql(String hql, int start, int max, Map<String, Object> values) {
        Assert.hasText(hql);
        Query query = createQuery(hql, values);
        prepareLimit(query, start, max);
        return query.list();
    }

    /**
     * 根据hql和条件值得到唯一的对象
     * @author huwanshan
     * @date 2010-12-11 下午05:33:21
     * @param hql
     * @param values
     *            可变数组
     * @return
     */
    public Object findUniqueByHql(String hql, Object... values) {
        Query query = createQuery(hql, values);
        prepareLimit(query, -1, 1);
        return query.uniqueResult();
    }

    /**
     * 根据hql和条件值得到唯一的对象
     * @author huwanshan
     * @date 2010-12-11 下午05:31:13
     * @param hql
     * @param values
     *            map
     * @return
     */
    public Object findUniqueByHql(String hql, Map<String, Object> values) {
        Query query = createQuery(hql, values);
        prepareLimit(query, -1, 1);
        return query.uniqueResult();
    }

    /**
     * 根据hql和条件可以得到符合条件的数据条数
     * @author huwanshan
     * @date 2010-12-11 下午05:33:01
     * @param queryString
     * @param values
     * @return
     */
    public Integer getTotalByHql(String queryString, Object... values) {
        Object o = findUniqueByHql(queryString, values);
        if (o != null)
            return Integer.valueOf(o.toString());
        return 0;
    }

    /**
     * 根据hql和条件值得到唯一的对象
     * @author huwanshan
     * @date 2010-12-11 下午05:34:56
     * @param queryString
     * @param values
     * @return
     */
    public Integer getTotalByHql(String queryString, Map<String, Object> values) {
        Object o = findUniqueByHql(queryString, values);
        if (o != null)
            return Integer.valueOf(o.toString());
        return 0;
    }

    /**
     * 根据Criterion条件 得到符合条件的数据总数
     * @author huwanshan
     * @date 2010-12-11 下午05:36:18
     * @param <E>
     * @param entityClass
     * @param criterion
     * @return
     */
    public <E> Integer getTotalByCriteria(Class<E> entityClass, List<Criterion> criterion) {
        Criteria criteria = createCriteria(entityClass);
        setConditions(criteria, criterion);
        criteria.setProjection(Projections.rowCount());
        Object o = criteria.uniqueResult();
        if (o != null)
            return Integer.valueOf(o.toString());
        return 0;
    }

    /**
     * 根据Criterion条件 得到符合条件的数据
     * @author huwanshan
     * @date 2010-12-11 下午05:44:23
     * @param <E>
     * @param entityClass
     * @param criterion
     * @param orders
     * @param start
     * @param max
     * @return
     */
    public <E> List<E> findListByCriteria(Class<E> entityClass, List<Criterion> criterion, List<Order> orders,
                    int start, int max) {
        Criteria criteria = createCriteria(entityClass);
        setConditions(criteria, criterion);
        setOrders(criteria, orders);
        prepareLimit(criteria, start, max);
        return criteria.list();
    }

    /**
     * 根据Criterion条件 得到符合条件的数据
     * @author huwanshan
     * @date 2010-12-11 下午07:34:41
     * @param <E>
     * @param criteria
     * @param criterion
     * @param orders
     * @param start
     * @param max
     * @return
     */
    public <E> List<E> findListByDetachedCriteria(DetachedCriteria criteria, List<Criterion> criterion,
                    List<Order> orders, int start, int max) {
        Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
        setConditions(executableCriteria, criterion);
        setOrders(executableCriteria, orders);
        prepareLimit(executableCriteria, start, max);
        return executableCriteria.list();
    }

    /**
     * 根据Criterion条件 得到符合条件的数据 用于单个对象查询
     * @author huwanshan
     * @date 2010-12-11 下午07:34:55
     * @param <E>
     * @param criteria
     * @param criterion
     * @return
     */
    public <E> E findUniqueByDetachedCriteria(DetachedCriteria criteria, List<Criterion> criterion) {
        Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
        setConditions(executableCriteria, criterion);
        prepareLimit(executableCriteria, -1, 1);
        return (E) executableCriteria.uniqueResult();
    }

    /**
     * 根据Criterion条件 得到符合条件的数据
     * @author huwanshan
     * @date 2010-12-11 下午07:35:22
     * @param <E>
     * @param entityClass
     * @param criterion
     * @return
     */
    public <E> E findUniqueByCriteria(Class<E> entityClass, List<Criterion> criterion) {
        Criteria criteria = createCriteria(entityClass);
        setConditions(criteria, criterion);
        prepareLimit(criteria, -1, 1);
        return (E) criteria.uniqueResult();
    }

    /** 根据QueryPara对象进行查询 */
    public List findListByQueryPara(String mainHql, List<QueryPara> queryParas, List<SortPara> sortParas, int start,
                    int max) {
        String hql = mainHql;
        if (queryParas != null && queryParas.size() > 0) {// 增加where部分
            hql += " where ";
            hql += HibernateQueryHelper.getQueryWhereStr(queryParas);
        }
        if (sortParas != null && sortParas.size() > 0) {
            hql += " order by ";
            hql += HibernateQueryHelper.getQueryOrderStr(sortParas);
        }
        Query query = createQuery(hql);
        HibernateQueryHelper.putValueInQuery(query, queryParas);
        prepareLimit(query, start, max);
        return query.list();
    }

    /**
     * 根据属性和值查找 符合条件的唯一对象
     * @author huwanshan
     * @date 2010-12-11 下午07:35:41
     * @param <E>
     * @param entityClass
     * @param propertyName
     * @param value
     * @return
     */
    public <E> E findUniqueByProperty(Class<E> entityClass, String propertyName, Object value) {
        Assert.hasText(propertyName);
        return (E) createCriteria(entityClass).add(Restrictions.eq(propertyName, value)).uniqueResult();
    }

    /**
     * 根据一组属性和值查找符合条件的唯一对象
     * @author huwanshan
     * @date 2010-12-11 下午07:36:28
     * @param <E>
     * @param entityClass
     * @param names
     * @param values
     * @return
     */
    public <E> E findUniqueByProperty(Class<E> entityClass, String[] names, Object[] values) {
        Criteria criteria = super.createCriteria(entityClass, names, values);
        return (E) criteria.uniqueResult();
    }

    /**
     * 根据一组属性和值查找符合条件的一组数据并且进行分页处理
     * @author huwanshan
     * @date 2010-12-11 下午07:37:10
     * @param <E>
     * @param entityClass
     * @param names
     * @param values
     * @param start
     * @param max
     * @return
     */
    public <E> List<E> findListByProperty(Class<E> entityClass, String[] names, Object[] values, int start, int max) {
        Criteria criteria = super.createCriteria(entityClass, names, values);
        prepareLimit(criteria, start, max);
        return (List<E>) criteria.list();
    }

    /**
     * 根据一组属性和值 得到符合条件的数据总数
     * @author huwanshan
     * @date 2010-12-11 下午07:38:06
     * @param <E>
     * @param entityClass
     * @param names
     * @param values
     * @return
     */
    public <E> Integer getTotalByProperty(Class<E> entityClass, String[] names, Object[] values) {
        Criteria criteria = createCriteria(entityClass);
        setConditions(criteria, names, values);
        criteria.setProjection(Projections.rowCount());
        Object o = criteria.uniqueResult();
        if (o != null)
            return Integer.valueOf(o.toString());
        return 0;
    }

    /**
     * QueryPara对象数据条件进行查询操作
     * @author huwanshan
     * @date 2010-12-11 下午07:38:58
     * @param mainHql
     * @param queryParas
     * @return
     */
    public Object findUniqueByQueryPara(String mainHql, List<QueryPara> queryParas) {
        String hql = mainHql;
        if (queryParas != null && queryParas.size() > 0) {
            hql += " where ";
            hql += HibernateQueryHelper.getQueryWhereStr(queryParas);
        }
        Query query = createQuery(hql);
        HibernateQueryHelper.putValueInQuery(query, queryParas);
        prepareLimit(query, -1, 1);
        return query.uniqueResult();
    }

    /**
     * 根据查询语句及查询参数，获取满足条件的记录数
     * @param mainHql
     *            主条件语句，为select count(*) from 开头
     * @param queryParas
     * @return
     */
    public Integer getTotalByQueryPara(String mainHql, List<QueryPara> queryParas) {
        Object o = findUniqueByQueryPara(mainHql, queryParas);
        if (o != null)
            return Integer.valueOf(o.toString());
        return 0;
    }

    /**
     * 判断对象某些属性的值在数据库中是否唯一.
     * @param uniquePropertyNames
     *            在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
     */
    public boolean isUnique(Object entity, String uniquePropertyNames) {
        Assert.hasText(uniquePropertyNames);
        Criteria criteria = createCriteria(entity.getClass()).setProjection(Projections.rowCount());
        String[] nameList = uniquePropertyNames.split(",");
        try {
            // 循环加入唯一列
            for (String name : nameList) {
                criteria.add(Restrictions.eq(name, PropertyUtils.getProperty(entity, name)));
            }
            // 以下代码为了如果是update的情况,排除entity自身.
            String idName = getIdName(entity.getClass());
            // 取得entity的主键值
            Object id = getId(entity);
            // 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
            if (id != null) {
                criteria.add(Restrictions.not(Restrictions.eq(idName, id)));
            }
        } catch (Exception e) {
            ReflectionUtils.handleReflectionException(e);
        }
        return (Integer) criteria.uniqueResult() == 0;
    }

    public void afterPropertiesSet() throws Exception {
        if (sessionFactory == null)
            throw new IllegalArgumentException("sessionFactory must be set");
    }

}
