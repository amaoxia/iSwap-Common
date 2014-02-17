package com.common.utils.ziputil;


import java.io.File;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

/**
 * 打包
 * @author huwanshan
 *
 */
public class ZipUntil {
    
	public synchronized  static void createZip(String[] fileName, String path, String zipName,String zipPath) {
		try {
	    mkDirs(zipPath);
	    zipPath = zipPath+"//"+zipName;
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(new File(zipPath));
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		for(int i=0;i<fileName.length;i++){
			String filepath = path+fileName[i];
			fileSet.setFile(new File(filepath));
		}
			zip.addFileset(fileSet);
			zip.execute();
		} catch (Exception e) {
			System.out.println("生成zip包出错！"+e);
		} 
	}
	
	public synchronized static void createZip(String[] fileName, String path[], String zipName) {
		String zipPath = path[0]+zipName+".zip";
		try {
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(new File(zipPath));
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		for(int i=0;i<fileName.length;i++){
			String filepath = path[i]+fileName[i];
			fileSet.setFile(new File(filepath));
		}
			zip.addFileset(fileSet);
			zip.execute();
		} catch (Exception e) {
			System.out.println("生成zip包出错！"+e);
		} 
	}
	
	/**  
	 * 创建目录，包括子目录  
	 * @param dir  
	 * @throws Exception  
	 */
	public static void mkDirs(String dir) throws Exception {
		if (dir == null || dir.equals(""))
			return;
		File f1 = new File(dir);
		if (!f1.exists()){
			f1.mkdirs();
			System.out.println("创建文件夹："+dir);
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		ZipUntil zip = new ZipUntil();
		try {
			String[] fileName = {"部门表.xls","汇总表.xls"};
			String zipPath = "C://test1//";
			zip.createZip(fileName,"C://","test.zip",zipPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
