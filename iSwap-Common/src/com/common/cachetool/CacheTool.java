package com.common.cachetool;

import java.io.Serializable;
import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 对cache 方法进行封装
 *@Company 中海纪元
 *@author   hudaowan
 *@version   iSwap V6.0 数据交换平台  
 *@date   2011-8-31 下午09:41:53
 *@Team 研发中心
 */   
public class CacheTool {
	 private static final Log log = LogFactory.getLog(CacheTool.class);
	 private static String path = "/config/ehcache/ehcache.xml";
	 private static CacheManager manager = null;
	 private static CacheTool tool = null;
	 public synchronized static CacheTool init(){
			 if(tool==null){    
				 tool = new CacheTool();
				 URL url = CacheTool.class.getResource(path);
				 manager = CacheManager.create(url);
				 log.info("缓存初始化成功!");
			 }  
		 return tool;
	 }
	    
	/**
	 * 得到缓存管理器
	 * @return 
	 * @author  hudaowan
	 * @date 2011-9-4 下午02:43:09
	 */
	 @SuppressWarnings("static-access")
	public CacheManager getCacheManager(){
		 return this.manager;
	 }
	 
	 /**
	  * 创建缓存
	  *@author hudaowan
	  *@date  Sep 19, 2008 2:47:13 PM
	  */
	 public Cache createCache(String key){
		 Cache cache = new Cache(key, 100, true, true, 0, 0); 
		 manager.addCache(cache);
		 log.info("名称：【"+key+"】缓存已经创建...");
		 return cache;
	 }
	 
	 /**
	  * 得到指定的缓存
	  * @param cacheName
	  * @return 
	  * @author  hudaowan
	  * @date 2011-9-4 下午03:02:33
	  */
	 public Cache findCache(String cacheName){
		 Cache cache  = manager.getCache(cacheName);  
		 log.info("cacheName：【"+cacheName+"】   objectCount：【"+cache.getSize()+"】");
		 return cache;
	 }
	 
	 /**
	  * 缓存中添加数据
	  *@author hudaowan
	  *@date  Sep 19, 2008 3:02:44 PM
	  *@param cache
	  *@param key
	  *@param text
	  */
	 public void putCacheInfo(Cache cache,Object key,Object obj){
		 Element elm = cache.get(key);
		 if(elm!=null){
			 cache.remove(key);
		 }
		 cache.put(new Element((Serializable)key, (Serializable) obj));
		 log.info("cacheName：【"+cache.getName()+"】 Key：【"+key.toString()+"】 添加成功！");
	 }
	 
	 /**
	  * 得到缓存的信息
	  *@author hudaowan
	  *@date  Sep 19, 2008 3:05:27 PM
	  *@param cache
	  */
	 public Object  getCacheInfo(Cache cache,Object key){
		 Object value = "";
		 Element elm = (Element)cache.get(key);
		 if(elm!=null){
			value = (Object)elm.getObjectValue();
		 }
		 log.info("cacheName：【"+cache.getName()+"】 Key：【"+key.toString()+"】 获取信息成功！");
	   return value;
	 }
	 
	 
	 /**
	  * 从缓存中删除指定的对象
	  *@author hudaowan
	  *@date  Sep 28, 2008 3:47:17 PM
	  *@param cache
	  *@param key
	  */
	 public void deleteCacheInfo(Cache cache,Object key){
		 Element elm = cache.get(key);
		 if(elm!=null){
			 cache.remove(key);
		 }
		 log.info("cacheName：【"+cache.getName()+"】 Key：【"+key.toString()+"】 信息删除成功！");
	 }
	 
	 /**
	  * 从缓存管理器中删除缓存
	  *@author hudaowan
	  *@date  Sep 19, 2008 3:00:15 PM
	  *@param key
	  */
	 public void removeCache(String key){
		 manager.removeCache(key);
		log.info("名称：【"+key+"】缓存被删除。");
	 }
	 
	 /**
	  * 缓存管理器停止
	  * @author  hudaowan
	  * @date 2011-9-4 下午03:12:11
	  */
	 public void showdowmCache(){
		 manager.shutdown();
	 }
	 
}