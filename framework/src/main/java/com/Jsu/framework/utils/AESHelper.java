package com.Jsu.framework.utils;

import android.util.Base64;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES
 * 
 * @author  炜杰
 * @version 1.4 2015-4-8
 */
public class AESHelper {
	private static Cipher cipher;
	private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	private static final String secretKey = "jutuBKiF1Yx#Lh76";

	public synchronized static String Encrypt(String pwd) throws Exception {
		try {
			cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
			byte[] raw = secretKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

			byte[] iv = getIV();
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
			byte[] encrypt = cipher.doFinal(pwd.getBytes());

			return Base64.encodeToString(byteMerger(encrypt, iv), Base64.NO_WRAP);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	private static byte[] getIV() {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString().getBytes();
	}
}
