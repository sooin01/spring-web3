package com.my.app.web.common.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoUtils {

	private static final String KEY = "12345678abcdefgh";

	public static String iv() {
		String iv = RandomStringUtils.randomAlphanumeric(16);
		log.debug("iv created: {}", iv);
		return iv;
	}

	public static String getRequestIv() {
		String iv = (String) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest()
				.getAttribute("iv");
		log.debug("iv request: {}", iv);
		return iv;
	}

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
			byte[] keys = KEY.getBytes("UTF-8");
			Key keySpec = new SecretKeySpec(keys, "AES");
			byte[] iv = "cCEml6750sYhtuBo".getBytes();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
			return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
		} catch (Exception e) {
			log.error("", e);
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
			byte[] keys = KEY.getBytes("UTF-8");
			Key keySpec = new SecretKeySpec(keys, "AES");
		    byte[] iv = "cCEml6750sYhtuBo".getBytes();
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
		    return new String(cipher.doFinal(Base64.decodeBase64(value.getBytes())));
		} catch (Exception e) {
			log.error("", e);
		}

		return value;
	}

}
