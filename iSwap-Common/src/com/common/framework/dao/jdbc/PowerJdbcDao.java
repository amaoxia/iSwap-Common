package com.common.framework.dao.jdbc;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.common.framework.dao.JdbcQueryHelper;
import com.common.framework.dao.QueryPara;
import com.common.framework.dao.SortPara;



/**
 * 基于spring jdbc 的关于存储过程及函数调用的DAO类
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap BI V1.0
 *@date  2010-12-7 下午04:27:03
 *@Team 研发中心
 */
@Repository
public class PowerJdbcDao implements InitializingBean {

	@Autowired
	protected DataSource dataSource;

	protected SimpleJdbcTemplate simpleJdbcTemplate;
	protected SimpleJdbcCall simpleJdbcCall;

	public PowerJdbcDao() {
	}

	public PowerJdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
	}

	public void afterPropertiesSet() throws Exception {
		if (dataSource == null)
			throw new IllegalArgumentException("dataSource must be set");
		this.simpleJdbcCall = new SimpleJdbcCall(dataSource);
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
	}

	/**
	 * 执行数据库函数function
	 * @param <E> 返回值类型
	 * @param catalogName 分类名称
	 * @param functionName 函数function名称
	 * @param clazz 返回值class类型
	 * @param paras 包含参数的Map对象
	 * @return E
	 */
	public <E> E executeFunction(String catalogName, String functionName, Class<E> clazz, Map<String, Object> paras) {
		SimpleJdbcCall call = simpleJdbcCall;
		if (StringUtils.isNotBlank(catalogName))
			call.withCatalogName(catalogName);
		if (StringUtils.isNotBlank(functionName))
			call.withFunctionName(functionName);
		MapSqlParameterSource in = new MapSqlParameterSource();
		if (paras != null && paras.size() > 0) {
			Set<Entry<String, Object>> _set = paras.entrySet();
			for (Entry<String, Object> entry : _set) {
				in.addValue(entry.getKey(), entry.getValue());
			}
		}
		E value = call.executeFunction(clazz, in);
		return value;
	}

	/**
	 * 执行数据库存储procedure
	 * @param <E> 返回值类型
	 * @param catalogName 分类名称
	 * @param procedureName 过程名称
	 * @param clazz 返回值class类型
	 * @param paras 包含参数的Map对象
	 * @return E
	 */
	public <E> E executeProcedureForObject(String catalogName, String procedureName, Class<E> clazz,
			Map<String, Object> paras) {
		SimpleJdbcCall call = simpleJdbcCall;
		if (StringUtils.isNotBlank(catalogName))
			call.withCatalogName(catalogName);
		if (StringUtils.isNotBlank(procedureName))
			call.withProcedureName(procedureName);
		MapSqlParameterSource in = new MapSqlParameterSource();
		if (paras != null && paras.size() > 0) {
			Set<Entry<String, Object>> _set = paras.entrySet();
			for (Entry<String, Object> entry : _set) {
				in.addValue(entry.getKey(), entry.getValue());
			}
		}
		E value = call.executeObject(clazz, in);
		return value;
	}

	/**
	 * 执行数据库存储procedure
	 * @param catalogName 分类名称
	 * @param procedureName 过程名称
	 * @param paras 包含参数的Map对象
	 * @return Map<String,Object>
	 */
	public Map<String, Object> executeProcedureForMap(String catalogName, String procedureName,
			Map<String, Object> paras) {
		SimpleJdbcCall call = simpleJdbcCall;
		if (StringUtils.isNotBlank(catalogName))
			call.withCatalogName(catalogName);
		if (StringUtils.isNotBlank(procedureName))
			call.withProcedureName(procedureName);
		MapSqlParameterSource in = new MapSqlParameterSource();
		if (paras != null && paras.size() > 0) {
			Set<Entry<String, Object>> _set = paras.entrySet();
			for (Entry<String, Object> entry : _set) {
				in.addValue(entry.getKey(), entry.getValue());
			}
		}
		return call.execute(in);
	}

	/**
	 * 执行数据库存储procedure,
	 * @param catalogName 分类名称
	 * @param procedureName 过程名称
	 * @param clazz 返回值class类型
	 * @param paras 包含参数的Map对象
	 * @return List<E> 
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public <E> List<E> executeProcedureForList(String catalogName, String procedureName, Class<E> clazz,
			Map<String, Object> paras) {
		SimpleJdbcCall call = simpleJdbcCall;
		call.getJdbcTemplate().setResultsMapCaseInsensitive(true);
		if (StringUtils.isNotBlank(catalogName))
			call.withCatalogName(catalogName);
		if (StringUtils.isNotBlank(procedureName))
			call.withProcedureName(procedureName);
		call.returningResultSet("list", ParameterizedBeanPropertyRowMapper.newInstance(clazz));
		MapSqlParameterSource in = new MapSqlParameterSource();
		if (paras != null && paras.size() > 0) {
			Set<Entry<String, Object>> _set = paras.entrySet();
			for (Entry<String, Object> entry : _set) {
				in.addValue(entry.getKey(), entry.getValue());
			}
		}
		Map<String, Object> value = call.execute(in);
		return (List<E>) value.get("list");
	}

	/**
	 * 根据查询条件进行数据查询
	 * @param mainHql 主干sql语句
	 * @param queryParas 查询条件列表
	 * @param sortParas 查询参数列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findListByQueryPara(String mainHql, List<QueryPara> queryParas, List<SortPara> sortParas) {
		String sql = mainHql;
		if (queryParas != null && queryParas.size() > 0) {//增加where部分
			sql += " where ";
			sql += JdbcQueryHelper.getQueryWhereStr(queryParas);
		}
		if (sortParas != null && sortParas.size() > 0) {
			sql += " order by ";
			sql += JdbcQueryHelper.getQueryOrderStr(sortParas);
		}
		Map<String, Object> paramValues = JdbcQueryHelper.getValueInMap(queryParas);
		return simpleJdbcTemplate.queryForList(sql, paramValues);
	}
}
