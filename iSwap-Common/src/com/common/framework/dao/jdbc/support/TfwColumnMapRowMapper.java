package com.common.framework.dao.jdbc.support;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

@SuppressWarnings("unchecked")
public class TfwColumnMapRowMapper implements RowMapper {

	public TfwColumnMapRowMapper() {
	}

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Map<String,Object> mapOfColValues = new HashMap<String,Object>();;
        for(int i = 1; i <= columnCount; i++)
        {
            String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
            Object obj = getColumnValue(rs, i);
            mapOfColValues.put(key.toLowerCase(), obj);
        }

        return mapOfColValues;
	}
	
	protected String getColumnKey(String columnName)
    {
        return columnName;
    }

    protected Object getColumnValue(ResultSet rs, int index)
        throws SQLException
    {
        return JdbcUtils.getResultSetValue(rs, index);
    }
    
}
