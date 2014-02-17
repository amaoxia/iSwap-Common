package com.common.utils.crypt;

import com.common.utils.crypt.support.Util;
import it.sauronsoftware.base64.Base64;


public class CryptUtil {

	public static String passport_encrypt(String txt, String key) {
		// 使用随机数发生器产生 0~32000 的值并 MD5()
		int j = (int) (Math.random() * 32000);
		String encrypt_key = Util.getMD5(Integer.toString(j));
		int ctr = 0;
		String tmp = "";
		for (int i = 0; i < txt.length(); i++) {
			// 如果 $ctr = $encrypt_key 的长度，则 $ctr 清零
			ctr = ctr == encrypt_key.length() ? 0 : ctr;
			// $tmp 字串在末尾增加两位，其第一位内容为 $encrypt_key 的第 $ctr 位，
			// 第二位内容为 $txt 的第 $i 位与 $encrypt_key 的 $ctr 位取异或。然后 $ctr = $ctr
			// + 1
			char a1 = encrypt_key.charAt(ctr);
			char t1 = txt.charAt(i);
			char t2 = encrypt_key.charAt(ctr++);
			char a2 = (char) (t1 ^ t2);
			tmp += String.valueOf(a1) + a2;
		}
		return Base64.encode((passport_key(tmp, key)));
	}

	public static String passport_decrypt(String txt1, String key) {
		// $txt 的结果为加密后的字串经过 base64 解码，然后与私有密匙一起，
		// 经过 passport_key() 函数处理后的返回值
		String txt = passport_key(new String(Base64.decode(txt1)), key);
		// 变量初始化
		String tmp = "";
		for (int i = 0; i < txt.length(); i++) {
			// $tmp 字串在末尾增加一位，其内容为 $txt 的第 $i 位，
			// 与 $txt 的第 $i + 1 位取异或。然后 $i = $i + 1
			tmp += (char) (txt.charAt(i) ^ txt.charAt(++i));
		}
		return tmp;
	}
	
	private static String passport_key(String txt,String encrypt_key1){    
        // 将 $encrypt_key 赋为 $encrypt_key 经 md5() 后的值
        String encrypt_key = Util.getMD5(encrypt_key1);    
        // 变量初始化
        int ctr = 0;    
        String tmp = "";    
        for(int i = 0; i < txt.length(); i++) {    
            // 如果 $ctr = $encrypt_key 的长度，则 $ctr 清零
            ctr = ctr == encrypt_key.length() ? 0 : ctr;    
            // $tmp 字串在末尾增加一位，其内容为 $txt 的第 $i 位，
            // 与 $encrypt_key 的第 $ctr + 1 位取异或。然后 $ctr = $ctr + 1
            tmp += (char)(txt.charAt(i) ^ encrypt_key.charAt(ctr++));    
        }    
        return tmp;    
    }
	
	public static void main(String[] args) {
		String s = passport_encrypt("tanhcadadfasdfafdafdafdafaa12312312@#@#@#@#","tanhc");
		
		System.out.println(s);
		System.out.println(passport_decrypt(s,"tanhc"));
		
		System.out.println(passport_decrypt(s,"tanhc1"));
	}
}
