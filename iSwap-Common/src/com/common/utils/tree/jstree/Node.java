/*
 * @(#)JsonData.java
 *
 * Copyright (C) 2005, zgcworld All right reserved.
 * see the site: http://www.zgcworld.com
 */

package com.common.utils.tree.jstree;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点，最后会生成 li 标签的结点，attr 属性的值会属于 li 标签
 * @author lifh
 * @mail wslfh2005@163.com
 * @since Dec 29, 2010 9:54:38 AM
 * @name com.common.utils.tree.jstree.JsonData.java
 * @version 1.0
 */

public class Node {

    /**
     * 节点类型：根节点
     */
    public static final String NODE_TYPE_ROOT = "root";
    /**
     * 节点类型：文件夹节点
     */
    public static final String NODE_TYPE_FOLDER = "folder";
    /**
     * 节点状态：打开
     */
    public static final String NODE_STATE_OPEN = "open";
    /**
     * 节点状态：关闭
     */
    public static final String NODE_STATE_CLOSE = "close";

    /**
     * checkbox 节点状态，选中
     */
    public static final String CHECKED = "jstree-checked";
    /**
     * checkbox 节点状态，没选中
     */
    public static final String UNCHECKED = "jstree-unchecked";

    /**
     * 节点状态 如：打开，关闭
     */
    private String state;

    /**
     * 属性
     */
    private Attr attr;
    /**
     * 数据
     */
    private Data data;
    /**
     * 子节点
     */
    private List<Node> children;

    /**
     * 可以扩展此类加入更多的属性做为参数，可以 通过 var $a = $(event.target); $a.parent().attr('id') 得到属性的值
     * @author lifh
     */
    public class Attr {

        /**
         * id
         */
        private String id;
        /**
         * 是什么类型,如：root ,folder 等
         */
        private String rel;
        /**
         * 设置它的样式，如 选中状态：jstree-checked
         */
        private String clAss = UNCHECKED;

        public String getId() {
            return id;
        }
        public Attr setId(String id) {
            this.id = id;
            return this;
        }
        public String getRel() {
            return rel;
        }
        public Attr setRel(String rel) {
            this.rel = rel;
            return this;
        }
        
        public String getClAss() {
            return clAss;
        }
        
        public void setClAss(String clAss) {
            this.clAss = clAss;
        }


    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Attr getAttr() {
        if (attr == null) {
            attr = new Attr();
        }
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Node> getChildren() {
        if (children == null) {
            children = new ArrayList<Node>();
        }
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

}
