package com.common.jobtool;

/**
 * JOB需要的表达式的封装
 *@Company 北京光码软件有限公司
 *@author huwanshan
 *@version   V1.0
 *@date  2008-8-26 上午10:45:25
 *@Team 数据交换平台研发小组
 */
public class ELTool {
	private static ELTool eltool = null;
	public synchronized static ELTool init(){
		if(eltool == null){
			eltool = new ELTool();
		}
		return eltool;
	}
	
	/**
	 * 秒表达式（"0/1 * * * * ?"）
	 *@author huwanshan
	 *@date  2008-9-1 下午03:55:15
	 *@param datetime("0/"+datetime+" * * * * ?")
	 *@return
	 */
	public String sec(String datetime) {
		String time = "0/"+datetime+" * * * * ?";    //按秒
		return time;
	}
	
	/**
	 * 分钟表达式（"0 0/1 * * * ?"）
	 *@author huwanshan
	 *@date  2008-9-1 下午03:55:15
	 *@param datetime
	 *@return
	 */
	public String cent(String datetime) {
		String cent = "0 0/"+datetime+" * * * ?";
		return cent;
	}
	
	/**
	 *从0点到23每个n（小时）调度一次
	 *@author huwanshan
	 *@date  2008-9-1 下午04:18:38
	 *@param datetime("0-23/2" n=2)
	 *@return
	 */
	public String time(String datetime) {
		String times = "0 0 0-23/"+datetime+" * * ?";
		return times;
	}
	
	
	/**
	 * 按天
	 * 2008-9-1,2008-9-2...12点
	 *@author huwanshan
	 *@date  2008-9-1 下午04:23:15
	 *@param datetime("0 0 12 1,2,3 8,9,10 ? 2008")
	 *@return
	 */
	public String day(String year,String month,String day) {
		String days = "0 10 23 "+day+" "+""+month+" "+" ? "+year;
		return days;
	}
	
	
	/**
	 * 星期表达式
	 * 每月周三12点『天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）』
	 *@author huwanshan
	 *@date  2008-9-1 下午10:42:15
	 *@param weeks ("0 0 12 ? * WED,THU,SAT")
	 *@return
	 */
	public String week(String weeks,String time) {
		//String times = "0 10 1 ? * "+weeks;
		String[] one =  time.split(":");
		String times = "0 "+Integer.valueOf(one[1])+" "+Integer.valueOf(one[0])+" ? * "+weeks;
		return times;
	}
	
	/**
	 * 按月
	 *@author huwanshan
	 *@date  2008-9-1 下午10:53:08
	 *@param weeks("0 0 12 15,25,26 * ?")
	 *@return
	 */
	public String month(String months, String time) {
		//String times = "0 10 19 "+months+" * ?";
		String[] one =  time.split(":");
		String times = "0 "+Integer.valueOf(one[1])+" "+Integer.valueOf(one[0])+" "+months+" * ?";
		return times;
	}
	
	/**
	 * 按每天什么时间调度
	 *@author huwanshan
	 *@date  2008-9-1 下午10:53:08
	 *@param day("0 0 10，14，16 * * ? 每天上午10点，下午2点，4点")
	 *@return  
	 */
	public String today(String time) {
		//String times = "0 0 "+time+" * * ?";
		String[] one =  time.split(":");
		String times = "0 "+Integer.valueOf(one[1])+" "+Integer.valueOf(one[0])+" * * ?";
		return times;
	}
	
	
}
