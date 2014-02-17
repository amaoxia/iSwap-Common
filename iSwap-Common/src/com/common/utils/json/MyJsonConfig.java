package com.common.utils.json;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JsonConfig;

/**
 * json配置注册类，添加了对日期进行了处理的注册类
 * 
 * @author tanhc
 * 
 */
public class MyJsonConfig extends JsonConfig {

	public MyJsonConfig(String datePatten) {
		DateJsonValueProcessor mdbp = StringUtils.isNotBlank(datePatten) ? new DateJsonValueProcessor(datePatten)
				: new DateJsonValueProcessor();
		registerJsonValueProcessor(Date.class, mdbp);
		registerJsonValueProcessor(Timestamp.class, mdbp);
		registerJsonValueProcessor(java.sql.Date.class, mdbp);
	}
}
