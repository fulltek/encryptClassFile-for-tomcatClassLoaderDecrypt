package com.codephase.tool;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class StreamDecrypt {
	public static InputStream decode(InputStream is) throws Exception{
	//	byte[] oldByts = new byte[is.available()];
	//	is.read(oldByts);
		//byte[] newByts = EncryptUtil.base64Decode(new String(oldByts));
		byte[] oldByts = read(is);
		byte[] newByts = EncryptUtil.aesDecryptToBytesByBytes(oldByts);
		
		return new ByteArrayInputStream(newByts);
	}
	
	private static byte[] read(InputStream in) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(in);
		byte[] buf = new byte[1024];
		int len = 0;
		List<Byte> list = new ArrayList<Byte>(); 
		while((len = bis.read(buf)) > 0) {
			for(int i=0; i<len; i++) {
				list.add(buf[i]);
			}
		}
		
		bis.close();
		byte[] result = new byte[list.size()];
		for(int i=0; i<list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}
	
	
	
}
