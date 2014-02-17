/*
 * @(#)FileOperatorUtil.java
 *
 * Copyright (C) 2005, zgcworld All right reserved.
 * see the site: http://www.zgcworld.com
 */

package com.common.utils.file;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 文件操作
 * @author  lifh
 * @mail    wslfh2005@163.com
 * @since   Jan 17, 2011 2:04:29 PM
 * @name    com.common.utils.file.FileOperatorUtil.java
 * @version 1.0
 */

public class FileOperatorUtil {
    /**
     * LOG used to output the Log info..
     */
    private static Logger logger = LoggerFactory.getLogger(FileOperatorUtil.class);

    /**
     * 查找classpath下的文件。
     * @param classPath 类似 ：/com/ligitalsoft/defcat/domain/
     * @param fileName 文件名
     * @return
     * @author lifh
     */
    public static InputStream getResource(String classPath, String fileName) {
        InputStream resource = null;
        try {
            resource = FileOperatorUtil.class.getResourceAsStream(classPath + fileName);
        } catch (Exception e) {
            logger.debug("找不到文件！");
        }
       
        return resource;
    }
}

