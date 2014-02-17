package com.common.utils.crypt.support;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang.StringUtils;

import com.common.utils.crypt.TextEncrypter;


public class TextDesEncrypter implements TextEncrypter {

	public String decrypt(String encStr, String key) {
		try {
			if (StringUtils.isNotBlank(key) && key.length() >= 8) {
				return new String(desDecrypt(encStr.getBytes(), key.getBytes()));
			} else {
				return new String(desDecrypt(encStr.getBytes(),
						TextEncrypter.DEFAULT_SALT.getBytes()));
			}
		} catch (Exception ex) {
			return null;
		}
	}

	public String encrypt(String orgStr, String key) {
		try {
			if (StringUtils.isNotBlank(key) && key.length() >= 8) {
				return new String(desEncrypt(orgStr.getBytes(), key.getBytes()));
			} else {
				return new String(desEncrypt(orgStr.getBytes(),
						TextEncrypter.DEFAULT_SALT.getBytes()));
			}
		} catch (Exception ex) {
			return null;
		}
	}

	private byte[] desEncrypt(byte[] orgData, byte[] rawKey)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			BadPaddingException, IllegalBlockSizeException {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(rawKey);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(1, key, sr);
		// 正式执行加密操作
		return cipher.doFinal(orgData);
	}

	private byte[] desDecrypt(byte[] encryptData, byte[] rawKey)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			BadPaddingException, IllegalBlockSizeException {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(rawKey);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		// 正式执行解密操作
		return cipher.doFinal(encryptData);
	}

}
