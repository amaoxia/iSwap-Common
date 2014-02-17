package com.common.framework.view;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.common.framework.domain.IBaseEntity;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 对基础的添加、删除、修改、查询方法做封装和实现
 * @Company 北京光码软件有限公司
 * @author huwanshan
 * @version iSwap V6.0 数据交换平台
 * @date 2010-12-9 上午10:43:29
 * @Team 研发中心
 */
@Results( { @Result(name = StrutsAction.LIST, location = "list.ftl", type = "freemarker"),
	        @Result(name = StrutsAction.VIEW, location = "view.ftl", type = "freemarker"),
	        @Result(name = StrutsAction.UPDATEVIEW, location = "update.ftl", type = "freemarker"),
	        @Result(name = StrutsAction.ADDVIEW, location = "add.ftl", type = "freemarker") })
public abstract class FreemarkerBaseAction<E extends IBaseEntity> extends BaseAction<E> implements ModelDriven<E>,
                Preparable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -753583350439245517L;

    /**
     * 以列表的方式展示信息
     * @author huwanshan
     * @date 2010-12-8 下午01:18:27
     * @return
     */
    @SuppressWarnings("static-access")
    public String list() {
        try {
            this.onBeforeList();
            this.setQueryAndsort();// 设置查询条件
            // 分页查询
            this.listDatas = this.getAllObjectBypage();
            this.onAfterList();
            return this.LIST;
        } catch (Exception e) {
            this.errorInfo = "系统繁忙，浏览信息失败，请稍候再试！";
            log.error(errorInfo, e);
            return this.ERROR;
        }
    }
   
    /**
     * 删除实体数据
     * @author huwanshan
     * @date 2010-12-8 下午01:34:01
     * @return
     */
    @SuppressWarnings("static-access")
    public String delete() {
        try {
            this.onBeforeDelete();
            this.getEntityService().deleteAllByIds(ids);
            this.onAfterDelete();
            return RELOAD;
        } catch (Exception e) {
            this.errorInfo = "删除数据失败，有关联数据正在使用!";
            log.error(errorInfo, e);
            return this.ERROR;
        }
    }

    /**
     * 查看（装载）具体的实体数据
     * @author huwanshan
     * @date 2010-12-8 下午01:18:36
     * @return
     */
    @SuppressWarnings("static-access")
    public String view() {
    	this.onBeforeView();
    	
    	this.onAfterView();
        return this.VIEW;
    }

    /**
     * 得到添加页面
     * @author huwanshan
     * @date 2010-12-12 上午10:33:28
     * @return
     */
    @SuppressWarnings("static-access")
    public String addView() {
        try {
            this.onBeforeAddView();
            return this.ADDVIEW;
        } catch (Exception e) {
            this.errorInfo = "读取添加页面失败，请稍候再试!";
            log.error(errorInfo, e);
            return this.ERROR;
        }
    }

    /**
     * 新增保存具体的实体
     * @author huwanshan
     * @date 2010-12-8 下午01:25:45
     * @return
     */
    @SuppressWarnings("static-access")
    public String add() {
        try {
            if (validData(entityobj)) {// 验证业务逻辑数据
                this.onBeforeAdd();
                getEntityService().insert(entityobj);
                this.onAfterAdd();
            }
            return RELOAD;
        } catch (Exception e) {
            this.errorInfo = "添加数据失败，请稍候再试!";
            log.error(errorInfo, e);
            return this.ERROR;
        }
    }

    /**
     * 得到实体数据修改页面
     * @author huwanshan
     * @date 2010-12-9 下午02:58:56
     * @return
     */
    @SuppressWarnings("static-access")
    public String updateView() {
        this.onBeforeUpdateView();   
        return this.UPDATEVIEW;
    }

    /**
     * 修改保存具体的实体
     * @author huwanshan
     * @date 2010-12-8 下午01:25:54
     * @return
     */
    @SuppressWarnings("static-access")
    public String update() {
        try {
            if (validData(entityobj)) {
                this.onBeforeUpdate();
                this.getEntityService().update(entityobj);
                this.onAfterUpdate();
            }
            return RELOAD;
        } catch (Exception e) {
            this.errorInfo = "修改数据失败，请稍候再试!";
            log.error(errorInfo, e);
            return this.ERROR;
        }
    }
    
    /**
	  * 下载
	  */
	 public void downloadFile(String fileName,String path) {
		 try {
			ServletOutputStream out = this.getHttpServletResponse().getOutputStream();
			String dowName = new String(fileName.getBytes("gb2312"),"iso8859-1");
			//String dowName = new String(dowfileName.getBytes("gb2312"),"");
			this.getHttpServletResponse().setContentType("APPLICATION/OCTET-STREAM");
			this.getHttpServletResponse().setHeader("Content-Disposition", "attachment; filename="+dowName);
			//得到路径变量
			String itemname = path+"/"+fileName;
			if (fileName == null)
			{
				itemname = path+"/";
			}
			FileInputStream fileInputStream = new FileInputStream(itemname);
			int i;
			while ((i = fileInputStream.read()) != -1)
			{
				out.write(i);
			}
			fileInputStream.close();
			out.close(); 
			File delFile = new File(itemname);
			delFile.delete();
		 } catch (Exception e) {
			log.error("下载失败！", e);
		}
	 }

    @Override
    public E getModel() {
        return entityobj;
    }

    /**
     * 定义在view()前执行二次绑定.
     */
    public void prepareView() throws Exception {
        prepareModel();
    }

    /**
     * 定义在updateView()前执行二次绑定.
     */
    public void prepareUpdateView() throws Exception {
        prepareModel();
    }

    /**
     * 定义在Add()前执行二次绑定.
     */
    public void prepareAdd() throws Exception {
        prepareModel();
    }

    /**
     * 定义在Add()前执行二次绑定.
     */
    public void prepareUpdate() throws Exception {
        prepareModel();
    }

    /**
     * 等同于prepare()的内部函数,供prepardMethodName()函数调用.
     */
    public void prepareModel() throws Exception {
        if (null != id) {
            this.entityobj = getEntityById();
        } else {
            entityobj = doNewEntity();
        }
    }

    /**
     * 实现空的prepare()函数,屏蔽了所有Action函数都会执行的公共的二次绑定.
     */
    public void prepare() throws Exception {
    }

    // ============================================对添加、删除、修改、查询方法进行拦截==================================
    protected boolean validData(E entity) {// 校验数据的合法性
        return true;
    }

    protected void onBeforeAddView() {// addView前执行的操作
    }

    protected void onBeforeAdd() {// insert前执行的操作
    }

    protected void onAfterAdd() {// insert后执行的操作
    }

    protected void onBeforeDelete() {// delete前执行的操作
    }

    protected void onAfterDelete() {// delete后执行的操作
    }

    protected void onBeforeUpdate() {// update前执行的操作
    }

    protected void onAfterUpdate() {// update后执行的操作
    }

    protected void onBeforeList() {// list执行之前的操作
    }

    protected void onAfterList() {// list查询后执行的操作
    }

    protected void onBeforeView() {// view执行之前的操作
    }

    protected void onAfterView() {// view执行之后的操作
    }

    protected void onBeforeUpdateView() {// updateView执行之前的操作
    }

    protected void onAfterUpdateView() {// updateView执行之后的操作
    }

}
