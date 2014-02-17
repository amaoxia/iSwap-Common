package com.common.jaxbutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

/**
 * 实现xml和object绑定的接口
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version  iSwap V6.0 数据交换平台  
 *@date  2010-12-9 上午11:27:19
 *@Team 研发中心
 */
public class ObjectBindXmlImpl implements IObjectBindXml {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@SuppressWarnings("unchecked")
	public void objToXml(String xmlPath, Class clazz) {
		 OutputStream os = null;
		try {
			 os = new FileOutputStream(xmlPath);
			 JAXBContext context = JAXBContext.newInstance(clazz);
			 Marshaller mu = context.createMarshaller();
			 mu.marshal(clazz, os);
		} catch (FileNotFoundException e) {
			log.error("ObjectBindXmlImpl objToXml is err!!!"+e);
		} catch (JAXBException e) {
			log.error("ObjectBindXmlImpl objToXml is err!!!"+e);
		}  
	}

	public String objToXml(String classPath ,Object obj,String filePath) {
		String xmlStr = null;
		try {
			 FileOutputStream outStream = new FileOutputStream(filePath);
			 JAXBContext context = JAXBContext.newInstance(classPath);
			 Marshaller mu = context.createMarshaller();
			 mu.marshal(obj, outStream);
			 SAXReader reader = new SAXReader();
			 Document doc = reader.read(filePath);
			 doc.setXMLEncoding("gb2312");
			 xmlStr = doc.asXML();
		} catch (FileNotFoundException e) {
			log.error("ObjectBindXmlImpl objToXml is err!!!"+e);
		} catch (JAXBException e) {
			log.error("ObjectBindXmlImpl objToXml is err!!!"+e);
		} catch (DocumentException e) {
			log.error("ObjectBindXmlImpl objToXml is err!!!"+e);
		} 
		return xmlStr;
	}
	
	public Object xmlToObj(File file, String classPath) {
		Object t = null;
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance(classPath);
			Unmarshaller u = jc.createUnmarshaller();
			t =  u.unmarshal(file);
		} catch (JAXBException e) {
			log.error("ObjectBindXmlImpl xmlToObj is err!!!"+e);
		}
		return t;
	}


	@SuppressWarnings("unchecked")
	public Object xmlStrToObj(String xmlStr,Class obj) {
		Object t = null;
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(obj);
			Unmarshaller u = jc.createUnmarshaller();
		    t = u.unmarshal( new StreamSource(new StringReader(xmlStr)));
		} catch (JAXBException e) {
			log.error("ObjectBindXmlImpl xmlStrToObj is err!!!"+e);
		}
		return t;
	}
	
	
	public Object xmlToObj(String xmlPath,String classPath) {
		Object t = null;
		InputStream xmlInputStream = null;
		try {
			xmlInputStream = new FileInputStream(xmlPath);
			JAXBContext jc = JAXBContext.newInstance(classPath);
			Unmarshaller u = jc.createUnmarshaller();
			t =  u.unmarshal(xmlInputStream);
			xmlInputStream.close();
		} catch (FileNotFoundException e) {
			log.error("ObjectBindXmlImpl xmlToObj is err!!!"+e);
		} catch (JAXBException e) {
			log.error("ObjectBindXmlImpl xmlToObj is err!!!"+e);
		} catch (IOException e) {
			log.error("ObjectBindXmlImpl xmlToObj is err!!!"+e);
		}
		return t;
	}
	
	public static void main(String[] agr) {
		File file = new File("E:/test/catalogxml.xml");
		try {
			ObjectBindXmlImpl objXml = new ObjectBindXmlImpl();
			 objXml.xmlToObj(file,"com.ligitalsoft.catalog.services.catalogservice.xmlbean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
