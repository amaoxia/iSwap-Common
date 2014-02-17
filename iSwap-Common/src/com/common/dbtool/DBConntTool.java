package com.common.dbtool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * 对数据库的操作进行封装
 * 
 * @Company 中海纪元
 * @author hudaowan
 * @version iSwap V6.0 数据交换平台
 * @date 2011-9-3 下午06:33:20
 * @Team 研发中心
 */
public class DBConntTool {

	private static final Log log = LogFactory.getLog(DBConntTool.class);
	private static DBConntTool db = null;
	private static BoneCPConfig config = null;
	private static Map<String,BoneCP> dbPool = null;
	private String jndiName = "";
	private String userName = "";
	private String url = "";

	public synchronized static DBConntTool jndiInit() {
		if (db == null) {
			db = new DBConntTool();
		}
		return db;
	}

	/**
	 * 使用BoneCp连接池
	 * 
	 * @return
	 * @author hudaowan
	 * @date 2011-9-3 下午07:25:22
	 */
	public synchronized static DBConntTool bcpoolInit() {
		if (db == null || config == null||dbPool ==null) {
			try {
				db = new DBConntTool();
				config = new BoneCPConfig();
				dbPool = new HashMap<String,BoneCP>();
			} catch (Exception e) {
				log.error("连接数据库失败", e);
			}
		}
		return db;
	}
	
	
	public DBConntTool() {

	}
	
	/**
	 * 创建连接池
	 *@author hudaowan
	 *@date   2012-4-12 下午2:19:36
	 *@return   
	 *        BoneCP  
	 *@exception
	 */
	private BoneCP createDBPool(String className, String url, String userName,String userPwd){
		 BoneCP connectionPool = null;
		 String key = url+"_"+userName+"_"+userPwd;
		 try {
			 if(!dbPool.containsKey(key)){
				// 加载JDBC驱动
				Class.forName(className);
				config.setJdbcUrl(url);
				// 数据库用户名
				config.setUsername(userName);
				// 数据库用户密码
				config.setPassword(userPwd);
				//设置每60秒检查数据库中的空闲连接数
	            config.setIdleConnectionTestPeriod(60);
	            //设置连接空闲时间
	            config.setIdleMaxAge(240);
	            //设置每个分区中的最大连接数 30
	            config.setMaxConnectionsPerPartition(30);
	            //设置每个分区中的最小连接数 10
	            config.setMinConnectionsPerPartition(10);
	            //当连接池中的连接耗尽的时候 BoneCP一次同时获取的连接数
	            config.setAcquireIncrement(5);
	            //连接释放处理
	            config.setReleaseHelperThreads(3);
	            //设置分区  分区数为3
	            config.setPartitionCount(3);
				// 创建数据库连接池
				connectionPool = new BoneCP(config);
				dbPool.put(key, connectionPool); 
				log.info("DBPool:【" + key + "】数据库的连接池创建成功!");
			 }else{
				 connectionPool = dbPool.get(key);
				 log.info("DBPool:【" + key + "】数据库的连接池成功获取!");
			 }
		 } catch (Exception e) {
			log.error(" DBPool:【" + url + "】数据库的连接池创建失败！", e);
		 }
      return connectionPool;
	}
   
	/**
	 * 使用BoneCp连接池得到数据库的连接
	 * 
	 * @param className
	 * @param url
	 * @param userName
	 * @param userPwd
	 * @return
	 * @author hudaowan
	 * @date 2011-9-3 下午07:21:01
	 */
	public  synchronized Connection getConn(String className, String url, String userName,String userPwd){
		Connection conn = null;
		try {
			BoneCP boneCP = this.createDBPool(className, url, userName, userPwd);
			conn = boneCP.getConnection();
			this.url = url;
			this.userName = userName;
			log.info("UserName:【" + userName + "】   URL:【" + url + "】连接成功！");
		} catch (Exception e) {
			log.error(" URL:【" + url + "】数据库连接失败！", e);
		}
		return conn;
	}

	/**
	 * 关闭数据库的连接
	 * 
	 * @author hudaowan
	 * @date 2011-9-3 下午09:22:49
	 */
	public synchronized void closeConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (!"".equals(jndiName)) {
				log.info("JNDI Name:【java:/" + jndiName + "】连接已关闭！");
			} else {
				log.info("UserName:【" + userName + "】   URL:【" + url+ "】连接已关闭！");
			}
		} catch (Exception e) {
			log.error("数据库连接关闭失败！", e);
		}
	}

	/**
	 * 停止数据库的连接池
	 * 
	 * @author hudaowan
	 * @date 2011-9-3 下午09:23:57
	 */
	public void shutdownConnPool() {
//		if (connectionPool != null) {
//			connectionPool.shutdown();
//		}
//		log.info("数据库的连接池已经关闭！");
	}

	/**
	 * 通过jndi得到数据库的连接
	 * 
	 * @author hudaowan
	 * @date Sep 28, 2008 11:10:20 AM
	 * @param jndiName
	 * @return
	 */
	public synchronized Connection getConn(String jndiName) {
		this.jndiName = jndiName;
		Connection conn = null;
		try {
			DataSource ds = this.getJNDI(jndiName);
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			log.info("JNDI Name:【java:/" + jndiName + "】 连接已打开....");
		} catch (Exception e) {
			log.error("JNDI Name:【java:/" + jndiName + "】连接失败！", e);
		}
		return conn;
	}

	private synchronized DataSource getJNDI(String jndiName)
			throws SQLException, IOException {
		DataSource ds = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/" + jndiName);
			log.info("jdniName:" + jndiName);
		} catch (Exception e) {
			log.error("连接数据库失败", e);
		}
		return ds;
	}

	public static void main(String[] msg) {
		DBConntTool tool = null;
		try {
			tool = DBConntTool.bcpoolInit();
			String className = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/iswapnodedb";
			String urlq = "jdbc:mysql://localhost:3306/iswap";
			String userName = "root";
			String userPwd = "root";
			for (int i = 0; i < 1000; i++) {
				Connection conn = tool.getConn(className, urlq, userName, userPwd);
				tool.closeConn(conn);
//				tool.getConn(className, urlq, userName, userPwd);
//				tool.closeConn();
				System.out.println("===================" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
