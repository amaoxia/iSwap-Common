/*
 * @(#)Dom4jXmlHelper.java
 *
 * Copyright (C) 2005, zgcworld All right reserved.
 * see the site: http://www.zgcworld.com
 */

package com.common.utils.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dom4j 帮助类
 * @author lifh
 * @mail wslfh2005@163.com
 * @since Jan 17, 2011 11:05:46 AM
 * @name com.common.utils.xml.Dom4jXmlHelper.java
 * @version 1.0
 */

public class Dom4jXmlHelper {

    /**
     * LOG used to output the Log info..
     */
    private static Logger logger = LoggerFactory.getLogger(Dom4jXmlHelper.class);

    /**
     * 取得doc
     * @param inputStream
     * @return
     * @author lifh
     */
    public static Document getDocument(InputStream inputStream) {
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(inputStream);
        } catch (DocumentException e) {
            throw new RuntimeException("提取XML文档失败！" + e);
        }
        return document;
    }

    /**
     * 取得doc
     * @param file
     * @return
     * @author lifh
     */
    public static Document getDocument(File file) {
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            throw new RuntimeException("提取XML文档失败！" + e);
        }
        return document;
    }
    
    /**
     * 保存文档。
     * @param document
     * @param calsspath
     * @param fileName
     * @author lifh
     */
    public static String saveDoc(Document document, String classpath,String fileName) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        String path = Dom4jXmlHelper.class.getResource("/").getPath()+classpath+fileName;
        XMLWriter xmlWriter = null;
        try {
            xmlWriter = new org.dom4j.io.XMLWriter(new FileWriter(path), format);
            document.setXMLEncoding("UTF-8");
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e1) {
            logger.error("IO读取出错！",e1);
        }
        logger.info("保存XML文档成功，路径："+path);
        return path;
    }
    
    public static void main(String[] args) {
       
        saveDoc( DocumentHelper.createDocument(), "com/ligitalsoft/defcat/domain/", "aa.xml");
    }
}
