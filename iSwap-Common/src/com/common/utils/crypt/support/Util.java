package com.common.utils.crypt.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {
	
	/** 将Hex十六进制字符转换为byte字节 */
	public static byte stringHEX2bytes(String str) {
		return (byte) ("0123456789abcdef".indexOf(str.substring(0, 1)) * 16 + "0123456789abcdef"
				.indexOf(str.substring(1)));
	}
	
	/** 将byte字节转换为Hex十六进制字符 */
	public static String byte2HEX(byte b) {
		return ("" + "0123456789abcdef".charAt(0xf & b >> 4) + "0123456789abcdef"
				.charAt(b & 0xF));
	}

	/**
     * 将表示16进制值的字符串转换为byte数组，
     * 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
	public static byte[] hexStr2ByteArr(String strIn){
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}
	
	/**
     * 将byte数组转换为表示16进制值的字符串，
     * 如：byte[]{8,18}转换为：0813，
     * 和public static byte[] hexStr2ByteArr(String strIn)
     * 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        //每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            //把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            //小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
    
	public static String getMD5(String mds) {
		String mdresult = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			mdresult = md5bytes2string(md.digest(mds.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return mdresult;
	}
	
	public static String md5bytes2string(byte[] bytes) {
		String result = "";
		for (int i = 0; i < bytes.length; i++) {
			result += byte2HEX(bytes[i]);
		}
		return result;
	}
}
