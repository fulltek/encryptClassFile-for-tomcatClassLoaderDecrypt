package com.codephase.test;

import java.io.File;

import com.codephase.tool.DecodeConf;
import com.codephase.tool.EncryptUtil;



public class ClassEncryption {

	public static void main(String args[]) throws Exception{
	//	File file=new File("C:\\tmp\\jar\\Hello.class");  encryptFiles(file);
		encryptWebucationClassForServerDeploy();	
	}

    /** 
     * 为加密部署webucation项目，而加密配置文件制定目录下的class文件 ,在加密项目发布前，需要提前运行本程序，对
     * decode.properties中定义的所有class文件进行加密
     * @param 无
     * @return 无
     * @throws Exception 
     * @author: mcload
	 * @version: V1.00-190715
     */  
	private static void encryptWebucationClassForServerDeploy() throws Exception{
		if(DecodeConf.getConf().isRunDecode()){
			for(String dir : DecodeConf.getConf().getDirs()){
				String filepath = DecodeConf.getConf().getClassPath()+dir;
				File classDir = new File(filepath);	//获取其file对象
				encryptFiles(classDir);
			}
		}
	}
	
    /** 
     * 加密某个文件的内容，并覆盖原文件
     * @param File
     * @return 无
     * @throws Exception 
     * @author: mcload
	 * @version: V1.00
     */  
	private static void encodeAndWriteBack(File file) throws Exception {
		// 读取文件内容
		byte[] oldcontent = FileOperateHelper.read(file);
		// 进行加密
		byte[] encodeContent = EncryptUtil.aesEncryptFromBytesToBytes(oldcontent);
		// 将加密后的内容，覆盖原来的文件内容
		FileOperateHelper.write(encodeContent, file);
		System.out.println(file.getPath() + " --> encrypt done!");
	}
 
    /** 
     * 递归加密某个文件或该文件夹下所有文件
     * @param File 文件夹或文件
     * @return 无
     * @throws Exception 
     * @author: mcload
	 * @version: V1.00
     */ 
	private static void encryptFiles(File dirOrFile) throws Exception {
			if (dirOrFile.isDirectory()) {
				for(File dOrF:dirOrFile.listFiles())
					encryptFiles(dOrF);
			}else {
				encodeAndWriteBack(dirOrFile);
			}
	}

}
