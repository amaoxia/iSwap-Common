package com.common.utils.crypt.support;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.common.utils.crypt.PasswordEncrypter;


public class PasswordMd5Encrypter implements PasswordEncrypter {
	
	public String encryptPassword(String password) {
		return encryptPassword(password,null);
	}

	public boolean isPasswordValid(String password, String encryptedPassword) {
		return isPasswordValid(password,encryptedPassword,null);
	}

	public String encryptPassword(String password, String salt) {
		if (password == null){
			password = "";
		}
		String encodeTxt = "";
		if(StringUtils.isNotBlank(salt)){
			encodeTxt=password+salt;
		}else{
		    encodeTxt = password + PasswordEncrypter.DEFAULT_SALT;
		}
		byte[] encryptedPassword = Base64.encodeBase64(DigestUtils
				.md5(encodeTxt));
		return new String(encryptedPassword);
	}

	public boolean isPasswordValid(String password, String encryptedPassword,
			String salt) {
		if (StringUtils.isBlank(encryptedPassword)&&StringUtils.isBlank(password)){
			return true;
		}else{
			return encryptedPassword.equals(encryptPassword(password,salt));
		}
	}
	
	public static void main(String[] args) {
		PasswordMd5Encrypter i = new PasswordMd5Encrypter();
		System.out.println(i.encryptPassword("demo"));
	}
}
