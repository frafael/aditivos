package util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	public static final String toMD5(String stringToHash) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			BigInteger hash = new BigInteger(1, md.digest(stringToHash.getBytes("UTF-8")));
			return hash.toString(16);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		} catch (NoSuchAlgorithmException e) {
			e.getStackTrace();
			return "";
		}
	}
}