package com.common.utils.ziputil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * 解压zip/rar的包
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version   V1.0
 *@date  2008-9-8 下午03:24:50
 *@Team 数据交换平台研发小组
 */
public class ExtractZip {
	private  final Log log = LogFactory.getLog(this.getClass());

	public ExtractZip() {

	}

	/**  
	 * 解压静态方法(不创建子文件夹)  
	 * @param zipFileName  
	 * @param outputDirectory  
	 * @throws Exception  
	 */
	@SuppressWarnings("unchecked")
	public List<String>  extract(String zipFileName,String outputDirectory) throws Exception{ 
    	List<String> list = new ArrayList<String>();
        try { 
        	ZipFile zipfile = new ZipFile(new File(zipFileName)); 
        	for (Enumeration entries = zipfile.getEntries(); entries.hasMoreElements();) { 
        		int c;   
                byte[] by=new byte[1024]; 
	        	ZipEntry entry = (ZipEntry) entries.nextElement(); 
	        	if(entry.isDirectory()) {
	        		log.info("Directory: "+entry.getName()); 	
	        	}else if(entry.getName().indexOf(".xml")!=-1){
	        		log.info("file: "+entry.getName()); 
        		    File f=new File(outputDirectory+"/"+entry.getName());   
                    InputStream in = zipfile.getInputStream(entry);   
                    FileOutputStream out=new FileOutputStream(f);                      
                    while((c=in.read(by)) != -1){   
                        out.write(by,0,c);   
                    }   
                    out.close();   
                    in.close(); 
                    list.add(entry.getName());
	        	}
        	} 
        	zipfile.close();
        }catch (Exception ex){   
          log.error("解压文件异常"+ex.getMessage());   
          throw new Exception(ex);
        }
		return list;
    }

	/**  
	 * 解压静态方法 （创建文件夹）
	 * @param zipFileName  
	 * @param outputDirectory     
	 * @throws Exception  
	 */
	@SuppressWarnings("unchecked")
	public String extractFolder(String zipFileName, String outputDirectory)throws Exception { 
		String fileName = null;
		try {
			ZipFile zipfile = new ZipFile(new File(zipFileName)); 
        	for (Enumeration entries = zipfile.getEntries(); entries.hasMoreElements();) { 
        		int c;   
                byte[] by=new byte[1024]; 
	        	ZipEntry entry = (ZipEntry) entries.nextElement(); 
	        	if(entry.isDirectory()) {
	        		log.info("Directory: "+entry.getName()); 	
	        		String name = entry.getName();
					name = name.substring(0, name.length() - 1);
					mkDirs(outputDirectory + File.separator + name);
	        	}else if(entry.getName().indexOf(".xml")!=-1 ){
	        		log.info("file: "+entry.getName()); 
	        		fileName = entry.getName();
	        		mkDirs(outputDirectory + File.separator + entry.getName());
        		    File f=new File(outputDirectory+"/"+entry.getName());   
                    InputStream in = zipfile.getInputStream(entry);   
                    FileOutputStream out=new FileOutputStream(f);                      
                    while((c=in.read(by)) != -1){   
                        out.write(by,0,c);   
                    }   
                    out.close();   
                    in.close(); 
	        	}else if(entry.getName().indexOf(".class")!=-1){
	        		log.info("file: "+entry.getName()); 
	        		mkDirs(outputDirectory + File.separator + entry.getName());
        		    File f=new File(outputDirectory+"/"+entry.getName());   
                    InputStream in = zipfile.getInputStream(entry);   
                    FileOutputStream out=new FileOutputStream(f);                      
                    while((c=in.read(by)) != -1){   
                        out.write(by,0,c);   
                    }   
                    out.close();   
                    in.close(); 
	        	}
        	} 
        	zipfile.close();
		} catch (Exception ex) {
			log.error("解压文件异常"+ex.getMessage());   
	        throw new Exception(ex);
		}
		return fileName;
	}

	/**  
	 * 创建目录，包括子目录  
	 * @param dir  
	 * @throws Exception  
	 */
	private  void mkDirs(String dir) throws Exception {
		if (dir == null || dir.equals(""))
			return;
		File f1 = new File(dir);
		if (!f1.exists()){
			f1.mkdirs();
			log.info("创建文件夹："+dir);
		}
	}

	/**  
	 * @param args  
	 */
	public static void main(String[] args) {
		ExtractZip zip = new ExtractZip();
		try {
			zip.extractFolder("F:/ttttt_ttttt_20080918212026546.zip",
					"F:/新建文件夹");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
