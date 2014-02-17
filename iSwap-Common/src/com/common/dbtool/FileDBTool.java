package com.common.dbtool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.config.ConfigAccess;
import com.common.config.Dictionary.Category;
import com.common.config.Entry;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

/**
 * 对MongoDB操作封装
 *@Company 中海纪元
 *@author   hudaowan
 *@version   iSwap V6.0 数据交换平台  
 *@date   2011-8-9 下午07:16:41
 *@Team 研发中心
 */
public class FileDBTool {
	private static final Log log = LogFactory.getLog(FileDBTool.class);
	
	//private static FileDBTool obj = null;
	private static Mongo mongo = null;
	public static FileDBTool init(){
//		if(obj==null){
//			obj = new FileDBTool();
//		}
		return FileDBToolHandler.obj;
	}
	private static class FileDBToolHandler {
		private static FileDBTool obj = new FileDBTool();
	}
	
	static {
		List<ServerAddress> replicaSetSeeds = new ArrayList<ServerAddress>();
        try {
        	log.info("获取云存储服务的连接......");
        	Category categry = ConfigAccess.init().findCategory("MongoDB");
        	List<Entry> entryList = categry.getEntry();
        	for(Entry entry:entryList){
        		 ServerAddress address =  new ServerAddress(entry.getCode(), new Integer(entry.getName()));
        		 replicaSetSeeds.add(address);
        	}
//        	 ServerAddress address =  new ServerAddress("192.168.68.168",new Integer(20000));
//    		 replicaSetSeeds.add(address);
        	
			MongoOptions options = new MongoOptions();

			options.autoConnectRetry = true;

			options.connectionsPerHost = 20;
			
			options.threadsAllowedToBlockForConnectionMultiplier = 10;

			options.connectTimeout = 6000;

			options.maxWaitTime = 12000;

			options.socketKeepAlive = true;

			options.socketTimeout = 2000;

			mongo = new Mongo(replicaSetSeeds,options);
        	
        } catch (Exception e) {
        	log.error("获取云存储服务的连接失败！", e);
        } 
	}
	
	/**
	 * 获取云存储服务连接
	 * @return 
	 * @author  2011-8-10 下午09:56:35
	 * @author  hudaowan
	 */
	public Mongo getMongoConn() {
		return mongo;
	}
	//mongo应为单例，不应每次都创建mongo对象实例
	
//	 public Mongo getMongoConn() {
//		     List<ServerAddress> replicaSetSeeds = new ArrayList<ServerAddress>();
//	        try {
//	        	log.info("获取云存储服务的连接......");
//	        	Category categry = ConfigAccess.init().findCategory("MongoDB");
//	        	List<Entry> entryList = categry.getEntry();
//	        	for(Entry entry:entryList){
//	        		 ServerAddress address =  new ServerAddress(entry.getCode(), new Integer(entry.getName()));
//	        		 replicaSetSeeds.add(address);
//	        	}
////	        	 ServerAddress address =  new ServerAddress("192.168.68.168",new Integer(20000));
////        		 replicaSetSeeds.add(address);
//	        	 mongo = new Mongo(replicaSetSeeds);
//	        	
//	        } catch (Exception e) {
//	        	log.error("获取云存储服务的连接失败！", e);
//	        } 
//	        return mongo;
//	    }
	
	/**
	 * 将数据写入到云存储中
	 * @param dataBaseName
	 * @param tableName
	 * @param listMap 
	 * @author  2011-8-10 下午10:15:24
	 * @author  hudaowan
	 */
	public void saveToFiledb(String dataBaseName,String tableName,List<Map<String,Object>> listMap){
		List<DBObject> dboList = new ArrayList<DBObject>();
		try{
			int count = 0;
			for(Map<String,Object> map:listMap){
				 BasicDBObject bdobj = new BasicDBObject();
				 bdobj.putAll(map);
				 dboList.add(bdobj);
			 }
			 count = dboList.size();
			 if(count>0){
				 DB db = mongo.getDB(dataBaseName);
				 DBCollection tabledb = db.getCollection(tableName);
				 tabledb.insert(dboList);
			 }
			 log.info("数据库名：【"+dataBaseName+"】  表名：【"+tableName+"】  成功写入"+count+"数据.");
		}catch(Exception e){
			log.error("数据写入云存储失败！",e);
		}
		
	}

	/**
	 * 将数据写入到云存储中
	 * @param dataBaseName
	 * @param tableName
	 * @param map 
	 * @author  2011-8-10 下午10:15:49
	 * @author  hudaowan
	 */
	public void saveToFiledb(String dataBaseName,String tableName,Map<String,Object> map){
		 try{
			 BasicDBObject bdobj = new BasicDBObject();
			 bdobj.putAll(map);
			 DB db = mongo.getDB(dataBaseName);
			 DBCollection tabledb = db.getCollection(tableName);
			 tabledb.insert(bdobj);
			 log.info("数据库名：【"+dataBaseName+"】  表名：【"+tableName+"】数据写入成功.");
		 }catch(Exception e){
			 log.error("数据写入云存储失败！",e);
		 }
	}
	
	/**
	 * 查询数据库
	 * @author hudaowan
	 * @date 2011-11-4下午05:57:06
	 * @param dataBaseName
	 * @param tableName
	 * @param prmtMap
	 * @return
	 */
	public List<Map<String,Object>> findToFiledb(String dataBaseName,String tableName){
		List<Map<String,Object>> list_map = new ArrayList<Map<String,Object>>();
		try{
			 DB db = mongo.getDB(dataBaseName);
			 DBCollection tabledb = db.getCollection(tableName);
			 DBCursor cur = tabledb.find();
			 while(cur.hasNext()){
				 DBObject rus_db =  cur.next();
				 list_map.add(rus_db.toMap());
			 }
			 log.info("数据库名：【"+dataBaseName+"】  表名：【"+tableName+"】查询数据总数！.");
		 }catch(Exception e){
			 log.error("查询云存储数据失败！",e);
		 }
		return list_map;
	}
	/**
	 * 加条件查询所有数据
	 * @param dataBaseName
	 * @param tableName
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findAllFiledb(String dataBaseName,String tableName,Map<String,Object> map){
		List<Map<String,Object>> list_map = new ArrayList<Map<String,Object>>();
		try{
			 DB db = mongo.getDB(dataBaseName);
			 DBCollection tabledb = db.getCollection(tableName);
			 BasicDBObject bdobj = new BasicDBObject();
			 bdobj.putAll(map);
			 DBCursor cur = tabledb.find(bdobj);
			 while(cur.hasNext()){
				 DBObject rus_db =  cur.next();
				 list_map.add(rus_db.toMap());
			 }
			 log.info("数据库名：【"+dataBaseName+"】  表名：【"+tableName+"】查询数据总数！.");
		 }catch(Exception e){
			 log.error("查询云存储数据失败！",e);
		 }
		return list_map;
	}
	/**
	 * 
	 * @author hudaowan
	 * @date 2011-11-5上午10:39:13
	 * @param dataBaseName
	 * @param tableName
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findToFiledb(String dataBaseName,String tableName,Map<String,Object> map){
		List<Map<String,Object>> list_map = new ArrayList<Map<String,Object>>();
		try{
			 DB db = mongo.getDB(dataBaseName);
			 DBCollection tabledb = db.getCollection(tableName);
			 BasicDBObject bdobj = new BasicDBObject();
			 bdobj.putAll(map);
			 DBCursor cur = tabledb.find(bdobj);
			 int n = 0;
			 while(cur.hasNext()&&n<10){
				 DBObject rus_db =  cur.next();
				 list_map.add(rus_db.toMap());
				 n++;
			 }
			 log.info("数据库名：【"+dataBaseName+"】  表名：【"+tableName+"】查询数据总数【"+list_map.size()+"】条！.");
		 }catch(Exception e){
			 log.error("查询云存储数据失败！",e);
		 }
		return list_map;
	}
	/**
	 * 查数据量多的数据
	 * 
	 * @author fangbin
	 * @param dataBaseName
	 * @param tableName
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> findManyToFiledb(String dataBaseName,String tableName,Map<String,Object> map){
		List<Map<String,Object>> list_map = new ArrayList<Map<String,Object>>();
		try{
			 DB db = mongo.getDB(dataBaseName);
			 DBCollection tabledb = db.getCollection(tableName);
			 BasicDBObject bdobj = new BasicDBObject();
			 bdobj.putAll(map);
			 DBCursor cur = tabledb.find(bdobj);
			 int i=cur.size();
			 if(i>0){
				 int n = 1;
				 while(i>=n&&n<=2){
					 DBObject rus_db =  cur.next();
					 list_map.add(rus_db.toMap());
					 n++;
				 }
			 }
			
			 log.info("数据库名：【"+dataBaseName+"】  表名：【"+tableName+"】查询数据总数【"+list_map.size()+"】条！.");
		 }catch(Exception e){
			 log.error("查询云存储数据失败！",e);
		 }
		return list_map;
	}
	/**
	 * 根据条件删除数据库中的数据
	 * @author hudaowan
	 * @date 2011-11-5上午10:39:20
	 * @param dataBaseName
	 * @param tableName
	 * @param map
	 */
	public int deleteToFiledb(String dataBaseName,String tableName,Map<String,Object> map){
		int count = 0;
		try{
			 DB db = mongo.getDB(dataBaseName);
			 DBCollection tabledb = db.getCollection(tableName);
			 BasicDBObject bdobj = new BasicDBObject();
			 bdobj.putAll(map);
			 count = tabledb.remove(bdobj).getN();
			 log.info("数据库名：【"+dataBaseName+"】  表名：【"+tableName+"】删除数据总数【"+count+"】！.");
		 }catch(Exception e){
			 log.error("删除云存储数据失败！",e);
		 }
		return count;
	}
	
	/**
	 * 修改数据库中的数据
	 * @author hudaowan
	 * @date 2011-11-5上午11:01:09
	 * @param dataBaseName
	 * @param tableName
	 * @param src_map
	 * @param taget_map
	 * @return
	 */
	public int updateToFiledb(String dataBaseName,String tableName,Map<String,Object> src_map,Map<String,Object> taget_map){
		int count = 0;
		try{
			 DB db = mongo.getDB(dataBaseName);
			 DBCollection tabledb = db.getCollection(tableName);
			 BasicDBObject src_bdobj = new BasicDBObject();
			 src_bdobj.putAll(src_map);
			 BasicDBObject taget_bdobj = new BasicDBObject();
			 taget_bdobj.putAll(taget_map);
			 count = tabledb.update(src_bdobj,taget_bdobj).getN();
			 log.info("数据库名：【"+dataBaseName+"】  表名：【"+tableName+"】更新数据总数【"+count+"】条！.");
		 }catch(Exception e){
			 log.error("更新云存储数据失败！",e);
		 }
		return count;
	}
	
	/**
	 * 关闭云存储的连接
	 * @author  hudaowan
	 * @date 2011-9-4 上午10:13:50
	 */
	public void closeFileDB(){
		//mongo.close();
		log.info("云存储连接已关闭！");
	}

}
