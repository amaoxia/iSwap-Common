package com.common.utils.json;

import java.util.List;

import net.sf.json.util.PropertyFilter;

/**
 * 命名属性过滤器
 * @author tanhc
 *
 */
public class NamedPropertyFilter implements PropertyFilter {

	private List<String> names;
	private boolean exclude = true;

	public void setExclude(boolean exclude) {
		this.exclude = exclude;
	}

	public NamedPropertyFilter(List<String> names) {
		this.names = names;
	}

	public NamedPropertyFilter(List<String> names, boolean exclude) {
		this.names = names;
		this.exclude = exclude;
	}

	public boolean apply(Object source, String property, Object value) {
		if (names == null || names.size() < 1) {
			return !exclude;
		}
		for (String name : names) {
			if (name.equals(property)) {
				return exclude;
			}
		}
		return !exclude;
	}
}
