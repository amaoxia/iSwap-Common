package com.common.utils.crypt.support;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.common.utils.crypt.PasswordEncrypter;


public class PasswordShaEncrypter implements PasswordEncrypter {
	
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
                .sha(encodeTxt));
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
		PasswordShaEncrypter i = new PasswordShaEncrypter();
		System.out.println("=========================");
		String ori="asdd"; String salt="1";
		String tar = i.encryptPassword(ori,salt);
		String tar1 = i.encryptPassword(ori);
		System.out.println(tar);System.out.println(tar1);
		if(i.isPasswordValid(ori, tar,salt)){
		  System.out.println("true");
		}else{
			System.out.println("false");
		}
		if(i.isPasswordValid(ori, tar1)){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}
}
