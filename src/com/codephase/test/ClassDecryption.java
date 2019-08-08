package com.codephase.test;

import java.io.File;

import com.codephase.tool.DecodeConf;
import com.codephase.tool.EncryptUtil;

public class ClassDecryption {
	
	public static void main(String args[]) throws Exception{
	//	File file=new File("C:\\tmp\\jar\\Hello.class"); decodeAndWriteBack(file);
		decryptWebucationClassForVerifyOnly();
	}
	
    /**  
     * 为加密部署webucation项目，而加密配置文件制定目录下的class文件。本程序用于解密被加密过的class文件。
     * @param 无
     * @return 无
     * @throws Exception 
     * @author: mcload
	 * @version: V1.00
     */  
	private static void decryptWebucationClassForVerifyOnly() throws Exception{
		if(DecodeConf.getConf().isRunDecode()){
			for(String dir : DecodeConf.getConf().getDirs()){
				String filepath = DecodeConf.getConf().getClassPath()+dir;
				File classDir = new File(filepath);	//获取其file对象
				decryptFiles(classDir);
			}
		}
	}
	
	private static void decodeAndWriteBack(File file) throws Exception {
		// 读取文件内容
		byte[] encrypted = FileOperateHelper.read(file);
		// 进行加密
		byte[] decrypted = EncryptUtil.aesDecryptToBytesByBytes(encrypted);
		// 将加密后的内容，覆盖原来的文件内容
		FileOperateHelper.write(decrypted, file);
		System.out.println(file.getPath() + " --> decrypt done!");
	}
	 
	private static void decryptFiles(File dirOrFile) throws Exception {
			if (dirOrFile.isDirectory()) {
				for(File dOrF:dirOrFile.listFiles())
					decryptFiles(dOrF);
			}else {
				decodeAndWriteBack(dirOrFile);
			}
	}
	
}
