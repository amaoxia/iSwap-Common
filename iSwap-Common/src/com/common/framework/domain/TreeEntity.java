package com.common.framework.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.common.framework.domain.support.TreeEntityUtil;


/**
 * 应用于自身关联的无限制级树型基类
 *@Company 北京光码软件有限公司
 *@author hudaowan
 *@version  iSwap BI V1.0
 *@date  2010-10-2 下午07:37:41
 *@Team 研发中心
 */
@SuppressWarnings({ "unchecked", "serial" })
@MappedSuperclass
public abstract class TreeEntity<E extends TreeEntity> implements IBaseEntity {

    protected String name; //名称
    protected Integer sortOrder;//排序
    protected E parent = null;//父节点
    protected Set<E> children = new HashSet<E>();//孩子节点

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public E getParent() {
        return parent;
    }

    public void setParent(E parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @OrderBy("sortOrder asc")
    public Set<E> getChildren() {
        return children;
    }

    public void setChildren(Set<E> children) {
        this.children = children;
    }

    /**
     * 增加孩子节点
     * 
     * @param entity
     *            孩子节点
     */
    @Transient
    public void addChild(E entity) {
        children.add(entity);
    }

    @Transient
    public String getText(){
        return getName();
    }
    
    /**
     * 删除孩子节点
     * 
     * @param entity
     *            孩子节点
     */
    @Transient
    public void rmChild(E entity) {
        if (getChildren() != null && getChildren().size() > 0
                && getChildren().contains(entity))
            getChildren().remove(entity);
        entity.setParent(null);
    }

    /**
     * 是否为根节点
     * 
     * @return boolean
     */
    @Transient
    public boolean isRoot() {
        return (getParent() == null);
    }

    /**
     * 是否为叶子节点
     * 
     * @return boolean
     */
    @Transient
    public boolean isLeaf() {
        return (getChildren()==null)||(getChildren()!=null && getChildren().size() == 0);
    }

    /**
     * 判断自己是否为某节点的孩子
     * 
     * @param entity
     *            某节点
     * @return
     */
    @Transient
    public boolean isChildOf(E entity) {
        return TreeEntityUtil.isParentOf(entity, this);
    }

    @Transient
    public boolean isParentOf(E entity) {
        return TreeEntityUtil.isParentOf(this, entity);
    }

    /**
     * 不允许将外键关系设置成环状. 就是说，本节点的子节点中，如果包含parent，就不能把parent设置为本节点的上级节点
     * 
     * @param parentNode
     *            父节点
     * @return
     */
    @Transient
    public boolean isDeadLock(E parentNode) {
        return TreeEntityUtil.isDeadLock(this, parentNode);
    }
}
