package com.my.app.web.common.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class CryptoUtils {

	/**
	 * 암호화
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String encryptAES(String value) {
		if (StringUtils.isBlank(value)) {
			return value;
		}

		try {
			Key keySpec = new SecretKeySpec("cCEml6750sYhtuBo".getBytes(), "AES");
			byte[] iv = "12345678abcdefgh".getBytes();
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
			byte[] encrypted = c.doFinal(value.getBytes());
			return Base64.encodeBase64String(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

	/**
	 * 복호화
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String decryptAES(String value) {
		if (StringUtils.isBlank(value)) {
			return value;
		}

		try {
			Key keySpec = new SecretKeySpec("cCEml6750sYhtuBo".getBytes(), "AES");
		    byte[] iv = "12345678abcdefgh".getBytes();
		    Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
		    byte[] byteStr = Base64.decodeBase64(value.getBytes());
		    return new String(c.doFinal(byteStr));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}

}
