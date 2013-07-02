package util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class LoginSvc {
	
	private static final String key = "1N7R4N37";

	public static Tripa DestrinchaToken(String token) throws IOException {
		String temp = LoginSvc.decryptBlowfish(token, key);
		// String temp = token;

		// jose|senha|12343
		Tripa tripa = new Tripa();
		
		int i = temp.indexOf('|');
		String usuario = temp.substring(0, i);
		tripa.usuario = usuario;
		int p = i + 1;
		i = temp.indexOf('|', p);
		String senha = temp.substring(p, i);
		tripa.senha = senha;
		p = i + 1;
		String timestamp = temp.substring(p);
		tripa.timestamp = timestamp;

		Date agora = new Date();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(agora);
		cal.add(Calendar.HOUR_OF_DAY, -1);
		Date agoraHaPouco = cal.getTime();
		
		cal.setTime(agora);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		Date daquiHaPouco = cal.getTime();

		String agoraStr = new SimpleDateFormat("yyyyMMddHH").format(agora);
		String agoraHaPoucoStr = new SimpleDateFormat("yyyyMMddHH").format(agoraHaPouco);
		String daquiHaPoucoStr = new SimpleDateFormat("yyyyMMddHH").format(daquiHaPouco);

		if (tripa.timestamp.equals(agoraStr) || tripa.timestamp.equals(agoraHaPoucoStr) || tripa.timestamp.equals(daquiHaPoucoStr))
			return tripa;
		return null;
	}
	
	public static String EntrinchaResp(String timestamp, String status) {
		String resp = encryptBlowfish(timestamp + "|" + status, key);
		return resp;
	}

	public static String encryptBlowfish(String to_encrypt, String strkey) {
		try {
			SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encodedBytes = cipher.doFinal(to_encrypt.getBytes());

			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encodeBuffer(encodedBytes);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public static String decryptBlowfish(String to_decrypt, String strkey) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decodedBytes = decoder.decodeBuffer(to_decrypt);
			
			
			SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decrypted = cipher.doFinal(decodedBytes);
			return new String(decrypted);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
}
