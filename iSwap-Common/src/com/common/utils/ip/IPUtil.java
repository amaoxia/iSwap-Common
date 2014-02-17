package com.common.utils.ip;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.common.utils.ip.support.IPSeeker;


public class IPUtil {
	private static IPSeeker seeker = IPSeeker.getInstance();

	/**
	 * 根据IP地址串,得到该ip地址对应的地址(名称)
	 * @param ip :IP地址串
	 * @return 该ip地址对应的地址(名称)
	 */
	public static String getAddress(String ip) {
		return seeker.getAddress(ip);
	}
	
	/**
	 * 得到机器的本地IP地址
	 * 
	 * @return 本地IP地址
	 */
	public static String getLocalIP() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		// System.out.println(ipAddrStr);
		return ipAddrStr;
	}
	
	/**
	 * 返回本地IP列表
	 * @return 返回本地IP列表
	 */
	public static List<String> getLocalIPs() {
		List<String> lips = new ArrayList<String>();
		Enumeration<NetworkInterface> netInterfaces = null;   
		try {   
		    netInterfaces = NetworkInterface.getNetworkInterfaces();   
		    while (netInterfaces.hasMoreElements()) {   
		        NetworkInterface ni = netInterfaces.nextElement();  
		        Enumeration<InetAddress> ips = ni.getInetAddresses();   
		        while (ips.hasMoreElements()) { 
		        	lips.add(ips.nextElement().getHostAddress());
		        }   
		    }   
		} catch (Exception e) {   
		    e.printStackTrace();   
		}  
		return lips;
	}
	
	/**
	 * 返回request端的IP地址对应的名称如‘202.108.22.5’地址对应的名称为“北京市 百度公司”
	 * @param request HttpServletRequest
	 * @return request端的IP地址对应的名称
	 */
	public static String getRequestIpAddrName(HttpServletRequest request) {
		return getAddress(getRequestIpAddr(request));
	}

	/**
	 * 返回request端的IP地址，如“202.106.0.20”
	 * @param request HttpServletRequest
	 * @return request端的IP地址
	 */
	public static String getRequestIpAddr(HttpServletRequest request) { 
		String ip = request.getHeader("x-forwarded-for");    
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	           ip = request.getHeader("Proxy-Client-IP");    
	       }    
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	           ip = request.getHeader("WL-Proxy-Client-IP");    
	       }    
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {    
	           ip = request.getRemoteAddr();    
	       }
	       if(ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")){
	    	   return "127.0.0.1";
	       }else{
	    	   return ip;
	       }
	}
	
	public static void main(String[] args) {
		System.out.println(getLocalIPs());
	}
}
