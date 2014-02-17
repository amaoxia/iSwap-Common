package com.common.framework.view;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.common.framework.dao.QueryPara;
import com.common.framework.dao.RequestHelper;
import com.common.framework.dao.SortPara;
import com.common.framework.dao.Constants;
import com.common.framework.exception.ServiceException;
import com.common.framework.services.IBaseServices;
import com.common.framework.web.pager.PageBean;

/**
 * action基类，提供常用的便利方法 主要是得到对象和得到id
 * 
 * @Company 北京光码软件有限公司
 * @author huwanshan
 * @version iSwap V6.0 数据交换平台
 * @date 2010-12-9 上午10:43:10
 * @Team 研发中心
 */

public abstract class BaseAction<E> extends StrutsAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -7385114529624026114L;
	public E entityobj; // 实体对象
	/**
	 * 主键
	 */
	protected Long id;
	/** 主键数组 */
	protected Long[] ids ;
	public String errorInfo; // 错误信息
	public String logcontent; // 操作日志
	public PageBean page = new PageBean(); // 分页对象
	public List<E> listDatas = new ArrayList<E>(); // 得到list对象在页面显示
	protected List<QueryPara> queryParas = new ArrayList<QueryPara>();// 查询的条件
	protected List<SortPara> sortParas = new ArrayList<SortPara>();// 排序的条件
	protected Map<String, String> serchMap = new HashMap<String, String>(); // 用于查询

	/** ====================对领域对象的操作======================================== */

	/**
	 * 功能：提供HQL及SQL的条件拼接功能 目前只支持各个条件的条件"与"and
	 * 
	 * @Company 北京光码软件有限公司
	 * @author huwanshan
	 * @date 2010-12-12 下午06:16:45
	 */
	protected void setQueryAndsort() {

		queryParas.addAll(RequestHelper
				.getParametersWithCondition(getHttpServletRequest()));// 在页面页面上的格式conditions[age,>=,int]
		sortParas.addAll(RequestHelper
				.getOrderParametersWithOrder(getHttpServletRequest()));// 在页面页面上的格式order[age]

		serchMap.putAll(RequestHelper.getParametersForMap(
				getHttpServletRequest(), RequestHelper.CONDITION)); // 将条件的属性和值写到serchMap中
		serchMap.putAll(RequestHelper.getParametersForMap(
				getHttpServletRequest(), RequestHelper.ORDER));// 将排序的属性和值的serchMap中

		queryParas.addAll(RequestHelper
				.getParametersWithPrefix(getHttpServletRequest()));// search_
		sortParas.addAll(RequestHelper
				.getOrderParametersWithPrefix(getHttpServletRequest()));// order_

		serchMap.putAll(RequestHelper.getParametersForMap(
				getHttpServletRequest(), RequestHelper.SEARCHPREFIX));// 将条件的值写的serchMap中
		serchMap.putAll(RequestHelper.getParametersForMap(
				getHttpServletRequest(), RequestHelper.ORDERPREFIX));// 将排序的属性和值的serchMap中

		Map<String, String> paraMap = RequestHelper.prefixedMapToMap(serchMap); // [e.loginName,string,like]=1
		// 转换成e_loginName=1
		if (sortParas.size() == 0) {
			sortParas.add(new SortPara("e.id", Constants.DESC));// 初始以主键ID排序
		}

		serchMap.clear();
		serchMap.putAll(paraMap);
	}

	/**
	 * 用于查询和分页显示
	 * 
	 * @author hudaowan
	 * @date 2010-9-15 下午01:55:23
	 * @return
	 */
	protected List<E> getAllObjectBypage() throws ServiceException {
		List<E> list = new ArrayList<E>();
		list = getEntityService().findAllByPage(getSelectStr(), queryParas,
				sortParas, page);
		return list;
	}

	/**
	 * 生成对象查询语句
	 * 
	 * @author hudaowan
	 * @date 2010-9-15 下午01:55:23
	 * @return
	 */
	protected String getSelectStr() { // 获取mainSql
		return "from " + getEntityClass().getSimpleName() + " e ";
	}

	/**
	 * 从request中把参数组装到域对象中
	 * 
	 * @author huwanshan
	 * @date 2010-12-12 下午09:31:56
	 * @param request
	 * @return
	 */
	protected E popValueFromRequest(HttpServletRequest request) {
		E obj = doNewEntity();
		Map<String, Object> source = getParamMap(request);
		mapperValue.map(source, obj);
		return obj;
	}

	/**
	 * new一个实体对象
	 * 
	 * @return 类型为E的一个实体对象
	 */
	protected E doNewEntity() {
		E object = null;
		try {
			object = getEntityClass().newInstance();
			this.onNewEntity(object);
		} catch (Exception e) {
			log.error("Can't new Instance of entity.", e);
		}
		return object;
	}

	/**
	 * new对象后进行其他的操作
	 * 
	 * @author hudaowan
	 * @date 2010-10-3 下午05:48:03
	 * @param object
	 */

	protected void onNewEntity(E object) {

	}

	/**
	 * 获取指定id的 领域对象
	 * 
	 * @author hudaowan
	 * @date 2010-10-3 下午05:43:40
	 * @param sid
	 * @return
	 */
	protected E getEntityById(Serializable sid) throws ServiceException {
		return this.getEntityService().findById(sid);
	}

	/**
	 * 获取当前id的对象
	 * 
	 * @author hudaowan
	 * @date 2010-10-3 下午05:47:21
	 * @return
	 */
	protected E getEntityById() throws ServiceException {
		return this.getEntityService().findById(id);
	}

	/** =============实体主键类型处理================ */

	protected int getPriKeyType() {// 主键类型 String:1,Long:2
		return 2;
	}

	protected Serializable convertPriKey(String prikey) {
		if (getPriKeyType() == 1) {
			return prikey;
		} else {
			return this.convertSerializable(prikey);
		}
	}

	protected Serializable convertSerializable(String id) {
		Serializable sid;
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			sid = Long.valueOf(id);
		} else {
			sid = id;
		}
		return sid;
	}

	/**
	 * 获取实体类的名称 不包含全路径(包路径)
	 * 
	 * @return 实体类的名称
	 */
	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		return (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public E getEntityobj() {
		return entityobj;
	}

	public void setEntityobj(E entityobj) {
		this.entityobj = entityobj;
	}

	public PageBean getPage() {
		return page;
	}

	public void setPage(PageBean page) {
		this.page = page;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getLogcontent() {
		return logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}

	public List<E> getListDatas() {
		return listDatas;
	}

	public void setListDatas(List<E> listDatas) {
		this.listDatas = listDatas;
	}

	public Map<String, String> getSerchMap() {
		return serchMap;
	}

	public void setSerchMap(Map<String, String> serchMap) {
		this.serchMap = serchMap;
	}

	// =================================================需要实现的抽象方法============================================
	protected abstract IBaseServices<E> getEntityService(); // 获取服务类

	// ==============================================================================================================

	public List<SortPara> getSortParas() {
		return sortParas;
	}

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public void setSortParas(List<SortPara> sortParas) {
		this.sortParas = sortParas;
	}
}
