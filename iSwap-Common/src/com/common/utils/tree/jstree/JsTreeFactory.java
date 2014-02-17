package com.common.utils.tree.jstree;

import com.common.utils.json.JsonHelper;

/**
 * @author lifh
 * @mail wslfh2005@163.com
 * @since Dec 29, 2010 10:41:47 AM
 * @name com.common.utils.tree.jstree.JsTreeFactory.java
 * @version 1.0
 */

public class JsTreeFactory {

    private static final JsTreeFactory jsTreeFactory = new JsTreeFactory();

    private JsTreeFactory() {
    }

    public static JsTreeFactory newInstance() {
        return jsTreeFactory;
    }

    public synchronized String createJsTree(Node node) {
        return JsonHelper.toJsonString(node);
    }
}
