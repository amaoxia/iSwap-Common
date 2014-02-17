package com.common.framework.view;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.framework.web.utils.WebUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 最基础的strutsAction
 *@Company 北京光码软件有限公司
 *@author hudaowan
 *@version  iSwap BI V1.0
 *@date  2010-10-3 下午04:58:29
 *@Team 研发中心
 */
public class StrutsAction extends ActionSupport {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7620856865904518908L;
    //写日志信息
	protected Logger log = LoggerFactory.getLogger(getClass());
	//对象映射
	@Autowired
	protected DozerBeanMapper mapperValue;
	
	public static final String LIST = "list";
	public static final String VIEW = "view";
	public static final String UPDATEVIEW = "update";
	public static final String ADDVIEW = "add";
	public static final String RELOAD = "reload";
	
	/**
	 * 从request中获取页面参数
	 *
	 * @param key 参数名
	 * @return <tt>String</tt>类型的值或<tt>null</tt>
	 */
	protected String getStringParameter(String key) {
		return getHttpServletRequest().getParameter(key);
	}

	/**
	 * 从request中获取页面参数
	 *
	 * @param key 参数名
	 * @return <tt>String[]</tt>类型的值或<tt>null</tt>
	 */
	protected String[] getParameter(String key) {
		return getHttpServletRequest().getParameterValues(key);
	}
	
	/**
	 * 得到session的值
	 *@author hudaowan
	 *@date  2010-10-3 下午05:04:51
	 *@return
	 */
	protected Map<String,Object> getSession() {
		return  ActionContext.getContext().getSession();
	}
   
    /**
     * 得到HttpServletRequest
     *@author hudaowan
     *@date  2010-10-3 下午05:05:42
     *@return
     */
	protected HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) ActionContext.getContext().get(
				ServletActionContext.HTTP_REQUEST);
	}

	/**
	 * 得到HttpServletResponse
	 *@author hudaowan
	 *@date  2010-10-3 下午05:06:02
	 *@return
	 */
	protected HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) ActionContext.getContext().get(
				ServletActionContext.HTTP_RESPONSE);
	}
    
	/**
	 * 将在值回写到上下文路径中
	 *@author hudaowan
	 *@date  2010-12-12 下午06:54:00
	 *@param map
	 */
	protected void setContextMap(Map<String,Object> map) {
		ActionContext.getContext().setParameters(map);
	}
	
	/**
	 * 请求的地址，不带参数
	 *@author hudaowan
	 *@date  2010-10-3 下午05:06:22
	 *@return
	 */
	public String getInputUrl() {
		return getHttpServletRequest().getRequestURI();
	}

    /**
     * 从session中获取值
     *@author hudaowan
     *@date  2010-10-3 下午05:07:19
     *@param name
     *@return
     */
	protected Object getFromSession(String name) {
		return ActionContext.getContext().getSession().get(name);
	}
    
	/**
	 * 向session中写值
	 *@author hudaowan
	 *@date  2010-10-3 下午05:08:57
	 *@param name
	 *@param value
	 */
	protected void setSession(String name, Object value) {
		ActionContext.getContext().getSession().put(name, value);
	}
    
	
	/**
	 * 清除session的值
	 *@author hudaowan
	 *@date  2010-10-3 下午05:09:43
	 *@param name
	 */
	protected void removeFromSession(String name) {
		ActionContext.getContext().getSession().remove(name);
	}

	public void addMessage(String key) {
		addMessage(key, null);
	}
    
	public void addMessage(String key, String[] params) {
		String text = getText(key, params);
		if (text != null) {
			try {
				addActionMessage(new String(text.getBytes("iso-8859-1"), "gbk"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String,Object> getParamMap(HttpServletRequest request){ //从request中获取参数Map
		return WebUtil.getParamMap(request, "");
	}
	/**
	 * 完成对象之间拷贝
	 *@author hudaowan
	 *@date  2010-9-15 下午12:46:16
	 *@param source
	 *@param destination
	 */
	protected void doValueCopy(Object source, Object destination) {//将源对象中的值copy到目标对象中
		this.mapperValue.map(source, destination);
	}

}
