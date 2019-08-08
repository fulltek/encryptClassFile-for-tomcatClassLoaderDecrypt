package com.codephase.test;

import com.codephase.tool.EncryptUtil;

public class StringEncryption {

	public static void main(String[] args) throws Exception{
		String name="elearning";
		String password="Codephasegd1302";
		System.out.println("数据库用户名： "+EncryptUtil.aesEncryptFromStringToBase64String(name));
		System.out.println("数据库密码： "+EncryptUtil.aesEncryptFromStringToBase64String(password));
	   //oXOLuNgIpiK9semkMbCQbQ==------H20tYwjXQ2C/c+Kiqykumg==
	}
}
