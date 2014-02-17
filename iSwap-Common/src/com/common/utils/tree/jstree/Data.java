/*
 * @(#)Data.java
 *
 * Copyright (C) 2005, zgcworld All right reserved.
 * see the site: http://www.zgcworld.com
 */

package com.common.utils.tree.jstree;

/**
 * 数据，最后会生成 a 标签，属性 attr 里面的值会做为 a 标签的属性值。
 * @author lifh
 * @mail wslfh2005@163.com
 * @since Dec 29, 2010 10:31:53 AM
 * @name com.common.utils.tree.jstree.Data.java
 * @version 1.0
 */

public class Data {
    
    /**
     * 名称
     */
    private String title;
    /**
     * 属性
     */
    private Attr attr = null;

    public class Attr {

        /**
         * 链接地址
         */
        private String href = null;
        /**
         * 提示信息，在页面以浮动的形式展现
         */
        private String title = "";
        /**
         * 是否叶子节点
         */
        private boolean leaf = false;
        
       

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

}
