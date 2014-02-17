package com.common.framework.dao.hibernate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * 对hibernate的封装
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-11 下午07:44:51
 *@Team 研发中心
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao implements InitializingBean {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 设置查询对象Query中的分页参数
	 * @param queryObject
	 * @param first
	 * @param max
	 */
	public void prepareLimit(Query queryObject, int first, int max) {
		if (first >= 0)
			queryObject.setFirstResult(first);
		if (max > 0)
			queryObject.setMaxResults(max);
	}
	
	/**
	 * 设置查询对象Criteria中的分页参数
	 * @param criteria
	 * @param first
	 * @param max
	 */
	public void prepareLimit(Criteria criteria, int first, int max) {
		if (first >= 0)
			criteria.setFirstResult(first);
		if (max > 0)
			criteria.setMaxResults(max);
	}

	/**
	 * 设置查询对象Criteria中的条件参数
	 * @param criteria
	 * @param criterions
	 */
	public void setConditions(Criteria criteria, List<Criterion> criterions) {
		if (criterions != null && criterions.size() > 0) {
			for (Criterion c : criterions) {
				criteria.add(c);
			}
		}
	}
	
	/**
	 * 设置查询对象Criteria中的条件过滤参数
	 * @param criteria
	 * @param orders
	 */
	public void setOrders(Criteria criteria, List<Order> orders) {
		if (orders != null && orders.size() > 0) {
			for (Order order : orders) {
				criteria.addOrder(order);
			}
		}
	}
	
	/**
	 * 设置查询对象Criteria中的排序过滤参数
	 * @param criteria
	 * @param sortMap
	 */
	public void setSortMap(Criteria criteria, Map sortMap) {
		if (null != sortMap && !sortMap.isEmpty()) {
			for (Object o : sortMap.keySet()) {
				String fieldName = o.toString();
				String orderType = sortMap.get(fieldName).toString();
				if ("asc".equalsIgnoreCase(orderType)) {
					criteria.addOrder(Order.asc(fieldName));
				} else {
					criteria.addOrder(Order.desc(fieldName));
				}
			}
		}
	}

	/**
	 * 根据实体的类名，创建查询对象Criteria
	 * @param <E>
	 * @param entityClass
	 * @return
	 */
	public <E> Criteria createCriteria(Class<E> entityClass){
		return getSession().createCriteria(entityClass);
	}
	
	public void setConditions(Criteria criteria, String[] names, Object[] values) {
		if ( null!= names && (names.length==values.length)) {
			for ( int i = 0; i < names.length; i++ ) {
				criteria.add(Restrictions.eq(names[i], values[i]));
			}
		}
	}
	
	/**
	 * 根据实体的类名，属性名数组，属性值数组，创建查询对象Criteria
	 * @param <E>
	 * @param entityClass
	 * @param names
	 * @param values
	 * @return
	 */
	public <E> Criteria createCriteria(Class<E> entityClass, String[] names, Object[] values){
		Criteria criteria = createCriteria(entityClass);
		setConditions(criteria,names,values);
		return criteria;
	}
	
	/**
	 * 根据实体类及对应的过滤条件Criterion列表，创建查询对象Criteria
	 * @param <E>
	 * @param entityClass
	 * @param criterions
	 * @return
	 */
	public <E> Criteria createCriteria(Class<E> entityClass, List<Criterion> criterions) {
		Criteria criteria = createCriteria(entityClass);
		setConditions(criteria, criterions);
		return criteria;
	}
	
	/**
	 * 根据查询语句获取查询对象Query
	 * @param queryString
	 * @return
	 */
	public Query createQuery(String queryString){
		Assert.hasText(queryString, "queryString不能为空");
		this.logger.info("查询HQL:【"+queryString+"】");
		return getSession().createQuery(queryString);
	}
	
	/**
	 * 根据带？占位符的查询语句及值对象，构造Query对象
	 * @param queryString 带？占位符的查询语句
	 * @param values 值对象
	 * @return Query对象
	 */
	public Query createQuery(String queryString, Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query queryObject = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				queryObject.setParameter(i, values[i]);
			}
		}
		return queryObject;
	}
	
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values 命名参数,按名称绑定.
	 */
	public Query createQuery(String queryString, final Map<String, Object> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	} 
	
	/**
	 * 取得对象的主键名,辅助函数.
	 */
	protected String getIdName(Class clazz) {
		Assert.notNull(clazz);
		ClassMetadata meta = sessionFactory.getClassMetadata(clazz);
		Assert.notNull(meta, "Class " + clazz + " not define in hibernate session factory.");
		String idName = meta.getIdentifierPropertyName();
		Assert.hasText(idName, clazz.getSimpleName() + " has no identifier property define.");
		return idName;
	}
	
	/**
	 * 取得对象的主键值,辅助函数.
	 */
	protected Object getId(Object entity) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Assert.notNull(entity);
		return  PropertyUtils.getProperty(entity, getIdName(entity.getClass()));
	}

	/**
	 * 初始化对象.
	 * 使用load()方法得到的仅是对象Proxy后, 在传到View层前需要进行初始化.
	 * initObject(user) ,初始化User的直接属性，但不会初始化延迟加载的关联集合和属性.
	 * initObject(user.getRoles())，初始化User的直接属性和关联集合.
	 * initObject(user.getDescription())，初始化User的直接属性和延迟加载的Description属性.
	 */
	public void initObject(Object object) {
		Hibernate.initialize(object);
	}
	
	/**
	 * 通过Set将不唯一的对象列表唯一化.
	 * 主要用于HQL/Criteria预加载关联集合形成重复记录,又不方便使用distinct查询语句时.
	 */
	public <E> List<E> distinct(List<E> list) {
		Set<E> set = new LinkedHashSet<E>(list);
		return new ArrayList<E>(set);
	}
}
