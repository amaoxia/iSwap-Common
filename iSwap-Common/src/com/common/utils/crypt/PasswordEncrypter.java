package com.common.utils.crypt;

public interface PasswordEncrypter {
	
	public static final String DEFAULT_SALT ="-1qazxsw2";
	/**
	 * 对密码进行加密处理
     * @param password 密码
     * @return 返回经过加密后的密码
     */
    public String encryptPassword(String password);

    public String encryptPassword(String password,String salt);
    /**
     * 将密码与加过密后的密码进行比较验证.
     * @param password 密码字符串
     * @param encryptedPassword 经过加密后的密码字符串
     * @return 密码是否有效
     */
    public boolean isPasswordValid(String password, String encryptedPassword);
    
    public boolean isPasswordValid(String password, String encryptedPassword, String salt);
}
