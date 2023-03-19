package com.codephase.test;

import com.codephase.tool.EncryptUtil;

public class StringDecryption {
	public static void main(String[] args) throws Exception{
		String encryptedName="encryptedName";
		String encryptedPassword="encryptedPassword";
		System.out.println(EncryptUtil.aesDecryptToStringByBase64String(encryptedName)+"~~~~~~"+EncryptUtil.aesDecryptToStringByBase64String(encryptedPassword));
	}
}
