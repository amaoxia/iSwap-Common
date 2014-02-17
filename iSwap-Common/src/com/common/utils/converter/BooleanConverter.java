package com.common.utils.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BooleanConverter implements Converter {

	private static Log logger = LogFactory.getLog(DateConverter.class);

	private String TRUESTR = "1";

	private String FALSESTR = "0";

	public Boolean convert(String value, String paramter) {

		Boolean flag = null;

		if (value == null || "".equals(value)) {
			return null;
		}
		if (TRUESTR.equals(value.trim())) {
			flag = true;
		}
		if (FALSESTR.equals(value.trim())) {
			flag = false;
		}
		return flag;
	}
}
