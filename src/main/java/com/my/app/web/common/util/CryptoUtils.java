package com.my.app.web.common.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoUtils {

	private static final String UTF8_KEY = "12345678abcdefgh";

	private static final String UTF8_IV = "cCEml6750sYhtuBo";

	private static final String HEX_KEY = "000102030405060708090a0b0c0d0e0f";

	private static final String HEX_IV = "101112131415161718191a1b1c1d1e1f";

	/**
	 * UTF-8: count 16
	 * HEX: count 32
	 *
	 * @param count
	 * @return
	 */
	public static String iv(int count) {
		String iv = RandomStringUtils.randomAlphanumeric(count);
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
	 * AES UTF-8 암호화
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String encryptUtf8Aes(String value) {
		if (StringUtils.isBlank(value)) {
			return value;
		}

		try {
			byte[] keys = UTF8_KEY.getBytes("UTF-8");
			Key keySpec = new SecretKeySpec(keys, "AES");
			byte[] iv = UTF8_IV.getBytes();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
			return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
		} catch (Exception e) {
			log.error("", e);
		}

		return value;
	}

	/**
	 * AES UTF-8 복호화
	 *
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String decryptUtf8Aes(String value) {
		if (StringUtils.isBlank(value)) {
			return value;
		}

		try {
			byte[] keys = UTF8_KEY.getBytes("UTF-8");
			Key keySpec = new SecretKeySpec(keys, "AES");
		    byte[] iv = UTF8_IV.getBytes();
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
		    return new String(cipher.doFinal(Base64.decodeBase64(value.getBytes())));
		} catch (Exception e) {
			log.error("", e);
		}

		return value;
	}

	/**
	 * AES HEX 암호화
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
			byte[] keys = DatatypeConverter.parseHexBinary(HEX_KEY);
			Key keySpec = new SecretKeySpec(keys, "AES");
			byte[] iv = DatatypeConverter.parseHexBinary(HEX_IV);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
			return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
		} catch (Exception e) {
			log.error("", e);
		}

		return value;
	}

	/**
	 * AES HEX 복호화
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
			byte[] keys = DatatypeConverter.parseHexBinary(HEX_KEY);
			Key keySpec = new SecretKeySpec(keys, "AES");
			byte[] iv = DatatypeConverter.parseHexBinary(HEX_IV);
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
		    return new String(cipher.doFinal(Base64.decodeBase64(value.getBytes())));
		} catch (Exception e) {
			log.error("", e);
		}

		return value;
	}

}
