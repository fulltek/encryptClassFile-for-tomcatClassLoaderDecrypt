package com.codephase.tool;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;


/**
 * 加密项目里面的Class文件，以保护码枫软件产权
 * @author MarkYXJ
 * @version 190801_stable
 * 
 */
 
public class EncryptUtil {

	
	private static String marijuana=null;
      
    /** 
     * 将byte[]转为各种进制的字符串 
     * @param bytes byte[] 
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
     * @return 转换后的字符
     */  
    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 
    }  
    
    private static void setCoral() throws AesException{
    	if(marijuana==null){
    		marijuana=WhiteIPVerify.getSingature(WhiteIPVerify.IpCheck(),ServerHWInfo.getMac(),ServerHWInfo.getDisk(),ServerHWInfo.getCpu());
    	}
    }
    
    private static String getOrgan() throws AesException{
    	setCoral();
    	return marijuana;
    }

    public static byte[] md5(byte[] bytes) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");  
        md.update(bytes);  
        return md.digest();  
    }  

    public static byte[] md5(String msg) throws Exception {  
        return isEmptyString(msg) ? null : md5(msg.getBytes("UTF-8"));  
    }  
      
    public static byte[] aesEncryptFromStringToBytes(String content) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
     
        //kgen.init(128, new SecureRandom(key.getBytes("UTF-8")));  
        //为兼容linux环境下的加解密，需要把上面一行替换为下面三行
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(getOrgan().getBytes("UTF-8"));
		kgen.init(128, secureRandom);
  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
          
        return cipher.doFinal(content.getBytes("UTF-8"));  
    }  
    
    public static byte[] aesEncryptFromBytesToBytes(byte[] content) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(getOrgan().getBytes("UTF-8"));
		kgen.init(128, secureRandom); 
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));      
        return cipher.doFinal(content);  
    }  
    
    public static String aesEncryptFromStringToBase64String(String content) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
     
        //kgen.init(128, new SecureRandom(key.getBytes("UTF-8")));  
        //为兼容linux环境下的加解密，需要把上面一行替换为下面三行
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(getOrgan().getBytes("UTF-8"));
		kgen.init(128, secureRandom);
  
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        
        byte[] encrypted =cipher.doFinal(content.getBytes("UTF-8"));
        String base64Encrypted = Base64.getEncoder().encodeToString(encrypted);
          
        return base64Encrypted;  
    }  
      

    public static String aesDecryptToStringByBytes(byte[] encryptBytes) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(getOrgan().getBytes("UTF-8"));
		kgen.init(128, secureRandom); 
          
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
          
        return new String(decryptBytes);  
    } 
      
    public static byte[] aesDecryptToBytesByBytes(byte[] encryptBytes) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(getOrgan().getBytes("UTF-8"));
		kgen.init(128, secureRandom); 
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);    
        return decryptBytes;  
    } 
     
    public static String aesDecryptToStringByBase64String(String encrypted) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(getOrgan().getBytes("UTF-8"));
		kgen.init(128, secureRandom); 
        byte[] encryptBytes=Base64.getDecoder().decode(encrypted);
        Cipher cipher = Cipher.getInstance("AES");  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
          
        return new String(decryptBytes,"UTF-8");  
    }
    
	public static boolean isEmptyString(String str){
		return str == null || str.length() == 0;
	}
	
	
	
}
