package com.codephase.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*
 * to check IP,mac,disk,cpu, if they are in the WhiteList
 * @author MarkYXJ
 * @version 190801_stable
 *  
 */

public class WhiteIPVerify {
	static Charset CHARSET = Charset.forName("utf-8");
	static String IpCheck() throws AesException{
		String nonce=getRandomStr();
		long ts=new Date().getTime();
		StringBuffer sb=new StringBuffer();
		sb.append("act=hwkt");
		sb.append("&a=").append(getSingature(ServerHWInfo.getMac(),Long.toString(ts),nonce,ServerHWInfo.getCpu()));
		sb.append("&b=").append(ServerHWInfo.getMac());
		sb.append("&c=").append(nonce);
		sb.append("&d=").append(ts);
		String url="http://mafeng.codephase.cn/lorc.htm";
		String rs=sendPost(sb.toString(),url).trim();
		if(rs.length()>0){
			StringBuffer sb1 = new StringBuffer();
			sb1.append(ServerHWInfo.getMac()).append(ServerHWInfo.getDisk()).append(ServerHWInfo.getCpu());
			return decrypt(rs,"A97eMWD7cyCfwAmR",md5(sb1.toString()));
		}
		else
			return null;
	}
	
    private static String sendPost(String postData, String postUrl) {
        try {  
            // 发送POST请求  
            URL url = new URL(postUrl);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setRequestMethod("POST");//修改发送方式  
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn.setUseCaches(false);  
            conn.setDoOutput(true);  
            conn.setRequestProperty("Content-Length", "" + postData.length());  
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");  
            out.write(postData);  
            out.flush();  
            out.close();  
            // 获取响应状态  
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {  
                return "";  
            }  
            // 获取响应内容体  
            String line, result = "";  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));  
            while ((line = in.readLine()) != null) {
                result += line + "\n";  
            }  
            in.close();  
            return result;  
        } catch (IOException e) { 
        	e.printStackTrace();
        }
        return "";  
    }
	
	static String getSingature(String token, String timestamp, String nonce, String encrypt) throws AesException{
			try {
				String[] array = new String[] {token, timestamp, nonce, encrypt };
				StringBuffer sb = new StringBuffer();
				// 字符串排序
				Arrays.sort(array);
				for (int i = 0; i < 4; i++) {
					sb.append(array[i]);
				}
				String str = sb.toString();
				// SHA1签名生成
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				md.update(str.getBytes());
				byte[] digest = md.digest();
			
				StringBuffer hexstr = new StringBuffer();
				String shaHex = "";
				for (int i = 0; i < digest.length; i++) {
					shaHex = Integer.toHexString(digest[i] & 0xFF);
					if (shaHex.length() < 2) {
						hexstr.append(0);
					}
					hexstr.append(shaHex);
				}
				return hexstr.toString();
			} catch (Exception e) {
				e.printStackTrace();
				throw new AesException(AesException.ComputeSignatureError);
			}
	  }
	
	// 随机生成16位字符串
	public static String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}


	public static  String decrypt(String text,String sKey,String sIv) throws AesException {
		byte[] original;
		byte[] baKey = sKey.getBytes(CHARSET);  
		byte[] baIv=sIv.getBytes(CHARSET);
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec key_spec = new SecretKeySpec(baKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(baIv, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
			byte[] encrypted = Base64.getDecoder().decode(text);
			original = cipher.doFinal(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.DecryptAESError);
		}

		String content;
		try {
			byte[] bytes = decode(original);
			content = new String(bytes, CHARSET);
		}catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.IllegalBuffer);
		}
		return content;
	}
	
	/**
	 * 删除解密后明文的补位字符
	 * 
	 * @param decrypted 解密后的明文
	 * @return 删除补位字符后的明文
	 */
	static byte[] decode(byte[] decrypted) {
		int pad = (int) decrypted[decrypted.length - 1];
		if (pad < 1 || pad > 32) {
			pad = 0;
		}
		return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
	}
	
	static String md5(String plainText ) {
		 try { 
			 MessageDigest md = MessageDigest.getInstance("MD5"); 
			 md.update(plainText.getBytes("UTF-8")); 
			 byte b[] = md.digest(); 
			 int i; 
			 StringBuffer buf = new StringBuffer(""); 
			 for (int offset = 0; offset < b.length; offset++) { 
				 i = b[offset]; 
				 if(i<0) i+= 256; 
				 if(i<16) 
				 buf.append("0"); 
				 buf.append(Integer.toHexString(i)); 
			 } 
			 return buf.toString();
		 } catch (NoSuchAlgorithmException e) { 
		 // TODO Auto-generated catch block 
		 e.printStackTrace(); 
		 } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	 }

}
