package com.common.jobtool;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 对Scheduler工作对象的封装
 *@Company 北京光码软件有限公司
 *@author hudaowan
 *@version  iSwap V5.0
 *@date  Aug 21, 2008 12:00:42 PM
 *@Team 数据交换平台研发小组
 */
public class JobTool {
	private static final Log log = LogFactory.getLog(JobTool.class);
	private static Scheduler sched = null;
	private static JobTool jobUtil = null;
	public synchronized static JobTool init(){
		if(jobUtil == null){
			jobUtil = new JobTool();
			SchedulerFactory sf = new StdSchedulerFactory();
			 try {
				sched = sf.getScheduler();
				sched.start();
			} catch (SchedulerException e) {
				log.error("Scheduler 初始化失败！ ",e);
			}
		}
		return jobUtil;
	}
	
	/**
	 * 添加一个调度任务
	 *@author hudaowan
	 *@date  Aug 21, 2008 12:50:06 PM
	 *@param jobName
	 *@param clazz
	 *@param spaceTime  "0/1 * * * * ?"
	 */
	@SuppressWarnings("unchecked")
	public void addJob(String jobName,String spaceTime,Class clazz){
		String groupName = jobName;
		JobDetail jobs = newJob(clazz).withIdentity(jobName,groupName).build();
		CronTrigger trigger; 
		try {
			if(sched.isShutdown()){
				log.info("Scheduler is shutdown,Scheduler start...");
				sched.start();
			}
			trigger = newTrigger().withIdentity(jobName+groupName, groupName).withSchedule(cronSchedule(spaceTime)).build();
			sched.scheduleJob(jobs, trigger);
			log.info("任务名称：【"+jobName+"】加载成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("添加任务失败！  任务的名称："+jobName, e);
			new Exception(e);
		} 
	
	}
	
	/**
	 * 删除调度任务
	 *@author hudaowan
	 *@date  Aug 21, 2008 1:01:11 PM
	 *@param jobName
	 */
	public void deleteJob(String jobName){
		String groupName = jobName;
		try {
			if(sched.isShutdown()){
				log.info("Scheduler is shutdown,Scheduler start...");
				sched.start();
			}else{
				JobKey key = new JobKey(jobName,groupName);
				sched.deleteJob(key);
				log.info("任务名称：【"+jobName+"】删除成功！");
			}
		} catch (SchedulerException e) {
			log.error("删除任务失败！  任务的名称："+jobName, e);
		}
	}
	
	/**
	 * 得到所有的job
	 *@author hudaowan
	 *@date  Aug 21, 2008 1:55:11 PM
	 *@return
	 */
	public List<String> getJobs(){
		List<String> str = null;
		try {
			str = sched.getJobGroupNames();
		} catch (SchedulerException e) {
			log.error("查询JOb失败", e);
		}
		return str;
	}
	
}
