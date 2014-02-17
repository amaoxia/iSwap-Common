package com.common.utils.crypt;

public interface TextEncrypter {
	public static final String DEFAULT_SALT ="-1qazxsw2";
	public String encrypt(String orgStr, String key);
	
	public String decrypt(String encStr, String key);
}
