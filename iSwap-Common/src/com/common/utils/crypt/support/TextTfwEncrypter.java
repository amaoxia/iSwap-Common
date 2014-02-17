package com.common.utils.crypt.support;

import com.common.utils.crypt.CryptUtil;
import com.common.utils.crypt.TextEncrypter;



public class TextTfwEncrypter implements TextEncrypter {

	public String encrypt(String orgStr, String key) {
		return CryptUtil.passport_encrypt(orgStr, key);
	}

	public String decrypt(String encStr, String key) {
		return CryptUtil.passport_decrypt(encStr, key);
	}

}
