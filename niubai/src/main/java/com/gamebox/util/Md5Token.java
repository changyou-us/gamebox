package com.gamebox.util;

import java.security.MessageDigest;

/**
 * MD5加密工具类  
 * @author Wu Ji
 */

public final class Md5Token {
	private static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static Md5Token instance = null;

	private Md5Token() {
	}

	public synchronized static Md5Token getInstance() {
		if (instance == null) {
			instance = new Md5Token();
		}
		return instance;
	}

	public String getShortToken(String arg0) {
		return encoder(arg0).substring(8, 24);
	}

	/**
	 * 加密，这也是gamefuse的加密算法
	 * @param arg0
	 * @return
	 */
	public String getLongToken(String arg0) {
		return encoder(arg0).toString();
	}

	private StringBuffer encoder(String arg) {
		if (arg == null) {
			arg = "";
		}
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			md5.update(arg.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toHex(md5.digest());
	}

	private StringBuffer toHex(byte[] bytes) {
		StringBuffer str = new StringBuffer(32);
		int length = bytes.length;
		for (int i = 0; i < length; i++) {
			str.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
			str.append(hexDigits[bytes[i] & 0x0f]);
		}
		return str;
	}
	
	/**
	 * gamebox的加密算法
	 * @param password 明文密码
	 * @param salt 秘钥
	 * @return 加密后的密码
	 */
	public String getGameboxPassword(String password,String salt)
	{
		return getLongToken(getLongToken(password) + salt);
	}
	
}
