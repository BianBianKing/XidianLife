package com.xidian.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
	public static String salt = "{d@sd{0!}";
	public static boolean passwordsMatch(String submittedPlaintext,String saved){
	        if (saved == null || saved.length() == 0) {
	            return submittedPlaintext == null || submittedPlaintext.isEmpty();
	        } else {
	            if (submittedPlaintext == null || submittedPlaintext.isEmpty()) {
	                return false;
	            }
	        }

	        return saved.equals(encryptPassword(submittedPlaintext));
	}

	public static String encryptPassword(String pwd){
		return encode("MD5",pwd+salt);
	}
	
	public static String MD5Encode(String text){
		return encode("MD5",text);
	}
	
    /**
	 * 将摘要信息转换为相应的编码
	 * @param code 编码类型
	 * @param message 摘要信息
	 * @return 相应的编码字符串
	 */
	private static String encode(String code,String message){
		if (message==null) {
			return null;
		}
		MessageDigest md;
		String encode = null;
		try {
			md = MessageDigest.getInstance(code);
			encode = byteArrayToHexString(md.digest(message.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encode;
	}

	 private static String byteArrayToHexString(byte buffer[]) {    
         StringBuilder sb = new StringBuilder(buffer.length * 2);    
         for (int i = 0; i < buffer.length; i++) {    
             sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));    
             sb.append(Character.forDigit(buffer[i] & 15, 16));    
         }    

         return sb.toString();    
     }
}
